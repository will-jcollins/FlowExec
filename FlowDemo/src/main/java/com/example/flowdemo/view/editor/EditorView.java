package com.example.flowdemo.view.editor;

import com.example.flowdemo.model.flow.expression.*;
import com.example.flowdemo.model.flow.nodes.*;
import com.example.flowdemo.model.transpiler.ErrorType;
import com.example.flowdemo.model.transpiler.FlowException;
import com.example.flowdemo.view.editor.cell.*;
import com.example.flowdemo.view.editor.expr.*;
import com.example.flowdemo.view.editor.menu.FunctionOptionsStage;
import com.example.flowdemo.view.editor.menu.ItemType;
import com.example.flowdemo.view.editor.menu.NewItem;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

/**
 * Class responsible for generating the visual elements of the flow-chart editing view
 */
public class EditorView extends BorderPane {
    // --- Vocabulary ---
    // Cell - UI element that visualises a FlowNode in the model

    private EditorViewModel viewModel; // ViewModel responsible for bridging view to the model
    private SimpleBooleanProperty updateSignal; // Property that changes when the model has changed
                                                // and signals that the view needs to be updated (linked to viewModel)
    private Map<String,UIFlow> functionRootMap; // Maps function name to all of its corresponding visual elements
    private Map<String,UIFlow> functionFlowMap; // Maps function name to all of its visual elements represented in the model
    private String selectedFlow; // String key for the UIFlow that is currently visible
    private UIFlow selectedFlowRoot; // UIFlow mapped in functionRootMap that has selectedFlow as its key
    private UIFlow selectedFlowBody; // UIFlow mapped in functionFlowMap that has selectedFlow as its key
    private TabPane editorRoot; // ViewPort for the flow-chart editor window
    private Text functionDescription; // Label that describes the flow-chart currently visible

    // Toolbar buttons
    private Button addButton;
    private Button editButton;
    private Button removeButton;
    public EditorView(EditorViewModel viewModel) {
        // Set attributes
        this.viewModel = viewModel;
        this.updateSignal = viewModel.updateSignalProperty();

        // Initialise flows
        functionRootMap = new HashMap<>();
        functionFlowMap = new HashMap<>();
        selectedFlowBody = new UIFlow(UICell.IRRELEVANT_ID);
        selectedFlowRoot = new UIFlow(UICell.IRRELEVANT_ID);
        functionRootMap.put("main", selectedFlowRoot);
        functionFlowMap.put("main", selectedFlowBody);

        // Initialise editor view
        ZoomableScrollPane mainView = new ZoomableScrollPane(selectedFlowRoot);
        editorRoot = new TabPane();
        editorRoot.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Initialise main function tab
        Tab mainTab = new Tab("main", mainView);
        editorRoot.getTabs().add(mainTab);
        editorRoot.getSelectionModel().select(mainTab);
        editorRoot.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (editorRoot.getSelectionModel().getSelectedItem() != null) {
                selectFlow(editorRoot.getSelectionModel().getSelectedItem().getText());
            }
        });
        setCenter(editorRoot);

        // Add start and end cells to main's root flow
        UISquircle start = new UISquircle("Start", UICell.IRRELEVANT_ID);
        start.setStyleClass("default");
        start.setOnMouseClicked(e -> selectedFlowRoot.updateLayout());
        UISquircle end = new UISquircle("End", UICell.IRRELEVANT_ID);
        end.setStyleClass("default");
        selectedFlowRoot.addCell(start, 0);
        selectedFlowRoot.addCell(selectedFlowBody, 1);
        selectedFlowRoot.addCell(end, 2);

        // Add event handlers to main's flowBody's cell placeholder
        setCellPlaceholderHandlers(selectedFlowBody.getPlaceholder());

        // Initialise new elements list
        VBox newElementsList = new VBox();
        newElementsList.setSpacing(20.0d);
        newElementsList.setAlignment(Pos.CENTER);
        newElementsList.setPadding(new Insets(20.0d, 20.0d, 20.0d, 20.0d));
        newElementsList.setId("item-list");
        setLeft(newElementsList);

