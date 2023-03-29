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

    protected Rectangle back; // Backing rectangle
    private TextField indexField; // Input for variable name
    private Label equals;
    protected HBox row; // Container for input elements
    private ExprPlaceholder value; // Expression input

    public UIAssign(int cellID) {
        super(cellID);

        // Create root node
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);

        // Create container node for inputs
        row = new HBox();
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10.0d);

        // Create textField for variable identifier
        indexField = new TextField();
        indexField.setPrefWidth(100.0d);
        indexField.setPromptText("Variable Name");
        indexField.textProperty().addListener((observableValue, oldVal, newVal) -> {
            Pattern idfrPattern = Pattern.compile("([a-z][a-zA-Z0-9]*)?");
            Matcher matcher = idfrPattern.matcher(newVal);
            if (!matcher.find()) {
                indexField.setText(oldVal);
            }
        });
        row.getChildren().add(indexField);

        equals = new Label("=");
        row.getChildren().add(equals);

        // Create expression placeholder for expression inputs
        value = new ExprPlaceholder(cellID, 0);
        row.getChildren().add(value);

        row.layout();

        // Create background rectangle
        back = new Rectangle(
                indexField.getPrefWidth() + equals.getWidth() + value.getWidth() + INSET + 2 * row.getSpacing(),
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
        return indexField;
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
        back.setWidth(indexField.getPrefWidth() + equals.getWidth() + value.getWidth() + INSET + 2 * row.getSpacing());
        back.setHeight(value.getHeight() + INSET);
    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return !indexField.getText().equals("") && (value.getExpr() != null ? value.getExpr().isComplete() : false);
    }
}
