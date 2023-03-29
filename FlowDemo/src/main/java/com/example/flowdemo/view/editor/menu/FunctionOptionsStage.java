package com.example.flowdemo.view.editor.menu;

import com.example.flowdemo.model.flow.DataType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FunctionOptionsStage extends Stage {
    public static final double PADDING = 10.0d;

    private TextField identifier;
    private ComboBox<String> returnType;
    private VBox parameterList;
    private List<TextField> paramIdentifiers;
    private List<ComboBox<String>> paramTypes;
    private Button removeParam;
    private Button confirm;
    private Button cancel;

    public FunctionOptionsStage(String titleText) {
        paramIdentifiers = new LinkedList<>();
        paramTypes = new LinkedList<>();

        BorderPane root = new BorderPane();

        VBox elementsList = new VBox();
        elementsList.setAlignment(Pos.CENTER);
        elementsList.setSpacing(PADDING);
        elementsList.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        root.setCenter(elementsList);

        Text title = new Text(titleText);
        elementsList.getChildren().add(title);

        // Include function signature inputs
        HBox signatureInputs = new HBox();
        signatureInputs.setAlignment(Pos.CENTER_LEFT);
        signatureInputs.setSpacing(PADDING);
        elementsList.getChildren().add(signatureInputs);

        // Type selection
        returnType = new ComboBox<>();
        for (DataType type : DataType.values()) {
            returnType.getItems().add(type.toString());
            returnType.getSelectionModel().select(type.toString());
        }
        signatureInputs.getChildren().add(returnType);

        // Identifier input
        identifier = new TextField();
        identifier.setPromptText("Function Name");
        signatureInputs.getChildren().add(identifier);

        parameterList = new VBox();
        parameterList.setSpacing(10.0d);
        elementsList.getChildren().add(parameterList);

        // Include add / remove parameter buttons
        HBox paramButtons = new HBox();
        paramButtons.setAlignment(Pos.CENTER);
        paramButtons.setSpacing(PADDING);
        elementsList.getChildren().add(paramButtons);

        removeParam = new Button("Remove Parameter");
        removeParam.setDisable(true);
        removeParam.setOnMouseClicked(e -> removeParameter());

        Button addParam = new Button("Add Parameter");
        addParam.setOnMouseClicked(e -> addParameter());

        paramButtons.getChildren().add(addParam);
        paramButtons.getChildren().add(removeParam);

        // Include confirm / cancel buttons
        HBox endButtons = new HBox();
        endButtons.setAlignment(Pos.CENTER);
        endButtons.setSpacing(PADDING);
        elementsList.getChildren().add(endButtons);

        confirm = new Button("Confirm");
        cancel = new Button("Cancel");
        endButtons.getChildren().add(confirm);
        endButtons.getChildren().add(cancel);


        Scene scene = new Scene(root);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(classloader.getResource("style.css").toExternalForm());
        setResizable(false);
        setScene(scene);
    }

    public void importData(String identifier, String returnType, List<String> pIdentifiers, List<String> pTypes) {
        this.identifier.setText(identifier);
        this.returnType.getSelectionModel().select(returnType);

        for (int i = 0; i < Math.min(pIdentifiers.size(), pTypes.size()); i++) {
            addParameter();
            paramIdentifiers.get(i).setText(pIdentifiers.get(i));
            paramTypes.get(i).getSelectionModel().select(pTypes.get(i));
        }
    }

    private void addParameter() {
        List<DataType> types = new LinkedList<>(Arrays.asList(DataType.values()));
        types.remove(DataType.VoidType);


        ScrollPane scrollPane = new ScrollPane();
        HBox parameterInputs = new HBox();
        parameterInputs.setSpacing(PADDING);

        TextField paramIdentifier = new TextField();
        paramIdentifier.setPromptText("Parameter Name");

        ComboBox<String> paramType = new ComboBox<>();
        for (DataType type : types) {
            paramType.getItems().add(type.toString());
            paramType.getSelectionModel().select(type.toString());
        }

        parameterInputs.getChildren().add(paramType);
        parameterInputs.getChildren().add(paramIdentifier);
        scrollPane.setContent(parameterInputs);

        parameterList.getChildren().add(scrollPane);
        paramIdentifiers.add(paramIdentifier);
        paramTypes.add(paramType);

        removeParam.setDisable(parameterList.getChildren().size() < 1);

        sizeToScene();
    }

    private void removeParameter() {
        // If no parameters left, hide remove parameter button
        if (parameterList.getChildren().size() > 0) {
            parameterList.getChildren().remove(parameterList.getChildren().size() - 1);
            paramIdentifiers.remove(paramIdentifiers.size() - 1);
            paramTypes.remove(paramTypes.size() - 1);
        }

        removeParam.setDisable(parameterList.getChildren().size() < 1);

        sizeToScene();
    }

    public String getIdentifer() {
        return identifier.getText();
    }

    public String getReturnType() {
        return returnType.getValue();
    }

    public List<String> getParameterIdentifiers() {
        List<String> identifiers = new ArrayList<>();
        for (TextField field : paramIdentifiers) {
            identifiers.add(field.getText());
        }
        return identifiers;
    }

    public List<String> getParameterTypes() {
        List<String> identifiers = new ArrayList<>();
        for (ComboBox<String> type : paramTypes) {
            identifiers.add(type.getSelectionModel().getSelectedItem());
        }
        return identifiers;
    }

    public void setOnConfirm(EventHandler<MouseEvent> event) {
        confirm.setOnMouseClicked(event);
    }

    public void setOnCancel(EventHandler<MouseEvent> event) {
        cancel.setOnMouseClicked(event);
    }

}
