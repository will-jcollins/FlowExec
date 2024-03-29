package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.List;

public class UIReturn extends UICell implements UIExprContainer {
    private StackPane root; // Root node
    private Rectangle back;
    private ExprPlaceholder placeholder;

    public UIReturn(int cellID) {
        super(cellID);

        root = new StackPane();
        getChildren().add(root);

        back = new Rectangle();
        back.setFill(Color.WHITE);
        back.setStroke(Color.BLACK);
        back.setStrokeWidth(2.0d);
        root.getChildren().add(back);

        placeholder = new ExprPlaceholder(cellID, 0);
        root.getChildren().add(placeholder);
    }

    @Override
    public double attachTopX() {
        return back.getWidth() / 2;
    }

    @Override
    public double attachBotX() {
        return back.getWidth() / 2;
    }

    @Override
    public double attachTopY() {
        return 0;
    }

    @Override
    public double attachBotY() {
        return back.getHeight();
    }

    @Override
    public double attachLeftX() {
        return 0;
    }

    @Override
    public double attachRightX() {
        return back.getWidth();
    }

    @Override
    public double attachLeftY() {
        return back.getHeight() / 2;
    }

    @Override
    public double attachRightY() {
        return back.getHeight() / 2;
    }

    @Override
    public double getHeight() {
        return back.getHeight();
    }

    @Override
    public double getWidth() {
        return back.getWidth();
    }

    @Override
    public void updateLayout() {
        double width = (root.getChildren().contains(getPseudoLabel()) ? getPseudoLabel().getWidth() : placeholder.getWidth()) + INSET;
        double height = (root.getChildren().contains(getPseudoLabel()) ? getPseudoLabel().getHeight() : placeholder.getHeight()) + INSET;
        back.setWidth(width);
        back.setHeight(height);
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        if (visible && !root.getChildren().contains(getPseudoLabel())) {
            // Remove input fields
            root.getChildren().remove(placeholder);

            // Add pseudocode label
            getPseudoLabel().setText("Return " + placeholder.getPseudoLabel());
            root.getChildren().add(getPseudoLabel());
        } else if (!visible && !root.getChildren().contains(placeholder)) {
            // Add input fields
            root.getChildren().add(placeholder);

            // Remove pseudocode label
            root.getChildren().remove(getPseudoLabel());
        }
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(placeholder);
    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return (placeholder.getExpr() != null ? placeholder.getExpr().isComplete() : false);
    }
}
