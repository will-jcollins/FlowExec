package com.example.flowdemo.view.editor.expr;

import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.LinkedList;
import java.util.List;

public class UICallExpr extends UIExpr implements UIExprContainer {
    private HBox paramExprs;
    private List<ExprPlaceholder> exprPlaceholders;
    private ComboBox<String> idfrBox;

    public UICallExpr(int id) {
        super(id);

        exprPlaceholders = new LinkedList<>();

        // Layout values horizontally
        HBox root = new HBox();
        root.setSpacing(10.0d);
        getChildren().add(root);

        // Container for picking function identifiers
        idfrBox = new ComboBox<>();
        idfrBox.setMaxWidth(PREF_WIDTH);
        idfrBox.setMaxHeight(PREF_HEIGHT);
        root.getChildren().add(idfrBox);

        // Horizontal list of expressions for parameters
        paramExprs = new HBox();
        paramExprs.setSpacing(10.0d);
        root.getChildren().add(paramExprs);

        // Activate mouse target to allow for draggable combo box
        getMouseTarget().toFront();
        setOnMouseClicked(e -> idfrBox.show());
    }

    public ComboBox<String> getComboBox() {
        return idfrBox;
    }

    public void addExprContainer() {
        ExprPlaceholder placeholder = new ExprPlaceholder(getExprId(), exprPlaceholders.size());
        exprPlaceholders.add(placeholder);
        paramExprs.getChildren().add(placeholder);
    }

    public void removeExprContainer() {
        paramExprs.getChildren().remove(exprPlaceholders.get(exprPlaceholders.size() - 1));
        exprPlaceholders.remove(exprPlaceholders.get(exprPlaceholders.size() - 1));
    }

    public void setSetNumberOfParameters(int n) {
        while (exprPlaceholders.size() < n) {
            addExprContainer();
        }

        while (exprPlaceholders.size() > n) {
            removeExprContainer();
        }
    }

    @Override
    public double getWidth() {
        if (idfrBox != null && paramExprs != null) {
            double width = idfrBox.getMaxWidth();

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                width += paramExprs.getSpacing();
                width += placeholder.getWidth();
            }

            return width;
        } else {
            return 0;
        }
    }

    @Override
    public double getHeight() {
        if (idfrBox != null && paramExprs != null) {
            double height = idfrBox.getMaxHeight();

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                height = Math.max(height, placeholder.getHeight());
            }

            return height;
        } else {
            return 0;
        }
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return exprPlaceholders;
    }

    @Override
    public boolean isComplete() {
        boolean complete = !idfrBox.getSelectionModel().getSelectedItem().equals("");

        for (ExprPlaceholder placeholder : exprPlaceholders) {
            complete = complete && (placeholder.getExpr() != null ? placeholder.getExpr().isComplete() : false);
        }

        return complete;

    }
}