//        // Add SplitPane to allow resizable elements list
//        SplitPane centerPane = new SplitPane();
//        centerPane.getItems().add(new ScrollPane(newElementsList));
//        centerPane.getItems().add(editorRoot);
//        setCenter(centerPane);

        // Add new elements
        for (ItemType type : ItemType.values()) {
            NewItem newItem = new NewItem(type);
            newItem.setOnDragDetected(e -> viewModel.onNewCellDragged(e, newItem));
//            Platform.runLater(() -> {
//                newItem.getImageView().fitWidthProperty().bind(centerPane.getDividers().get(0).positionProperty().multiply(centerPane.widthProperty()));
//                newItem.getImageView().fitHeightProperty().bind(centerPane.getDividers().get(0).positionProperty().multiply(centerPane.heightProperty()));
//
//            });
            newElementsList.getChildren().add(newItem);
        }

        // Add toolbar
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));

        Button saveButton = new Button("Save");
        saveButton.setOnMouseClicked(e -> viewModel.saveModel());
        toolbar.getChildren().add(saveButton);

        Button loadButton = new Button("Load");
        loadButton.setOnMouseClicked(e -> {
            viewModel.loadModel();

            // Create new tabs with new UIFlows
            editorRoot.getTabs().clear();

            List<String> identifiers = viewModel.getFunctionIdentifiers();
            for (int i = identifiers.size() - 1; i >= 0; i--) {
                String identifier = identifiers.get(i);
                // Create function's UIFlows
                UIFlow flowRoot = new UIFlow(UICell.IRRELEVANT_ID);
                UIFlow flowBody = new UIFlow(UICell.IRRELEVANT_ID);
                UISquircle startSquircle = new UISquircle("Start", UICell.IRRELEVANT_ID);
                UISquircle endSquircle = new UISquircle("End", UICell.IRRELEVANT_ID);
                flowRoot.addCell(startSquircle, 0);
                flowRoot.addCell(flowBody, 1);
                flowRoot.addCell(endSquircle, 2);

                // Add function to View maps
                functionRootMap.put(identifier, flowRoot);
                functionFlowMap.put(identifier, flowBody);

                // Set event handlers for root UIFlow placeholder
                setCellPlaceholderHandlers(flowBody.getPlaceholder());

                // Create ViewPort and add it to the editor TabPane
                ZoomableScrollPane editorView = new ZoomableScrollPane(flowRoot);
                editorView.setId("grid");
                Tab tab = new Tab(identifier, editorView);
                editorRoot.getTabs().add(tab);
            }

            selectFlow("main");
        });
        toolbar.getChildren().add(loadButton);

        addButton = new Button("Add function");
        addButton.setOnMouseClicked(e -> addFunction());
        toolbar.getChildren().add(addButton);

        removeButton = new Button("Remove Function");
        removeButton.setOnMouseClicked(e -> removeFunction(selectedFlow));
        toolbar.getChildren().add(removeButton);

        editButton = new Button("Edit Function");
        editButton.setOnMouseClicked(e -> editFunction());
        toolbar.getChildren().add(editButton);

        Button compileButton = new Button("Compile");
        compileButton.setOnMouseClicked(e -> {
            boolean complete = true;
            for (UIFlow flow : functionRootMap.values()) {
                complete = flow.isComplete() && complete;
            }

            if (complete) {
                viewModel.analyseModels();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields are not completed.");
                alert.showAndWait();
            }
        });
        toolbar.getChildren().add(compileButton);

        setTop(toolbar);

        // Add function description
        functionDescription = new Text("   " + viewModel.getFunctionDescription());
        HBox functionDescriptionContainer = new HBox(functionDescription);
        functionDescriptionContainer.setId("description");
        setBottom(functionDescriptionContainer);

        // Select main as the currently visible Flow-Chart (only one that should currently exist)
        selectFlow("main");

        // Update to reflect model
        importFlow(viewModel.getFlowNodes(), selectedFlowBody);

        // Add listener to binding
        updateSignal.addListener((observableValue, oldValue, newValue) -> importFlow(viewModel.getFlowNodes(), functionFlowMap.get(selectedFlow)));
    }


    private void importFlow(List<FlowNode> flowNodeList, UIFlow uiFlow) {
        // Updates a UIFlow to represent the current state of a list of model nodes

        List<UICell> uiCellList = uiFlow.getCells();

        // Declare helper variables for loop
        FlowNode currentNode;
        UICell currentCell;
        int cellIndex = 0; // Tracks the current position on the flow object rather than in the cellList

        for (int i = 0; i < flowNodeList.size(); i++) {
            if (uiCellList.size() <= i) {
                // End of cells has been reached whilst nodes still remain, add remaining nodes to UI
                while (i < flowNodeList.size()) {
                    currentNode = flowNodeList.get(i);
                    uiFlow.addCell(generateCell(currentNode), cellIndex);
                    i += 1;
                    cellIndex += 1;
                }
                break; // Nothing left to do, break out of for loop
            }

            currentNode = flowNodeList.get(i);
            currentCell = uiCellList.get(i);

            if (currentNode.getId() == currentCell.getCellID()) {
                // Cell and node match, make sure information is correct
                updateCell(currentCell, currentNode);
                cellIndex += 1;
            } else if (currentNode.getId() != currentCell.getCellID()) {
                // Cell and node do not match, insert new cell
                UICell newCell = generateCell(currentNode);
                uiCellList.add(cellIndex, newCell);
                uiFlow.addCell(generateCell(currentNode),cellIndex);
                cellIndex += 1;
            }
        }

        if (flowNodeList.size() < uiCellList.size()) {
            // End of nodes has been reached whilst cells still remain, remove remaining cells

            while (cellIndex < uiCellList.size()) {
                currentCell = uiCellList.get(cellIndex);
                uiFlow.removeCell(currentCell);
                cellIndex += 1;
            }
        }

        // Reflect changes outside the UI thread to prevent blocking
        Runnable a = () -> {
            try {
                sleep(50);
                Platform.runLater(() -> selectedFlowRoot.updateLayout());
            } catch (InterruptedException e) { }
        };
        Thread b = new Thread(a);
        b.start();
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
            uiAssignArray.getVarName().textProperty().addListener(e -> viewModel.updateAssignNodeIdentifier(assignArrayNode, uiAssignArray.getVarName().getText()));
            out = uiAssignArray;
        } else if (in instanceof AssignNode assignNode) {
            UIAssign uiAssign = new UIAssign(in.getId());

            // Set a listener to reflect any changes to the variable textField in the model
            uiAssign.getVarName().textProperty().addListener(e -> viewModel.updateAssignNodeIdentifier(assignNode, uiAssign.getVarName().getText()));
            out = uiAssign;
        } else if (in instanceof DeclareAssignNode declareAssignNode) {
            UIDeclareAssign uiDeclareAssign = new UIDeclareAssign(in.getId());

            // Set a listener to reflect any changes to the variable textField and type ComboBox in the model
            uiDeclareAssign.getTypes().getSelectionModel().selectedItemProperty().addListener(e -> viewModel.updateDeclareAssignNodeType(declareAssignNode, uiDeclareAssign.getTypes().getSelectionModel().getSelectedItem()));
            uiDeclareAssign.getVarName().textProperty().addListener(e -> viewModel.updateDeclareAssignNodeIdentifier(declareAssignNode, uiDeclareAssign.getVarName().getText()));
            out = uiDeclareAssign;
        } else if (in instanceof OutputNode) {
            out = new UIOutput(in.getId());
        } else if (in instanceof ForNode forNode) {
            UIFor uiFor = new UIFor(in.getId());

            // Set a listener to reflect any changes to the counter variable textField in the model
            uiFor.getIdentifier().addListener(e -> viewModel.updateForIdentifier(forNode, uiFor.getIdentifier().getValue()));
            out = uiFor;
        } else if (in instanceof CallNode callNode) {
            UICall uiCall = new UICall(in.getId());

            // Set a listener to reflect any changes to the identifier ComboBox in the model and update the number of parameters
            uiCall.getIdfrBox().getSelectionModel().selectedItemProperty().addListener(e -> {
                // Update identifier in model
                viewModel.updateCallNodeIdentifier(callNode, uiCall.getIdfrBox().getSelectionModel().getSelectedItem());

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
        out.setOnDragDetected(e -> viewModel.onCellDragged(e, out));

        // Highlight cell if another cell is being dragged over it
        out.setOnDragEntered(e -> {
            if (viewModel.isNodeDragged()) {

            }
        });

        // Reset cell styling when another cell is no longer being dragged over it
        out.setOnDragExited(e -> {

            e.consume();
        });

        // Inform the viewModel that a cell has been drag and dropped on this cell
        out.setOnDragDropped(e -> viewModel.onCellDropped(e,out));

        // Inform JavaFX that the drag gesture is valid if another cell is being dragged over it
        out.setOnDragOver(e -> {
            if (viewModel.isNodeDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        return out;
    }

    private void updateCell(UICell uiCell, FlowNode flowNode) {
        // Populates input cell with model information from input node

        if (flowNode instanceof CallNode callNode && uiCell instanceof UICall uiCall) {
            // Include list of currently declared flow-chart function identifiers in the ComboBox
            List<String> functionIdentifiers = new ArrayList<>(List.copyOf(viewModel.getFunctionIdentifiers()));
            functionIdentifiers.removeAll(uiCall.getIdfrBox().getItems());
            for (String identifier : functionIdentifiers) {
                uiCall.getIdfrBox().getItems().add(identifier);
            }

            // Select ComboBox item (identifier) that is stored in model CallNode
            uiCall.getIdfrBox().getSelectionModel().select(callNode.getIdentifier());

            if (!callNode.getIdentifier().equals("")) {
                viewModel.updateCallNodeIdentifier(callNode, callNode.getIdentifier());
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

        FlowException error = viewModel.getError();
        if (error != null) {
            // If an error was found when converting to another language, update styling to communicate this
            if (error.getErrorType() == ErrorType.Node && error.getId() == uiCell.getCellID()) {
                uiCell.setStyleClass("error");
            } else {
                uiCell.setStyleClass("default");
            }
        } else {
            uiCell.setStyleClass("default");
        }
    }

    private void setExprHandlers(UIExpr expr) {
        // Inform the viewModel that expr is being dragged when a drag gesture is detected
        expr.setOnDragDetected(e -> viewModel.onExprDragged(e,expr));
    }

    private void setTopExprPlaceholderHandlers(ExprPlaceholder placeholder) {
        // Sets event handlers for expression placeholders that are not contained within another expression

        // Inform JavaFX that the drag gesture is valid if an expression is being dragged over it
        placeholder.setOnDragOver(e -> {
            if (viewModel.isExprDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        // Highlight placeholder if an expression is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (viewModel.isExprDragged()) {
                placeholder.setStroke(Color.BLUE);
            }
        });

        // Reset cell styling when an expression is no longer being dragged over it
        placeholder.setOnDragExited(e -> placeholder.setStroke(Color.BLACK));

        // Inform the viewModel that an expression has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> viewModel.onExprPlaceholderDrop(e, placeholder));
    }

    private void setNestedExprPlaceholderHandlers(ExprPlaceholder placeholder) {
        // Sets event handlers for expressions placeholders that are contained within another expression

        // Inform JavaFX that the drag gesture is valid if a cell is being dragged over it
        placeholder.setOnDragOver(e -> {
            if (viewModel.isExprDragged()) {
                e.acceptTransferModes(TransferMode.ANY);
            }
        });

        // Highlight placeholder if an expression is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (viewModel.isExprDragged()) {
                placeholder.setStroke(Color.BLUE);
            }
        });

        // Reset cell styling when an expression is no longer being dragged over it
        placeholder.setOnDragExited(e -> placeholder.setStroke(Color.BLACK));

        // Inform the viewModel that an expression has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> viewModel.onNestedExprPlaceholderDrop(e, placeholder));
    }

    private void setCellPlaceholderHandlers(CellPlaceholder placeholder) {
        // Sets event handlers for cell placeholders

        // Inform the viewModel that a cell has been drag and dropped on this placeholder
        placeholder.setOnDragDropped(e -> viewModel.onCellPlaceholderDrop(e, placeholder));

        // Highlight placeholder if a cell is being dragged over it
        placeholder.setOnDragEntered(e -> {
            if (viewModel.isNodeDragged()) {
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
            if (viewModel.isNodeDragged()) {
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
            uiBool.getComboxBoxProperty().addListener(e -> viewModel.updateBoolExpr(boolLit, uiBool.getValue()));
            out = uiBool;
        } else if (expr instanceof CharLit charLit) {
            UIChar uiChar = new UIChar(expr.getId());

            // Set a listener to reflect any changes to the char TextField in the model
            uiChar.getTextProperty().addListener(e -> viewModel.updateCharExpr(charLit, uiChar.getValue()));
            out = uiChar;
        } else if (expr instanceof IntLit intLit) {
            UIInt uiInt = new UIInt(expr.getId());

            // Set a listener to reflect any changes to the integer TextField in the model
            uiInt.getTextProperty().addListener(e -> viewModel.updateIntExpr(intLit, uiInt.getText()));
            out = uiInt;
        } else if (expr instanceof  VarExpr varExpr) {
            UIVar uiVar = new UIVar(expr.getId());

            // Set a listener to reflect any changes to the identifier TextField in the model
            uiVar.getTextProperty().addListener(e -> viewModel.updateVarExpr(varExpr, uiVar.getValue()));
            out = uiVar;
        } else if (expr instanceof OpExpr opExpr) {
            UIOpExpr uiOpExpr = new UIOpExpr(expr.getId());

            // Set a listener to reflect any changes to the operator ComboBox in the model
            uiOpExpr.getComboxBoxProperty().addListener(e -> viewModel.updateOpExpr(opExpr, uiOpExpr.getValue()));
            out = uiOpExpr;
        } else if (expr instanceof ModifierExpr modExpr) {
            UIModifierExpr uiModExpr = new UIModifierExpr(expr.getId());

            // Set a listener to reflect any changes to the modifier ComboBox in the model
            uiModExpr.getComboxBoxProperty().addListener(e -> viewModel.updateModExpr(modExpr, uiModExpr.getModifier()));
            out = uiModExpr;
        } else if (expr instanceof CallExpr callExpr) {
            UICallExpr uiCallExpr = new UICallExpr(expr.getId());

            for (String identifier : viewModel.getFunctionIdentifiers()) {
                uiCallExpr.getComboBox().getItems().add(identifier);
            }

            // Set a listener to reflect any changes to the identifier ComboBox in the model and update the number of parameters
            uiCallExpr.getComboBox().getSelectionModel().selectedItemProperty().addListener(e -> {
                // Update identifier in model
                viewModel.updateCallExprIdentifier(callExpr, uiCallExpr.getComboBox().getSelectionModel().getSelectedItem());

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
    }

    private void addFunction() {
        // Build and show JavaFX stage that allows user to select function signature and parameters' typed identifiers
        FunctionOptionsStage addFunctionStage = new FunctionOptionsStage("Add new function");

        // Set program to add function to the model and interface when the confirm button is pressed on the new stage
        addFunctionStage.setOnConfirm(e -> {
            Pattern idfrPattern = Pattern.compile("[a-z][a-zA-Z0-9]*");
            Matcher matcher = idfrPattern.matcher(addFunctionStage.getIdentifer());
            // Check information is valid
            if (viewModel.hasIdentifier(addFunctionStage.getIdentifer()) || !matcher.find()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please ensure function name is unique and well-formatted," +
                                     "function names must be camelCase starting with a letter");
                alert.showAndWait();
                return;
            }

            for (String identifier : addFunctionStage.getParameterIdentifiers()) {
                matcher = idfrPattern.matcher(identifier);
                if (!matcher.find() || Collections.frequency(addFunctionStage.getParameterIdentifiers(), identifier) > 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please ensure parameter names are unique and well-formatted," +
                            "parameter names must be camelCase starting with a letter." +
                            "'" + identifier + "' is not.");
                    alert.showAndWait();
                    return;
                }
            }

            // Gather data input from widgets
            String identifer = addFunctionStage.getIdentifer();
            String returnType = addFunctionStage.getReturnType();
            List<String> paramIdentifiers = addFunctionStage.getParameterIdentifiers();
            List<String> paramTypes = addFunctionStage.getParameterTypes();

            // Create function's UIFlows
            UIFlow flowRoot = new UIFlow(UICell.IRRELEVANT_ID);
            UIFlow flowBody = new UIFlow(UICell.IRRELEVANT_ID);
            UISquircle start = new UISquircle("Start", UICell.IRRELEVANT_ID);
            UISquircle end = new UISquircle("End", UICell.IRRELEVANT_ID);
            flowRoot.addCell(start, 0);
            flowRoot.addCell(flowBody, 1);
            flowRoot.addCell(end, 2);

            // Add function to View maps
            functionRootMap.put(identifer, flowRoot);
            functionFlowMap.put(identifer, flowBody);

            // Set event handlers for root UIFlow placeholder
            setCellPlaceholderHandlers(flowBody.getPlaceholder());

            // Create ViewPort and add it to the editor TabPane
            ZoomableScrollPane editorView = new ZoomableScrollPane(flowRoot);
            editorView.setId("grid");
            Tab tab = new Tab(identifer, editorView);
            editorRoot.getTabs().add(tab);

            // Inform viewModel of gathered information
            viewModel.addFunction(identifer, returnType, paramIdentifiers, paramTypes);
            selectFlow(identifer);

            addFunctionStage.close();
        });

        // Set program to ignore inputs when cancel button is pressed on new stage
        addFunctionStage.setOnCancel(e -> addFunctionStage.close());

        addFunctionStage.showAndWait();
    }

    private void editFunction() {
        // Build and show JavaFX stage that allows user to select new function signature and parameters' typed identifiers
        FunctionOptionsStage editFunctionStage = new FunctionOptionsStage("Edit function");

        // Populate stage with existing function information
        editFunctionStage.importData(viewModel.getSelectedIdentifier(), viewModel.getSelectedReturnType(), viewModel.getSelectedParameterIdentifiers(), viewModel.getSelectedParameterTypes());

        editFunctionStage.setOnConfirm(e -> {
            Pattern idfrPattern = Pattern.compile("[a-z][a-zA-Z0-9]*");
            Matcher matcher = idfrPattern.matcher(editFunctionStage.getIdentifer());
            // Check information is valid
            if ((!editFunctionStage.getIdentifer().equals(viewModel.getSelectedIdentifier()) && viewModel.hasIdentifier(editFunctionStage.getIdentifer())) || !matcher.find()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please ensure function name is unique and well-formatted, function names must be camelCase starting with a letter");
                alert.showAndWait();
                return;
            }

            for (String identifier : editFunctionStage.getParameterIdentifiers()) {
                matcher = idfrPattern.matcher(identifier);
                if (!matcher.find() || Collections.frequency(editFunctionStage.getParameterIdentifiers(), identifier) > 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please ensure parameter names are unique and well-formatted," +
                            "parameter names must be camelCase starting with a letter." +
                            "'" + identifier + "' is not.");
                    alert.showAndWait();
                    return;
                }
            }

            // Inform viewModel of changes
            viewModel.editFunction(editFunctionStage.getIdentifer(), editFunctionStage.getReturnType(), editFunctionStage.getParameterIdentifiers(), editFunctionStage.getParameterTypes());
            // Change tab name to new identifier
            editorRoot.getSelectionModel().getSelectedItem().setText(editFunctionStage.getIdentifer());

            // Change map keys to new identifier
            functionRootMap.remove(selectedFlow);
            functionFlowMap.remove(selectedFlow);
            functionFlowMap.put(editFunctionStage.getIdentifer(), selectedFlowBody);
            functionRootMap.put(editFunctionStage.getIdentifer(), selectedFlowRoot);

            // Re-select updated function
            selectFlow(editFunctionStage.getIdentifer());

            editFunctionStage.close();
        });
        editFunctionStage.setOnCancel(e -> editFunctionStage.close());
        editFunctionStage.showAndWait();
    }

    private void removeFunction(String identifier) {
        // Removes a function from the editor interface
        functionFlowMap.remove(identifier);

        // Search for tab with matching identifier
        Tab selectedTab = null;
        for (Tab tab : editorRoot.getTabs()) {
            selectedTab = tab.getText().equals(identifier) ? tab : selectedTab;
        }

        // Remove resulting tab if one is found
        if (selectedTab != null) {
            editorRoot.getTabs().remove(selectedTab);
        }

        // Inform the viewModel of the change
        viewModel.removeFunction(identifier);
    }

    private void selectFlow(String identifier) {
        // Update attributes with information from maps
        selectedFlow = identifier;
        selectedFlowRoot = functionRootMap.get(identifier);
        selectedFlowBody = functionFlowMap.get(identifier);

        // Inform viewModel of selection
        viewModel.selectModel(identifier);

        // Update function description
        functionDescription.setText("   " + viewModel.getFunctionDescription());

        // Select matching tab
        for (Tab tab : editorRoot.getTabs()) {
            if (tab.getText().equals(identifier)) {
                editorRoot.getSelectionModel().select(tab);
            }
        }

        editButton.setDisable(identifier.equals("main"));
        removeButton.setDisable(identifier.equals("main"));

        importFlow(viewModel.getFlowNodes(), functionFlowMap.get(selectedFlow));
    }
}
