package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Visual representation of a DeclareAssignNode in the Flow model
 */
public class UIDeclareAssign extends UICell implements UIExprContainer {
    private StackPane root; // Root node
    private Rectangle back; // Backing rectangle
    private ExprPlaceholder value; // Expression input
    private ComboBox<String> types; // List of choices of data types
    private TextField varName; // Input for variable name
    private Label equals;
    private HBox row; // Container for input elements
    public UIDeclareAssign(int cellID) {
        super(cellID);

        // Create root node
        root = new StackPane();
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);

        // Create container node for inputs
        row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10.0d);
        root.getChildren().add(row);

        // Create list of choices of data types
        types = new ComboBox<>();
        ObservableList<String> options = FXCollections.observableArrayList();
        for (DataType type : DataType.values()) {
            options.add(type.toString());
        }
        options.remove(DataType.VoidType.toString());
        types.getItems().addAll(options);
        row.getChildren().add(types);
        row.layout();
        types.setPrefWidth(100.0d);

        // Create TextField for variable identifier
        varName = new TextField();
        varName.setPromptText("Variable Name");
        varName.textProperty().addListener((observableValue, oldVal, newVal) -> {
            Pattern idfrPattern = Pattern.compile("([a-z][a-zA-Z0-9]*)?");
            Matcher matcher = idfrPattern.matcher(newVal);
            if (!matcher.find()) {
                varName.setText(oldVal);
            }
        });
        row.getChildren().add(varName);
        varName.setPrefWidth(100.0d);

        equals = new Label("=");
        row.getChildren().add(equals);

        // Create expression placeholder for expression inputs
        value = new ExprPlaceholder(cellID, 0);
        row.getChildren().add(value);

        // Create background rectangle
        back = new Rectangle((row.getBoundsInLocal().getWidth() + varName.getPrefWidth() + types.getPrefWidth()) + INSET + 3 * row.getSpacing(), row.getBoundsInLocal().getHeight() + INSET, Color.WHITE);
        root.getChildren().add(back);
        back.toBack();
    }

    /**
     * @return ComboBox containing choices of data types
     */
    public ComboBox<String> getTypes() {
        return types;
    }

    /**
     * @return TextField for variable identifier
     */
    public TextField getVarName() {
        return varName;
    }

    @Override
    public double attachTopX() {
        return getWidth() / 2;
    }

    @Override
    public double attachBotX() {
        return getWidth() / 2;
    }

    @Override
    public double getHeight() {
        return back.getBoundsInLocal().getHeight();
    }

    @Override
    public double getWidth() {
        return back.getBoundsInLocal().getWidth();
    }

    @Override
    public void updateLayout() {
        if (!root.getChildren().contains(getPseudoLabel())) {
            back.setWidth(types.getPrefWidth() + varName.getPrefWidth() + equals.getWidth() + value.getWidth() + INSET + 3 * row.getSpacing());
            back.setHeight(value.getHeight() + INSET);
        } else {
            back.setWidth(getPseudoLabel().getWidth() + INSET * 2);
            back.setHeight(getPseudoLabel().getHeight() + INSET);
        }
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        if (visible && !root.getChildren().contains(getPseudoLabel())) {
            // Remove input fields
            root.getChildren().remove(row);

            String type = types.getSelectionModel().getSelectedItem() == null ? "" : types.getSelectionModel().getSelectedItem();

            // Add pseudocode label
            getPseudoLabel().setText("Declare " + varName.getText() + " as " + type + " and set to " + value.getPseudoLabel());
            root.getChildren().add(getPseudoLabel());
        } else if (!visible && !root.getChildren().contains(row)) {
            // Add input fields
            root.getChildren().add(row);

            // Remove pseudocode label
            root.getChildren().remove(getPseudoLabel());
        }
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(value);
    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return !types.getSelectionModel().getSelectedItem().equals("") && !varName.getText().equals("") && (value.getExpr() != null ? value.getExpr().isComplete() : false);
    }
}
