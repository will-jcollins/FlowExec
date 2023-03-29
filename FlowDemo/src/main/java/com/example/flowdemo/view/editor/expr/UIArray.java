package com.example.flowdemo.view.editor.expr;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.LinkedList;
import java.util.List;

public class UIArray extends UIExpr implements UIExprContainer {

    private HBox root;
    private Label openLabel;
    private Label closeLabel;
    private HBox elementExprs;
    private List<ExprPlaceholder> exprPlaceholders = new LinkedList<>();

    public UIArray(int id) {
        super(id);

        // Layout values horizontally
        root = new HBox();
        root.setAlignment(Pos.CENTER_LEFT);
        root.setSpacing(10.0d);
        getChildren().add(root);

        openLabel = new Label("{");
        openLabel.setId("item-list");
        root.getChildren().add(openLabel);

        // Horizontal list of expressions for array elements
        elementExprs = new HBox();
        elementExprs.setSpacing(10.0d);
        root.getChildren().add(elementExprs);

        closeLabel = new Label("}");
        closeLabel.setId("item-list");
        root.getChildren().add(closeLabel);

        addElement();
    }

    /**
     * Adds an expression placeholder for an element in the array
     */
    public void addElement() {
        ExprPlaceholder placeholder = new ExprPlaceholder(getExprId(), exprPlaceholders.size());
        exprPlaceholders.add(placeholder);
        elementExprs.getChildren().add(placeholder);
    }

    /**
     * Removes an expression placeholder for an element in the array if size > 0
     */
    public void removeElement() {
        if (exprPlaceholders.size() > 0) {
            elementExprs.getChildren().remove(exprPlaceholders.get(exprPlaceholders.size() - 1));
            exprPlaceholders.remove(exprPlaceholders.size() - 1);
        }
    }

    public void setNumberOfElements(int n) {
        while (exprPlaceholders.size() < n) {
            addElement();
        }

        while (exprPlaceholders.size() > n) {
            removeElement();
        }
    }

    @Override
    public double getWidth() {
        if (elementExprs != null && openLabel != null && closeLabel != null) {
            double width = openLabel.getWidth() + closeLabel.getWidth() + root.getSpacing() * 2;

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                width += elementExprs.getSpacing();
                width += placeholder.getWidth();
            }

            return width;
        } else {
            return 0;
        }
    }

    @Override
    public double getHeight() {
        if (elementExprs != null && openLabel != null && closeLabel != null) {
            double height = openLabel.getHeight();

            for (ExprPlaceholder placeholder : getExprPlaceholders()) {
                height = Math.max(placeholder.getHeight(), height);
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
        boolean complete = true;

        for (int i = 0; i < exprPlaceholders.size() - 1; i++) {
            complete = (exprPlaceholders.get(i).getExpr() == null ? false : exprPlaceholders.get(i).getExpr().isComplete());
        }

        return complete;
    }


}
