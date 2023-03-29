package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExpr;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

/**
 * Visual representation of a CallNode in the Flow model
 */
public class UICall extends UICell implements UIExprContainer {

    // Pair of rectangles
    private Rectangle longBack;
    private Rectangle shortBack;
    private HBox paramExprs; // Container the ExprPlaceholders are placed within
    private List<ExprPlaceholder> exprPlaceholders;
    private ComboBox<String> idfrBox; // List of choices of functions to call

    public UICall(int cellID) {
        super(cellID);

        // Create root node
        StackPane root = new StackPane();
        getChildren().add(root);

        // Create background rectangles
        longBack = new Rectangle();
        root.getChildren().add(longBack);
        shortBack = new Rectangle();
        root.getChildren().add(shortBack);

        exprPlaceholders = new LinkedList<>();

        // Create node to layout inputs horizontally
        HBox callRoot = new HBox();
        callRoot.setAlignment(Pos.CENTER);
        callRoot.setSpacing(10.0d);
        root.getChildren().add(callRoot);

        // Container for picking function identifiers
        idfrBox = new ComboBox<>();
        idfrBox.setMaxWidth(UIExpr.PREF_WIDTH);
        idfrBox.setMaxHeight(UIExpr.PREF_HEIGHT);
        callRoot.getChildren().add(idfrBox);

        // Horizontal list of expressions for parameters
        paramExprs = new HBox();
        paramExprs.setAlignment(Pos.CENTER);
        paramExprs.setSpacing(10.0d);
        callRoot.getChildren().add(paramExprs);
    }

    /**
     * @return ComboBox with list of choices of functions to call
     */
    public ComboBox<String> getIdfrBox() {
        return idfrBox;
    }

    /**
     * Adds one exprPlaceholder for parameter inputs
     */
    public void addExprContainer() {
        ExprPlaceholder placeholder = new ExprPlaceholder(getCellID(), exprPlaceholders.size());
        exprPlaceholders.add(placeholder);
        paramExprs.getChildren().add(placeholder);
    }

    /**
     * Removes one ExprPlaceholder for parameter inputs
     */
    public void removeExprContainer() {
        paramExprs.getChildren().remove(exprPlaceholders.get(exprPlaceholders.size() - 1));
        exprPlaceholders.remove(exprPlaceholders.get(exprPlaceholders.size() - 1));
    }

    /**
     * Sets the number of ExprPlaceholders for parameter inputs
     * @param n new number of parameter inputs
     */
    public void setSetNumberOfParameters(int n) {
        while (exprPlaceholders.size() < n) {
            addExprContainer();
        }

        while (exprPlaceholders.size() > n) {
            removeExprContainer();
        }
    }

    @Override
    public double attachTopX() {
        return longBack.getWidth() / 2;
    }

    @Override
    public double attachBotX() {
        return longBack.getWidth() / 2;
    }

    @Override
    public double attachTopY() {
        return 0;
    }

    @Override
    public double attachBotY() {
        return longBack.getHeight();
    }

    @Override
    public double attachLeftX() {
        return 0;
    }

    @Override
    public double attachRightX() {
        return longBack.getWidth();
    }

    @Override
    public double attachLeftY() {
        return longBack.getHeight() / 2;
    }

    @Override
    public double attachRightY() {
        return longBack.getHeight() / 2;
    }

    @Override
    public double getHeight() {
        return longBack.getHeight();
    }

    @Override
    public double getWidth() {
        return longBack.getWidth();
    }

    @Override
    public void updateLayout() {
        double width = INSET;
        if (idfrBox != null && paramExprs != null) {
            width += idfrBox.getMaxWidth();

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                width += paramExprs.getSpacing();
                width += placeholder.getWidth();
            }
        }

        double height = 0;
        if (idfrBox != null && paramExprs != null) {
            height = idfrBox.getMaxHeight();

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                height = Math.max(height, placeholder.getHeight());
            }
        }
        height += INSET;

        longBack.setWidth(width + INSET);
        longBack.setHeight(height);
        shortBack.setWidth(width);
        shortBack.setHeight(height);
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return exprPlaceholders;
    }

    @Override
    public void setStyleClass(String styleClass) {
        longBack.getStyleClass().clear();
        longBack.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);

        shortBack.getStyleClass().clear();
        shortBack.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        boolean complete = true;

        complete = complete && !idfrBox.getSelectionModel().getSelectedItem().equals("");

        for (ExprPlaceholder placeholder : exprPlaceholders) {
            complete = complete && (placeholder.getExpr() != null ? placeholder.getExpr().isComplete() : false);
        }

        return complete;
    }
}
