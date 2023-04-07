package com.example.flowdemo.view.editor;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.FlowFile;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.*;
import com.example.flowdemo.model.flow.nodes.*;
import com.example.flowdemo.model.transpiler.*;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarLexer;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;
import com.example.flowdemo.view.editor.cell.*;
import com.example.flowdemo.view.editor.expr.*;
import com.example.flowdemo.view.editor.menu.NewItem;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class EditorViewModel {
    // Model attributes
    private Map<String, Flow> flowMap; // Maps identifiers to flow-chart models
    private Flow selectedModel; // Currently visible model
    private FlowNode dragNode; // Node being dragged
    private Expr dragExpr; // Expression being dragged
    private FlowException error; // Error returned from semantic analysis

    // View attributes
    private Map<String,UIFlow> functionRootMap; // Maps function name to all of its corresponding visual elements
    private Map<String,UIFlow> functionFlowMap; // Maps function name to all of its visual elements represented in the model
    private UIFlow selectedFlowRoot; // UIFlow mapped in functionRootMap with identifier matching selectedModel
    private UIFlow selectedFlowBody; // UIFlow mapped in functionFlowMap with identifier matching selectedModel
    private boolean pseudoVisible = false; // True if pseudocode labels should be visible

    public EditorViewModel() {
        flowMap = new HashMap<>();
        flowMap.put("main", new Flow());

        // Create UIFlow maps
        functionRootMap = new HashMap<>();
        functionFlowMap = new HashMap<>();

        // Create default main method
        addUIFlow("main");
        selectModel("main");
    }

    // --- VIEW UPDATE --------------------------------------------------------

    public UIFlow getFlowRoot(String identifier) {
        return functionRootMap.get(identifier);
    }

    private void addUIFlow(String identifier) {
        UIFlow flowRoot = new UIFlow(UICell.IRRELEVANT_ID);
        UIFlow flowBody = new UIFlow(UICell.IRRELEVANT_ID);

        // Add start and end cells to main's root flow
        UISquircle start = new UISquircle("Start", UICell.IRRELEVANT_ID);
        start.setStyleClass("default");
        start.setOnMouseClicked(e -> selectedFlowRoot.updateLayout());
        UISquircle end = new UISquircle("End", UICell.IRRELEVANT_ID);
        end.setStyleClass("default");
        flowRoot.addCell(start, 0);
        flowRoot.addCell(flowBody, 1);
        flowRoot.addCell(end, 2);

        setCellPlaceholderHandlers(flowBody.getPlaceholder());

        functionRootMap.put(identifier, flowRoot);
        functionFlowMap.put(identifier, flowBody);
    }

    private void importFlow(List<FlowNode> flowNodeList, UIFlow uiFlow) {
        // Updates a UIFlow to represent the current state of a list of model nodes

        List<UICell> uiCellList = uiFlow.getCells();

        for (UICell cell : uiCellList) {
            uiFlow.removeCell(cell);
        }

        for (FlowNode flowNode : flowNodeList) {
            UICell uiCell = generateCell(flowNode);
            uiFlow.addCell(uiCell, flowNodeList.indexOf(flowNode));
        }
    }

    private UICell generateCell(FlowNode in) {
        // Generates a visual representation of a model node input

        // Declare output variable
        UICell out;

        if (in instanceof IfNode) {
            out = new UIIf(in.getId());
        } else if (in instanceof WhileNode) {
            out = new UIWhile(in.getId());
        } else if (in instanceof AssignArrayNode assignArrayNode) {
            UIAssign uiAssignArray = new UIAssignArray(in.getId());

            // Set a listener to reflect any changes to the variable textField in the model
            uiAssignArray.getVarName().textProperty().addListener(e -> assignArrayNode.setIdentifier(uiAssignArray.getVarName().getText()));
            out = uiAssignArray;
        } else if (in instanceof AssignNode assignNode) {
            UIAssign uiAssign = new UIAssign(in.getId());

            // Set a listener to reflect any changes to the variable textField in the model
            uiAssign.getVarName().textProperty().addListener(e -> assignNode.setIdentifier(uiAssign.getVarName().getText()));
            out = uiAssign;
        } else if (in instanceof DeclareAssignNode declareAssignNode) {
            UIDeclareAssign uiDeclareAssign = new UIDeclareAssign(in.getId());

            // Set a listener to reflect any changes to the variable textField and type ComboBox in the model
            uiDeclareAssign.getTypes().getSelectionModel().selectedItemProperty().addListener(e -> {
                // Update declaration variable type in the model
                declareAssignNode.getSignature().setType(DataType.fromString(uiDeclareAssign.getTypes().getSelectionModel().getSelectedItem()));
            });
            uiDeclareAssign.getVarName().textProperty().addListener(e -> {
                // Update declaration variable identifier in the model
                declareAssignNode.getSignature().setIdentifier(uiDeclareAssign.getVarName().getText());
            });
            out = uiDeclareAssign;
        } else if (in instanceof OutputNode) {
            out = new UIOutput(in.getId());
        } else if (in instanceof ForNode forNode) {
            UIFor uiFor = new UIFor(in.getId());

            // Set a listener to reflect any changes to the counter variable textField in the model
            uiFor.getIdentifier().addListener(e -> forNode.setIdentifier(uiFor.getIdentifier().getValue()));
            out = uiFor;
        } else if (in instanceof CallNode callNode) {
            UICall uiCall = new UICall(in.getId());

            // Set a listener to reflect any changes to the identifier ComboBox in the model and update the number of parameters
            uiCall.getIdfrBox().getSelectionModel().selectedItemProperty().addListener(e -> {
                // Update identifier and number of parameters in the model
                String identifier = uiCall.getIdfrBox().getSelectionModel().getSelectedItem();
                callNode.setIdentifier(identifier);
                if (flowMap.get(identifier) != null) {
                    callNode.setNumberOfParameters(flowMap.get(identifier).getParameters().size());
                }

                // Update number of expression placeholders to match number of parameters
                uiCall.setSetNumberOfParameters(callNode.getExprs().size());

                // Set drag and drop handlers to expression placeholders
                for (ExprPlaceholder placeholder : uiCall.getExprPlaceholders()) {
                    setTopExprPlaceholderHandlers(placeholder);
                }

                selectedFlowRoot.updateLayout();
            });
            out = uiCall;
        } else if (in instanceof ReturnNode) {
            out = new UIReturn(in.getId());
        } else {
            // FlowNode hasn't been given a UI definition; Generate a cell that communicates this
            out = new UISquircle("UNDEFINED",UICell.IRRELEVANT_ID);
        }

        // Set event handlers on child UIFlows contained within the new cell so new cells can be added to it by the user
        if (out instanceof UIFlowContainer container) {
            for (UIFlow placeholder : container.getFlows()) {
                setCellPlaceholderHandlers(placeholder.getPlaceholder());
            }
        }

        // Set event handlers on expressions contained within the cell so new expressions can be added by the user
        if (out instanceof UIExprContainer container) {
            for (ExprPlaceholder placeholder : container.getExprPlaceholders()) {
                setTopExprPlaceholderHandlers(placeholder);
            }
        }

        // Populate cell with model information
        updateCell(out, in);

        // Inform the viewModel that cell is being dragged when a drag gesture is detected
        out.setOnDragDetected(e -> onCellDragged(e, out));

        // Highlight cell if another cell is being dragged over it
        out.setOnDragEntered(e -> {
            if (isNodeDragged()) {

            }
        });

        // Reset cell styling when another cell is no longer being dragged over it
        out.setOnDragExited(e -> {

            e.consume();
        });

        // Inform the viewModel that a cell has been drag and dropped on this cell
        out.setOnDragDropped(e -> onCellDropped(e,out));

        // Inform JavaFX that the drag gesture is valid if another cell is being dragged over it
        out.setOnDragOver(e -> {
            if (isNodeDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        return out;
    }

    private void updateCell(UICell uiCell, FlowNode flowNode) {
        // Populates input cell with model information from input node

        if (flowNode instanceof CallNode callNode && uiCell instanceof UICall uiCall) {
            // Include list of currently declared flow-chart function identifiers in the ComboBox
            List<String> functionIdentifiers = new ArrayList<>(List.copyOf(getFunctionIdentifiers()));
            functionIdentifiers.removeAll(uiCall.getIdfrBox().getItems());
            for (String identifier : functionIdentifiers) {
                uiCall.getIdfrBox().getItems().add(identifier);
            }

            // Select ComboBox item (identifier) that is stored in model CallNode
            uiCall.getIdfrBox().getSelectionModel().select(callNode.getIdentifier());

            if (!callNode.getIdentifier().equals("")) {
                String identifier = callNode.getIdentifier();
                // TODO double check this works
                if (flowMap.get(identifier) != null) {
                    // Update model to reflect number of parameters in the function
                    callNode.setNumberOfParameters(flowMap.get(identifier).getParameters().size());
                }
            }

            // Update the number of expression placeholders (function parameter inputs) to reflect model
            uiCall.setSetNumberOfParameters(callNode.getExprs().size());
            // Set drag and drop handlers to expression placeholders
            for (ExprPlaceholder placeholder : uiCall.getExprPlaceholders()) {
                setTopExprPlaceholderHandlers(placeholder);
            }
        }

        if (flowNode instanceof DeclareAssignNode declareAssignNode && uiCell instanceof UIDeclareAssign uiDeclareAssign) {
            // Update ComboBox to reflect FlowNode's type selection
            if (declareAssignNode.getSignature().getType() != null) {
                uiDeclareAssign.getTypes().getSelectionModel().select(declareAssignNode.getSignature().getType().toString());
            } else {
                uiDeclareAssign.getTypes().getSelectionModel().clearSelection();
            }

            // Update TextField to reflect FlowNode's variable identifier
            uiDeclareAssign.getVarName().setText(declareAssignNode.getSignature().getIdentifier());
        }

        if (flowNode instanceof AssignNode assignNode && uiCell instanceof UIAssign uiAssign) {
            // Update TextField to reflect FlowNode's variable identifier
            uiAssign.getVarName().setText(assignNode.getIdentifier());
        }

        if (flowNode instanceof ForNode forNode && uiCell instanceof UIFor uiFor) {
            // Update TextField to reflect FlowNode's counter variable identifier
            uiFor.getIdentifier().set(forNode.getIdentifier());
        }

        if (flowNode instanceof FlowContainer container && uiCell instanceof UIFlowContainer uiContainer) {
            // Import the Flow into each UIFlow
            List<Flow> flows = container.getFlows();
            for (int i = 0; i < flows.size(); i++) {
                importFlow(flows.get(i).getFlowNodes(), uiContainer.getFlows().get(i));
            }
        }

        if (flowNode instanceof ExprContainer container && uiCell instanceof UIExprContainer uiContainer) {
            // Generate each Expr in the container
            List<Expr> exprs = container.getExprs();
            for (int i = 0; i < exprs.size(); i++) {
                uiContainer.getExprPlaceholders().get(i).setExpr(generateExpr(exprs.get(i)));
            }
        }

        if (error != null && uiCell != null) {
            // If an error was found when converting to another language, update styling to communicate this
            if (error.getErrorType() == ErrorType.Node && error.getId() == uiCell.getCellID()) {
                uiCell.setStyleClass("error");
            } else {
                uiCell.setStyleClass("default");
            }
        } else if (uiCell != null) {
            uiCell.setStyleClass("default");
        }
    }

    private void setExprHandlers(UIExpr expr) {
        // Inform the viewModel that expr is being dragged when a drag gesture is detected
        expr.setOnDragDetected(e -> onExprDragged(e,expr));
    }

    private void setTopExprPlaceholderHandlers(ExprPlaceholder placeholder) {
        // Sets event handlers for expression placeholders that are not contained within another expression

        // Inform JavaFX that the drag gesture is valid if an expression is being dragged over it
        placeholder.setOnDragOver(e -> {
            if (isExprDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        // Highlight placeholder if an expression is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (isExprDragged()) {
                placeholder.setStroke(Color.BLUE);
            }
        });

        // Reset cell styling when an expression is no longer being dragged over it
        placeholder.setOnDragExited(e -> placeholder.setStroke(Color.BLACK));

        // Inform the viewModel that an expression has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> onExprPlaceholderDrop(e, placeholder));
    }

    private void setNestedExprPlaceholderHandlers(ExprPlaceholder placeholder) {
        // Sets event handlers for expressions placeholders that are contained within another expression

        // Inform JavaFX that the drag gesture is valid if a cell is being dragged over it
        placeholder.setOnDragOver(e -> {
            if (isExprDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        // Highlight placeholder if an expression is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (isExprDragged()) {
                placeholder.setStroke(Color.BLUE);
            }
        });

        // Reset cell styling when an expression is no longer being dragged over it
        placeholder.setOnDragExited(e -> placeholder.setStroke(Color.BLACK));

        // Inform the viewModel that an expression has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> onNestedExprPlaceholderDrop(e, placeholder));
    }

    private void setCellPlaceholderHandlers(CellPlaceholder placeholder) {
        // Sets event handlers for cell placeholders

        // Inform the viewModel that a cell has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> onCellPlaceholderDrop(e, placeholder));

        // Highlight placeholder if a cell is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (isNodeDragged()) {
                placeholder.setStrokeFill(Color.BLUE);
                e.consume();
            }
        });

        // Reset cell styling when a cell is no longer being dragged over it
        placeholder.setOnDragExited(e -> {
            placeholder.setStrokeFill(Color.BLACK);
            e.consume();
        });

        // Inform JavaFX that the drag gesture is valid if a cell is being dragged over it
        placeholder.setOnDragOver(e -> {
            if (isNodeDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });
    }

    private UIExpr generateExpr(Expr expr) {
        // Generate expression visualisation from model information

        // Declare output variable
        UIExpr out = null;

        if (expr instanceof BoolLit boolLit) {
            UIBool uiBool = new UIBool(expr.getId());

            // Set a listener to reflect any changes to the boolean ComboBox in the model
            uiBool.getComboxBoxProperty().addListener(e -> {
                String value = uiBool.getValue().toLowerCase(Locale.ROOT);
                boolLit.setVal(value.equals("true"));
            });
            out = uiBool;
        } else if (expr instanceof CharLit charLit) {
            UIChar uiChar = new UIChar(expr.getId());

            // Set a listener to reflect any changes to the char TextField in the model
            uiChar.getTextProperty().addListener(e -> {
                String value = uiChar.getValue().toLowerCase(Locale.ROOT);
                if (value.length() > 0) {
                    charLit.setVal(value.toCharArray()[0]);
                } else {
                    charLit.setVal('\u0000'); // Null char value
                }
            });
            out = uiChar;
        } else if (expr instanceof IntLit intLit) {
            UIInt uiInt = new UIInt(expr.getId());

            // Set a listener to reflect any changes to the integer TextField in the model
            uiInt.getTextProperty().addListener(e -> {
                String value = uiInt.getText().toLowerCase(Locale.ROOT);
                try {
                    int intValue = Integer.parseInt(value);
                    intLit.setVal(intValue);
                } catch (NumberFormatException exception) {
                    intLit.setVal(0);
                }
            });
            out = uiInt;
        } else if (expr instanceof  VarExpr varExpr) {
            UIVar uiVar = new UIVar(expr.getId());

            // Set a listener to reflect any changes to the identifier TextField in the model
            uiVar.getTextProperty().addListener(e -> varExpr.setIdentifier(uiVar.getValue()));
            out = uiVar;
        } else if (expr instanceof OpExpr opExpr) {
            UIOpExpr uiOpExpr = new UIOpExpr(expr.getId());

            // Set a listener to reflect any changes to the operator ComboBox in the model
            uiOpExpr.getComboxBoxProperty().addListener(e -> opExpr.setOp(Operator.fromString(uiOpExpr.getValue())));
            out = uiOpExpr;
        } else if (expr instanceof ModifierExpr modExpr) {
            UIModifierExpr uiModExpr = new UIModifierExpr(expr.getId());

            // Set a listener to reflect any changes to the modifier ComboBox in the model
            uiModExpr.getComboxBoxProperty().addListener(e -> modExpr.setModifier(Modifier.fromString(uiModExpr.getModifier())));
            out = uiModExpr;
        } else if (expr instanceof CallExpr callExpr) {
            UICallExpr uiCallExpr = new UICallExpr(expr.getId());

            for (String identifier : getFunctionIdentifiers()) {
                uiCallExpr.getComboBox().getItems().add(identifier);
            }

            // Set a listener to reflect any changes to the identifier ComboBox in the model and update the number of parameters
            uiCallExpr.getComboBox().getSelectionModel().selectedItemProperty().addListener(e -> {
                // Update identifier in model
                String identifier = uiCallExpr.getComboBox().getSelectionModel().getSelectedItem();
                callExpr.setIdentifier(identifier);

                // Check if identifier exists in the model
                if (flowMap.get(identifier) != null) {
                    callExpr.setNumberOfParameters(flowMap.get(identifier).getParameters().size());
                }

                // Update number of expression placeholders to match number of parameters
                uiCallExpr.setSetNumberOfParameters(callExpr.getExprs().size());

                // Set drag and drop handlers to expression placeholders
                for (ExprPlaceholder placeholder : uiCallExpr.getExprPlaceholders()) {
                    setNestedExprPlaceholderHandlers(placeholder);
                }

                selectedFlowRoot.updateLayout();
            });
            out = uiCallExpr;
        } else if (expr instanceof ArrayLit arrayLit) {
            out = new UIArray(arrayLit.getId());
        }

        // If an expression was generated and event handlers for drag events
        if (out != null) {
            setExprHandlers(out);
        }

        // Populate expression with model information
        updateExpr(out, expr);

        // Add drag and drop handlers to expressions placeholders nested within other expressions
        if (out instanceof UIExprContainer uiExprContainer) {
            for (ExprPlaceholder placeholder : uiExprContainer.getExprPlaceholders()) {
                setNestedExprPlaceholderHandlers(placeholder);
            }
        }

        return out;
    }

    private void updateExpr(UIExpr uiExpr, Expr expr) {
        // Populate UI fields with information from the model
        if (expr instanceof BoolLit boolLit && uiExpr instanceof UIBool uiBool) {
            uiBool.getComboBox().getSelectionModel().select(boolLit.getVal() ? "True" : "False");
        } else if (expr instanceof CharLit charLit && uiExpr instanceof UIChar uiChar) {
            uiChar.getTextProperty().set(Character.toString(charLit.getVal()));
        } else if (expr instanceof IntLit intLit && uiExpr instanceof UIInt uiInt) {
            uiInt.getTextProperty().set(Integer.toString(intLit.getVal()));
        } else if (expr instanceof VarExpr varExpr && uiExpr instanceof UIVar uiVar) {
            uiVar.getTextProperty().set(varExpr.getIdentifier());
        } else if (expr instanceof OpExpr opExpr && uiExpr instanceof UIOpExpr uiOpExpr) {
            if (opExpr.getOp() != null) {
                uiOpExpr.getComboBox().getSelectionModel().select(opExpr.getOp().toString());
            } else {
                uiOpExpr.getComboBox().getSelectionModel().clearSelection();
            }
        } else if (expr instanceof ModifierExpr modExpr && uiExpr instanceof UIModifierExpr uiModExpr) {
            if (modExpr.getModifier() != null) {
                uiModExpr.getComboBox().getSelectionModel().select(modExpr.getModifier().toString());
            } else {
                uiModExpr.getComboBox().getSelectionModel().clearSelection();
            }
        } else if (expr instanceof CallExpr callExpr && uiExpr instanceof UICallExpr uiCallExpr) {
            uiCallExpr.getComboBox().getSelectionModel().select(callExpr.getIdentifier());
        } else if (expr instanceof ArrayLit arrayLit && uiExpr instanceof UIArray uiArray) {
            uiArray.setNumberOfElements(arrayLit.getExprs().size() + 1);
        }

        if (expr instanceof ExprContainer exprContainer && uiExpr instanceof UIExprContainer uiExprContainer) {
            // If expression contains child expressions, generate child expressions
            List<Expr> exprList = exprContainer.getExprs();
            for (int i = 0; i < exprList.size(); i++) {
                uiExprContainer.getExprPlaceholders().get(i).setExpr(generateExpr(exprList.get(i)));
            }
        }

        if (error != null && uiExpr != null) {
            // If an error was found when converting to another language, update styling to communicate this
            if (error.getErrorType() == ErrorType.Expr && error.getId() == uiExpr.getExprId()) {
                uiExpr.setStyleClass("error");
            } else {
                uiExpr.setStyleClass("default");
            }
        } else if (uiExpr != null) {
            uiExpr.setStyleClass("default");
        }
    }

    // --- USER EVENTS ---------------------------------------------------------

    /**
     * Configures the viewModel for a drag gesture on a cell
     * @param e mouse event that triggered the drag gesture
     * @param source the UICell that was dragged
     */
    public void onCellDragged(MouseEvent e, UICell source) {
        // Reset existing drag attributes
        dragExpr = null;
        dragNode = null;

        // Find and assign the dragNode from id, then remove it from the model
        dragNode = selectedModel.getByID(source.getCellID());
        selectedModel.removeByID(source.getCellID());

        // Insert the cell's model id into the drag-board
        ClipboardContent content = new ClipboardContent();
        content.putString(Integer.toString(source.getCellID()));
        Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);
        dragboard.setContent(content);

        // Set dragView to display a snapshot of the UICell
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        dragboard.setDragView(source.snapshot(params,null));

        // Reflect changes to the model in the view
        update();
        e.consume();
    }

    /**
     * Updates the model when a drag-drop gesture occurs on a UICell
     * @param e DragEvent that triggered the drag-drop gesture
     * @param target the UICell the drag-drop gesture occurred on
     */
    public void onCellDropped(DragEvent e, UICell target) {
        if (isNodeDragged()) {
            // If a FlowNode is being dragged insert it before the corresponding node it was dropped on
            selectedModel.insertByID(dragNode, target.getCellID());
            dragNode = null;
        }

        // End the gesture and reflect changes to the model in the view
        e.setDropCompleted(true);
        update();
    }

    /**
     * Indicates if an expression is being dragged
     * @return boolean
     */
    public boolean isExprDragged() {
        return dragExpr != null;
    }

    /**
     * Indicates if a FlowNode is being dragged
     * @return boolean
     */
    public boolean isNodeDragged() {
        return dragNode != null;
    }

    /**
     * Updates the model when a drag-drop gesture occurs on a cell placeholder
     * @param e DragEvent that triggered the drag-drop gesture
     * @param placeholder the cell placeholder the drag-drop gesture occurred on
     */
    public void onCellPlaceholderDrop(DragEvent e, CellPlaceholder placeholder) {
        if (dragNode != null) {
            // If cell ID is greater than zero then it is within a nested Flow, otherwise it is in the top Flow
            if (placeholder.getCellID() >= 0) {
                // Find FlowNode which contains the Flow the placeholder represents
                FlowNode targetNode = selectedModel.getByID(placeholder.getCellID());

                // Insert dragNode into the first position of the found Flow
                if (targetNode instanceof FlowContainer container) {
                    container.getFlows().get(placeholder.getBranch()).insertByIndex(dragNode, 0);
                }

            } else {
                // Insert dragNode into the first position of the selected Flow
                selectedModel.insertByIndex(dragNode, 0);
            }

            // Reset drag attribute
            dragNode = null;
            e.consume();
        }

        // End the gesture and reflect changes to the model in the view
        e.setDropCompleted(true);
        update();
    }

    private void update() {
        importFlow(selectedModel.getFlowNodes(), selectedFlowBody);
        selectedFlowRoot.setPseudoVisible(pseudoVisible);

        // Update layout after graphics have been updated in JavaFX UI thread to ensure correct positioning
        Runnable task = () -> {
            try {
                sleep(50);
                Platform.runLater(() -> {
                    selectedFlowRoot.updateLayout();
                });
            } catch (InterruptedException e) { }
        };
        Thread updateThread = new Thread(task);
        updateThread.start();
    }

    /**
     * Configures the viewModel for a drag gesture on a new item dragged from the menu
     * @param e mouse event that triggered the drag gesture
     * @param newItem enumeration of the item that is being dragged
     */
    public void onNewCellDragged(MouseEvent e, NewItem newItem) {
        // Reset existing drag attributes
        dragNode = null;
        dragExpr = null;

        // Convert enumeration to new model object
        switch (newItem.getValue()) {
            case For:
                dragNode = new ForNode();
                break;
            case While:
                dragNode = new WhileNode();
                break;
            case If:
                dragNode = new IfNode();
                break;
            case Output:
                dragNode = new OutputNode();
                break;
            case Assign:
                dragNode = new AssignNode();
                break;
            case AssignArray:
                dragNode = new AssignArrayNode();
                break;
            case DeclareAssign:
                dragNode = new DeclareAssignNode();
                break;
            case Call:
                dragNode = new CallNode();
                break;
            case Return:
                dragNode = new ReturnNode();
                break;
            case BoolLit:
                dragExpr = new BoolLit();
                break;
            case CharLit:
                dragExpr = new CharLit();
                break;
            case IntLit:
                dragExpr = new IntLit();
                break;
            case OpExpr:
                dragExpr = new OpExpr();
                break;
            case VarExpr:
                dragExpr = new VarExpr();
                break;
            case ModifierExpr:
                dragExpr = new ModifierExpr();
                break;
            case CallExpr:
                dragExpr = new CallExpr();
                break;
            case ArrayLit:
                dragExpr = new ArrayLit();
                break;
            default:
                break;
        }

        // Insert the new item's model id into the drag-board
        ClipboardContent content = new ClipboardContent();

        if (dragNode != null) {
            content.putString(Integer.toString(dragNode.getId()));
        } else if (dragExpr != null) {
            content.putString(Integer.toString(dragExpr.getId()));
        }

        Dragboard dragboard = newItem.startDragAndDrop(TransferMode.ANY);
        dragboard.setContent(content);

        // Set dragView to display a snapshot of the new item
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        dragboard.setDragView(newItem.snapshot(params,null));

        // Reflect changes to the model in the view
//        signalUpdate();
        e.consume();
    }

    /**
     * Updates the model when a drag-drop gesture occurs on an expression placeholder within a UICell
     * @param e DragEvent that triggered the drag-drop gesture
     * @param placeholder the expression placeholder the drag-drop gesture occurred on
     */
    public void onExprPlaceholderDrop(DragEvent e, ExprPlaceholder placeholder) {
        if (dragExpr != null) {
            // Find FlowNode which contains the expression placeholder
            FlowNode parentNode = selectedModel.getByID(placeholder.getExprId());

            // Insert dragExpr into model FlowNode
            if (parentNode instanceof ExprContainer container) {
                container.setExpr(dragExpr, placeholder.getBranch());
            }

            // Reset drag attribute
            e.consume();
            dragExpr = null;
        }

        // Reflect changes to the model in the view
        update();
    }

    /**
     * Updates the model when a drag-drop gesture occurs on an expression placeholder within another UI expression
     * @param e DragEvent that triggered the drag-drop gesture
     * @param placeholder the expression placeholder the drag-drop gesture occurred on
     */
    public void onNestedExprPlaceholderDrop(DragEvent e, ExprPlaceholder placeholder) {
        if (dragExpr != null) {
            // Find expression which contains the expression placeholder
            Expr parentExpr = selectedModel.getExprParentById(placeholder.getExprId());

            // Insert dragExpr into model expression
            if (parentExpr instanceof ExprContainer container) {
                container.setExpr(dragExpr, placeholder.getBranch());
            }

            // Reset drag attribute
            e.consume();
            dragExpr = null;
        }

        // Reflect changes to the model in the view
        update();
    }

    /**
     * Configures the viewModel for a drag gesture on an expression
     * @param e
     * @param source
     */
    public void onExprDragged(MouseEvent e, UIExpr source) {
        // Reset existing drag attributes
        dragExpr = null;
        dragNode = null;

        // Find and assign the dragExpr from id, then remove it from the model
        dragExpr = selectedModel.getExprById(source.getExprId());
        selectedModel.removeExprById(source.getExprId());

        // Insert the expr's model id into the drag-board
        ClipboardContent content = new ClipboardContent();
        content.putString(Integer.toString(source.getExprId()));
        Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);
        dragboard.setContent(content);

        // Set dragView to display a snapshot of the UICell
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        dragboard.setDragView(source.snapshot(params,null));

        // Reflect changes to the model in the view
        update();
        e.consume();
    }

    /**
     * Changes the model that viewModel methods will manipulate according to input identifier
     * @param identifier identifier of the Flow model to select
     */
    public void selectModel(String identifier) {
        selectedModel = flowMap.get(identifier);
        selectedFlowRoot = functionRootMap.get(identifier);
        selectedFlowBody = functionFlowMap.get(identifier);

        update();
    }

    /**
     * Creates a new model from the parameters
     * @param identifier Flow's identifier
     * @param returnType DataType that Flow returns
     * @param paramIdentifiers Ordered list of parameter's string identifiers
     * @param paramTypes Ordered list of parameter's string DataTypes
     */
    public void addFunction(String identifier, String returnType, List<String> paramIdentifiers, List<String> paramTypes) {
        // Create typed identifier for the Flow (identifier and return type)
        Signature signature = new Signature(identifier, DataType.fromString(returnType));

        // Convert list of parameter identifiers and types to list of typed identifiers
        List<Signature> parameters = new ArrayList<>();
        for (int i = 0; i < Math.min(paramIdentifiers.size(), paramTypes.size()); i++) {
            parameters.add(new Signature(paramIdentifiers.get(i), DataType.fromString(paramTypes.get(i))));
        }

        // Construct new flow and add it to the flowMap
        flowMap.put(signature.getIdentifier(), new Flow(signature, parameters));
        addUIFlow(signature.getIdentifier());
    }

    /**
     * Updates the signature and parameter typed identifiers of the currently selected model
     * @param identifer Flow's new identifier
     * @param returnType New DataType that Flow returns
     * @param paramIdentifiers New ordered list of parameter's string identifiers
     * @param paramTypes New ordered list of parameter's string DataTypes
     */
    public void editFunction(String identifer, String returnType, List<String> paramIdentifiers, List<String> paramTypes) {
        Signature signature = new Signature(identifer, DataType.fromString(returnType));
        List<Signature> parameters = new ArrayList<>();

        for (int i = 0; i < Math.min(paramIdentifiers.size(), paramTypes.size()); i++) {
            parameters.add(new Signature(paramIdentifiers.get(i), DataType.fromString(paramTypes.get(i))));
        }

        flowMap.remove(selectedModel.getSignature().getIdentifier());
        flowMap.put(identifer, selectedModel);

        UIFlow temp = functionFlowMap.get(selectedModel.getSignature().getIdentifier());
        functionFlowMap.remove(selectedModel.getSignature().getIdentifier());
        functionFlowMap.put(identifer, temp);

        temp = functionRootMap.get(selectedModel.getSignature().getIdentifier());
        functionRootMap.remove(selectedModel.getSignature().getIdentifier());
        functionRootMap.put(identifer, temp);

        selectedModel.setSignature(signature);
        selectedModel.setParameters(parameters);
    }

    /**
     * Removes references to the model which corresponds to the input identifier
     */
    public void removeFunction() {
        String identifier = getSelectedIdentifier();
        flowMap.remove(identifier);
        functionRootMap.remove(identifier);
        functionFlowMap.remove(identifier);

        // Removes CallNodes with given identifier
        for (Flow flow : flowMap.values()) {
            for (FlowNode node : flow.getFlowNodes()) {
                if (node instanceof CallNode callNode) {
                    if (callNode.getIdentifier().equals(identifier)) {
                        flow.removeByID(callNode.getId());
                    }
                } else if (node instanceof ExprContainer exprContainer) {
                    for (Expr expr : exprContainer.getExprs()) {
                        removeFunction(identifier, flow, expr);
                    }
                } else if (node instanceof FlowContainer flowContainer) {
                    for (Flow containedFlow : flowContainer.getFlows()) {
                        removeFunction(identifier, flow, containedFlow);
                    }
                }
            }
        }

        selectModel("main");
    }

    private void removeFunction(String identifier, Flow searchFlow, Expr expr) {
        if (expr instanceof CallExpr callExpr) {
            if (callExpr.getIdentifier().equals(identifier)) {
                searchFlow.removeExprById(callExpr.getId());
            }
        } else if (expr instanceof ExprContainer exprContainer) {
            for (Expr containedExpr : exprContainer.getExprs()) {
                removeFunction(identifier, searchFlow, containedExpr);
            }
        }
    }

    private void removeFunction(String identifier, Flow searchFlow, Flow flow) {
        for (FlowNode node : flow.getFlowNodes()) {
            if (node instanceof CallNode callNode) {
                if (callNode.getIdentifier().equals(identifier)) {
                    searchFlow.removeByID(callNode.getId());
                }
            } else if (node instanceof ExprContainer exprContainer) {
                for (Expr expr : exprContainer.getExprs()) {
                    removeFunction(identifier, searchFlow, expr);
                }
            } else if (node instanceof FlowContainer flowContainer) {
                for (Flow containedFlow : flowContainer.getFlows()) {
                    removeFunction(identifier, searchFlow, containedFlow);
                }
            }
        }
    }

    /**
     * Returns a description of the selected model
     * @return String description
     */
    public String getFunctionDescription() {
        Signature signature = selectedModel.getSignature();
        List<Signature> parameters = selectedModel.getParameters();

        // Build function description
        StringBuilder sb = new StringBuilder();
        sb.append("Function " + signature.getIdentifier() + " returns " + signature.getType().toString() + " ");

        // Build parameter description
        String prefix = "Inputs: ";
        for (Signature param : parameters) {
            sb.append(prefix);
            prefix = ", ";
            sb.append(param.getType() + " " + param.getIdentifier());
        }

        return sb.toString();
    }

    /**
     * @return identifier of the selected model
     */
    public String getSelectedIdentifier() {
        return selectedModel.getSignature().getIdentifier();
    }

    /**
     * @return return type of the selected model
     */
    public String getSelectedReturnType() {
        return selectedModel.getSignature().getType().toString();
    }

    /**
     * @return Ordered list of parameter identifiers of the selected model
     */
    public List<String> getSelectedParameterIdentifiers() {
        List<String> paramIdentifiers = new ArrayList<>();

        for (Signature signature : selectedModel.getParameters()) {
            paramIdentifiers.add(signature.getIdentifier());
        }

        return paramIdentifiers;
    }

    /**
     * @return Ordered list of parameter types of the selected model
     */
    public List<String> getSelectedParameterTypes() {
        List<String> paramIdentifiers = new ArrayList<>();

        for (Signature signature : selectedModel.getParameters()) {
            paramIdentifiers.add(signature.getType().toString());
        }

        return paramIdentifiers;
    }

    /**
     * @return List of identifiers for models stored in the viewModel
     */
    public List<String> getFunctionIdentifiers() {
        return flowMap.keySet().stream().toList();
    }

    /**
     * Parses, analyses, and converts all models that exist in the system
     * Also updates the getError() method to return any semantic errors found
     * @param language name of the language to convert models to
     */
    public void convertModel(String language) {
        // Build flow-language code from models
        StringBuilder source = new StringBuilder();
        for (Flow flow : flowMap.values()) {
            source.append(flow.toFunctionCode());
        }

        ParseTree tree;

        try {
            // Pass intermediary code into lexer and parser
            CharStream input = CharStreams.fromString(source.toString());
            FlowGrammarLexer lexer = new FlowGrammarLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            FlowGrammarParser parser = new FlowGrammarParser(tokens);

            // Retrieve AST
            tree = parser.prog();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Parse Error. Please make sure all fields have been completed");
            alert.showAndWait();
            return;
        }

        try {
            // Analyse AST
            FlowGrammarAnalyser analyser = new FlowGrammarAnalyser();
            analyser.visit(tree);
        } catch (FlowException e) {
            // Store and report error
            error = e;
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
            update();
            return;
        }

        String extension = (language.equals("python") ? "py" : language);

        // Show dialog for choosing file location
        // Create prompt to specify a file name and location to save models
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save " + language + " source code");
        // Filter so file is saved with .flow extension
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(language + "source code(*." + extension + ")", "*." + extension));

        // Show dialog and store returned File
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (file.getName().endsWith("." + extension)) {
                try {
                    // Convert AST to source code
                    String sourceCode;
                    if (language.equals("java")) {
                        JavaConverter javaConverter = new JavaConverter();
                        sourceCode = javaConverter.visit(tree);
                    } else {
                        PythonConverter pythonConverter = new PythonConverter();
                        sourceCode = pythonConverter.visit(tree);
                    }

                    // Print java source code to file
                    PrintWriter fileStream = new PrintWriter(file);
                    fileStream.print(sourceCode);

                    fileStream.close();

                    // Notify user of success
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Source code saved succesfully.");
                    successAlert.showAndWait();
                } catch (IOException e) {
                    // Notify user of failure
                    Alert failAlert = new Alert(Alert.AlertType.ERROR);
                    failAlert.setContentText("Failed to save source code.");
                    failAlert.showAndWait();
                }
            } else {
                // Notify user of success
                Alert successAlert = new Alert(Alert.AlertType.ERROR);
                successAlert.setContentText("Source code not saved. Please use the '." + extension + "' file extension.");
                successAlert.showAndWait();
            }
        }
    }

    /**
     * @return FlowException found when analysing models (null if none were found)
     */
    public FlowException getError() {
        return error;
    }

    /**
     * Indicates if a model with the identifier given exists
     * @param identifier identifier to check
     * @return if identifier exists
     */
    public boolean hasIdentifier(String identifier) {
        return flowMap.keySet().contains(identifier);
    }

    /**
     * Creates a file explorer prompt and saves the models (using serialization)
     * to the path selected.
     */
    public void saveModel() {
        // Create prompt to specify a file name and location to save models
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Flow Program");
        // Filter so file is saved with .flow extension
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Flow program(*.flow)", "*.flow"));

        // Show dialog and store returned File
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            if (file.getName().endsWith(".flow")) {
                try {
                    FileOutputStream fileStream = new FileOutputStream(file);
                    ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);

                    // Serialize FlowFile and save to file
                    FlowFile fileData = new FlowFile(flowMap, FlowNode.getIdCounter(), Expr.getIdCounter());
                    objectStream.writeObject(fileData);

                    objectStream.close();
                    fileStream.close();

                    // Notify user of success
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Flow program saved successfully.");
                    successAlert.showAndWait();
                } catch (IOException e) {
                    // Notify user of failure
                    Alert failAlert = new Alert(Alert.AlertType.ERROR);
                    failAlert.setContentText("Failed to save flow program.");
                    failAlert.showAndWait();
                }
            } else {
                // Notify user of success
                Alert successAlert = new Alert(Alert.AlertType.ERROR);
                successAlert.setContentText("Flow program not saved. Please use the '.flow' file extension.");
                successAlert.showAndWait();
            }
        }
    }

    public void loadModel() {
        // Create prompt to choose a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Flow Program");
        // Filter so file is saved with .flow extension
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Flow program(*.flow)", "*.flow"));

        // Show dialog and store returned File
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            if (file.getName().endsWith(".flow")) {
                try {
                    FileInputStream fileStream = new FileInputStream(file);
                    ObjectInputStream objectStream = new ObjectInputStream(fileStream);

                    // De-serialize file's attributes and populate view-model attributes
                    FlowFile fileData = (FlowFile) objectStream.readObject();
                    flowMap = fileData.getFlowMap();
                    FlowNode.setIdCounter(fileData.getMaxNodeId());
                    Expr.setIdCounter(fileData.getMaxExprId());

                    // Add UIFlows for each imported flow-chart model
                    for (String identifier : flowMap.keySet()) {
                        addUIFlow(identifier);
                    }

                    objectStream.close();
                    fileStream.close();

                    // Notify user of success
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setContentText("Flow program loaded.");
                    successAlert.showAndWait();
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    // Notify user of failure
                    Alert failAlert = new Alert(Alert.AlertType.ERROR);
                    failAlert.setContentText("Failed to load flow program.");
                    failAlert.showAndWait();
                }
            } else {
                // Notify user of success
                Alert successAlert = new Alert(Alert.AlertType.ERROR);
                successAlert.setContentText("Invalid file format. Only files with the '.flow' extension are supported");
                successAlert.showAndWait();
            }
        }
    }

    /**
     * Inverts the visibility of the pseudo code label
     */
    public void togglePseudoVisible() {
        this.pseudoVisible = !this.pseudoVisible;
        update();
    }

    public boolean isPseudoVisible() {
        return pseudoVisible;
    }
}
