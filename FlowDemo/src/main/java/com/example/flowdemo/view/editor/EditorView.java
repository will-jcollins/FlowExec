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
    private EditorViewModel viewModel; // ViewModel responsible for bridging view to the model
    private TabPane editorRoot; // ViewPort for the flow-chart editor window
    private Text functionDescription; // Label that describes the inputs and outputs of the flow-chart currently visible

    // Toolbar buttons
    private Button editButton; // Button to edit the currently selected flow, needed as an attribute so it can be disabled
    private Button removeButton; // Button to remove the currently selected flow, needed as an attribute so it can be disabled
    public EditorView(EditorViewModel viewModel) {
        // Set attributes
        this.viewModel = viewModel;

        // Initialise viewport for flow-chart editor
        editorRoot = new TabPane();
        editorRoot.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Add listener for change to selected tab
        editorRoot.getSelectionModel().selectedItemProperty().addListener(e -> {
            if (editorRoot.getSelectionModel().getSelectedItem() != null) {
                selectFlow(editorRoot.getSelectionModel().getSelectedItem().getText());
            }
        });
        setCenter(editorRoot);

        // Initialise new elements list
        VBox newElementsList = new VBox();
        newElementsList.setSpacing(20.0d);
        newElementsList.setAlignment(Pos.CENTER);
        newElementsList.setPadding(new Insets(20.0d, 20.0d, 20.0d, 20.0d));
        newElementsList.setId("item-list");


        // Add new elements
        for (ItemType type : ItemType.values()) {
            NewItem newItem = new NewItem(type);
            newItem.setOnDragDetected(e -> viewModel.onNewCellDragged(e, newItem));
            newElementsList.getChildren().add(newItem);
        }

        ScrollPane newElementsRoot = new ScrollPane();
        newElementsRoot.setContent(newElementsList);
        setLeft(newElementsRoot);

        // Add horizontal container for buttons to be placed
        HBox toolbar = new HBox();
        toolbar.setSpacing(10);
        toolbar.setPadding(new Insets(10, 10, 10, 10));

        // Button to save model
        Button saveButton = new Button("Save");
        saveButton.setOnMouseClicked(e -> viewModel.saveModel());
        toolbar.getChildren().add(saveButton);

        // Button to load model
        Button loadButton = new Button("Load");
        loadButton.setOnMouseClicked(e -> {
            viewModel.loadModel();

            // Create new tabs from loaded information in the viewModel
            editorRoot.getTabs().clear();
            updateTabs();
            selectFlow("main");
        });
        toolbar.getChildren().add(loadButton);

        // Button to add new function
        Button addButton = new Button("Add function");
        addButton.setOnMouseClicked(e -> addFunction());
        toolbar.getChildren().add(addButton);

        // Button to remove current function
        removeButton = new Button("Remove Function");
        removeButton.setOnMouseClicked(e -> {
            viewModel.removeFunction();
            updateTabs();
        });
        toolbar.getChildren().add(removeButton);

        // Button to edit function name and parameters
        editButton = new Button("Edit Function");
        editButton.setOnMouseClicked(e -> editFunction());
        toolbar.getChildren().add(editButton);

        // Button to toggle pseudocode visibility
        Button pseudoCodeButton = new Button("Show Pseudocode");
        pseudoCodeButton.setOnMouseClicked(e -> {
            // Toggle pseudocode visibility
            viewModel.togglePseudoVisible();

            // Update button label
            if (viewModel.isPseudoVisible()) {
                pseudoCodeButton.setText("Hide Pseudocode");
            } else {
                pseudoCodeButton.setText("Show Pseudocode");
            }
        });
        toolbar.getChildren().add(pseudoCodeButton);

        Button compileButton = new Button("Compile");
        toolbar.getChildren().add(compileButton);

        // Selection for target language of compilation
        ComboBox<String> compileChoice = new ComboBox<>();
        compileChoice.getItems().add("java");
        compileChoice.getItems().add("python");
        compileChoice.getSelectionModel().select("java");
        toolbar.getChildren().add(compileChoice);

        compileButton.setOnMouseClicked(e -> {
            boolean complete = true;
            for (String identifier : viewModel.getFunctionIdentifiers()) {
                complete = viewModel.getFlowRoot(identifier).isComplete() && complete;
            }

            if (complete) {
                viewModel.convertModel(compileChoice.getSelectionModel().getSelectedItem());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("All fields are not completed.");
                alert.showAndWait();
            }
        });

        setTop(toolbar);

        // Add function description
        functionDescription = new Text("   " + viewModel.getFunctionDescription());
        HBox functionDescriptionContainer = new HBox(functionDescription);
        functionDescriptionContainer.setId("description");
        setBottom(functionDescriptionContainer);

        // Select main as the currently visible Flow-Chart (only one that should currently exist)
        updateTabs();
        selectFlow("main");
    }

    /**
     * Reloads the tabs in the editorView to match the structure of the model
     */
    public void updateTabs() {
        // Clear tabs
        editorRoot.getTabs().clear();

        // Add new tabs (for loop must be descending to ensure tabs are added in correct order)
        for (int i = viewModel.getFunctionIdentifiers().size() - 1; i >= 0; i--) {
            String identifier = viewModel.getFunctionIdentifiers().get(i);
            ZoomableScrollPane tabView = new ZoomableScrollPane(viewModel.getFlowRoot(identifier));
            Tab tab = new Tab(identifier, tabView);
            editorRoot.getTabs().add(tab);
        }
    }

    private void selectFlow(String identifier) {
        // Inform viewModel of selection
        viewModel.selectModel(identifier);

        // Retrieve and display function description
        functionDescription.setText("   " + viewModel.getFunctionDescription());

        // Select matching tab
        for (Tab tab : editorRoot.getTabs()) {
            if (tab.getText().equals(identifier)) {
                editorRoot.getSelectionModel().select(tab);
            }
        }

        // Disable edit and remove buttons if main function is selected
        editButton.setDisable(identifier.equals("main"));
        removeButton.setDisable(identifier.equals("main"));
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
            String identifier = addFunctionStage.getIdentifer();
            String returnType = addFunctionStage.getReturnType();
            List<String> paramIdentifiers = addFunctionStage.getParameterIdentifiers();
            List<String> paramTypes = addFunctionStage.getParameterTypes();

            // Inform viewModel of gathered information
            viewModel.addFunction(identifier, returnType, paramIdentifiers, paramTypes);

            // Create ViewPort and add it to the editor TabPane
            ZoomableScrollPane editorView = new ZoomableScrollPane(viewModel.getFlowRoot(identifier));
            editorView.setId("grid");
            Tab tab = new Tab(identifier, editorView);
            editorRoot.getTabs().add(tab);

            // Select newly created function
            updateTabs();
            selectFlow(identifier);

            // Close stage
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

            // Re-select updated function
            updateTabs();
            selectFlow(editFunctionStage.getIdentifer());

            editFunctionStage.close();
        });
        editFunctionStage.setOnCancel(e -> editFunctionStage.close());
        editFunctionStage.showAndWait();
    }
}
