package com.example.flowdemo.view.editor;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.FlowFile;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.*;
import com.example.flowdemo.model.flow.nodes.*;
import com.example.flowdemo.model.transpiler.FlowException;
import com.example.flowdemo.model.transpiler.FlowGrammarAnalyser;
import com.example.flowdemo.model.transpiler.JavaConverter;
import com.example.flowdemo.model.transpiler.PythonConverter;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarLexer;
import com.example.flowdemo.model.transpiler.antlr.FlowGrammarParser;
import com.example.flowdemo.view.editor.cell.CellPlaceholder;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExpr;
import com.example.flowdemo.view.editor.menu.NewItem;
import com.example.flowdemo.view.editor.cell.UICell;
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

public class EditorViewModel {
    private Map<String, Flow> flowMap; // Maps identifiers to flow-chart models
    private Flow selectedModel; // Currently visible model
    private FlowNode dragNode; // Node being dragged
    private Expr dragExpr; // Expression being dragged
    private FlowException error; // Error returned from semantic analysis

    // Binding
    private SimpleBooleanProperty updateSignal;

    public EditorViewModel() {
        flowMap = new HashMap<>();
        flowMap.put("main", new Flow());
        selectModel("main");

        this.updateSignal = new SimpleBooleanProperty(true);
    }

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
        signalUpdate();
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
        signalUpdate();
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
        signalUpdate();
    }

    /**
     * Returns a JavaFX boolean property that changes when the model is updated
     * @return change property
     */
    public SimpleBooleanProperty updateSignalProperty() {
        return updateSignal;
    }

    /**
     * Returns a new ordered list of FlowNodes that is separated from the model
     * @return List of FlowNodes
     */
    public List<FlowNode> getFlowNodes() {
        return selectedModel.getFlowNodes();
    }

    private void signalUpdate() {
        // Flip boolean
        updateSignal.set(!updateSignal.get());
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
        signalUpdate();
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
        signalUpdate();
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
        signalUpdate();
        e.consume();
    }

    public void updateBoolExpr(BoolLit expr, String value) {
        value = value.toLowerCase(Locale.ROOT);
        expr.setVal(value.equals("true"));
    }

    public void updateCharExpr(CharLit expr, String value) {
        value = value.toLowerCase(Locale.ROOT);
        if (value.length() > 0) {
            expr.setVal(value.toCharArray()[0]);
        } else {
            expr.setVal('\u0000'); // Null char value
        }
    }

    public void updateIntExpr(IntLit expr, String value) {
        value = value.toLowerCase(Locale.ROOT);
        try {
            int intValue = Integer.parseInt(value);
            expr.setVal(intValue);
        } catch (NumberFormatException e) {
            expr.setVal(0);
        }
    }

    public void testHandler() {
        System.out.println(selectedModel.toFunctionCode());
    }

    public void updateOpExpr(OpExpr opExpr, String value) {
        opExpr.setOp(Operator.fromString(value));
    }

    public void updateVarExpr(VarExpr varExpr, String value) {
        varExpr.setIdentifier(value);
    }

    public void updateModExpr(ModifierExpr modExpr, String value) {
        modExpr.setModifier(Modifier.fromString(value));
    }

    public void updateAssignNodeIdentifier(AssignNode assignNode, String identifier) {assignNode.setIdentifier(identifier);}

    public void updateDeclareAssignNodeType(DeclareAssignNode assignNode, String type) {assignNode.getSignature().setType(DataType.fromString(type));}

    public void updateDeclareAssignNodeIdentifier(DeclareAssignNode assignNode, String identifier) {assignNode.getSignature().setIdentifier(identifier);}

    public void updateCallExprIdentifier(CallExpr callExpr, String identifier) {
        callExpr.setIdentifier(identifier);
        if (flowMap.get(identifier) != null) {
            callExpr.setNumberOfParameters(flowMap.get(identifier).getParameters().size());
        }
    }

    public void updateCallNodeIdentifier(CallNode callNode, String identifier) {
        callNode.setIdentifier(identifier);
        if (flowMap.get(identifier) != null) {
            callNode.setNumberOfParameters(flowMap.get(identifier).getParameters().size());
        }
    }

    public void updateForIdentifier(ForNode forNode, String text) {
        forNode.setIdentifier(text);
    }

    /**
     * Changes the model that viewModel methods will manipulate according to input identifier
     * @param identifier identifier of the Flow model to select
     */
    public void selectModel(String identifier) {
        selectedModel = flowMap.get(identifier);
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

        selectedModel.setSignature(signature);
        selectedModel.setParameters(parameters);
    }

    /**
     * Removes references to the model which corresponds to the input identifier
     * @param identifier identifier of the Flow model to remove
     */
    public void removeFunction(String identifier) {
        flowMap.remove(identifier);

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
            signalUpdate();
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

                    // Serialize Flow model map and save to file
                    FlowFile fileData = (FlowFile) objectStream.readObject();
                    flowMap = fileData.getFlowMap();
                    FlowNode.setIdCounter(fileData.getMaxNodeId());
                    Expr.setIdCounter(fileData.getMaxExprId());

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
}
