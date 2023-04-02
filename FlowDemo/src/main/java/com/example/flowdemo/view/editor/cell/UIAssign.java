package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.geometry.Pos;
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
 * Visual representation of an AssignNode in the Flow model
 */
public class UIAssign extends UICell implements UIExprContainer {
    protected StackPane root; // Root node

    protected Rectangle back; // Backing rectangle
    private TextField varName; // Input for variable name
    private Label equals;
    protected HBox row; // Container for input elements
    private ExprPlaceholder value; // Expression input

    public UIAssign(int cellID) {
        super(cellID);

        // Create root node
        root = new StackPane();
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);

        // Create container node for inputs
        row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10.0d);

        // Create textField for variable identifier
        varName = new TextField();
        varName.setPrefWidth(100.0d);
        varName.setPromptText("Variable Name");
        varName.textProperty().addListener((observableValue, oldVal, newVal) -> {
            Pattern idfrPattern = Pattern.compile("([a-z][a-zA-Z0-9]*)?");
            Matcher matcher = idfrPattern.matcher(newVal);
            if (!matcher.find()) {
                varName.setText(oldVal);
            }
        });
        row.getChildren().add(varName);

        equals = new Label("=");
        row.getChildren().add(equals);

        // Create expression placeholder for expression inputs
        value = new ExprPlaceholder(cellID, 0);
        row.getChildren().add(value);

        row.layout();

        // Create background rectangle
        back = new Rectangle(
                varName.getPrefWidth() + equals.getWidth() + value.getWidth() + INSET + 2 * row.getSpacing(),
                value.getHeight() + INSET, Color.WHITE
        );
        root.getChildren().add(back);

        // Add input container to root
        root.getChildren().add(row);
    }

    /**
     * @return TextField for variable identifier
     */
    public TextField getVarName() {
        return varName;
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(value);
    }

    @Override
    public double attachTopX() {
        return back.getBoundsInLocal().getWidth() / 2;
    }

    @Override
    public double attachBotX() {
        return back.getBoundsInLocal().getWidth() / 2;
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
            back.setWidth(varName.getPrefWidth() + equals.getWidth() + value.getWidth() + INSET + 2 * row.getSpacing());
            back.setHeight(value.getHeight() + INSET);
        } else {
            back.setWidth(getPseudoLabel().getBoundsInLocal().getWidth() + INSET * 2);
            back.setHeight(getPseudoLabel().getBoundsInLocal().getHeight() + INSET);
        }

    }

    @Override
    public void setPseudoVisible(boolean visible) {
        if (visible && !root.getChildren().contains(getPseudoLabel())) {
            // Remove input fields
            root.getChildren().remove(row);

            // Add pseudocode label
            getPseudoLabel().setText("Set " + varName.getText() + " to " + value.getPseudoLabel());
            root.getChildren().add(getPseudoLabel());
        } else if (!visible && !root.getChildren().contains(row)) {
            // Add input fields
            root.getChildren().add(row);

            // Remove pseudocode label
            root.getChildren().remove(getPseudoLabel());
        }
    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return !varName.getText().equals("") && (value.getExpr() != null ? value.getExpr().isComplete() : false);
    }
}
