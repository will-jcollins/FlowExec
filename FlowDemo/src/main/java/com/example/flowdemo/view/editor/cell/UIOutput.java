package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;

import java.util.Arrays;
import java.util.List;


public class UIOutput extends UICell implements UIExprContainer {

    private ExprPlaceholder exprPlaceholder;
    private Polygon parallelogram;

    public UIOutput(int cellID) {
        super(cellID);

        StackPane root = new StackPane();
        getChildren().add(root);

        exprPlaceholder = new ExprPlaceholder(cellID, 0);

        parallelogram = new Polygon();
        updateLayout();
        root.getChildren().add(parallelogram);
        root.getChildren().add(exprPlaceholder);
    }

    @Override
    public List<com.example.flowdemo.view.editor.expr.ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(exprPlaceholder);
    }

    @Override
    public double attachTopX() {
        return parallelogram.getBoundsInLocal().getWidth() / 2;
    }

    @Override
    public double attachBotX() {
        return parallelogram.getBoundsInLocal().getWidth() / 2;
    }

    @Override
    public double attachTopY() {
        return 0;
    }

    @Override
    public double attachBotY() {
        return parallelogram.getBoundsInLocal().getHeight();
    }

    @Override
    public double attachLeftX() {
        return 0;
    }

    @Override
    public double attachRightX() {
        return parallelogram.getBoundsInLocal().getWidth();
    }

    @Override
    public double attachLeftY() {
        return parallelogram.getBoundsInLocal().getHeight() / 2;
    }

    @Override
    public double attachRightY() {
        return parallelogram.getBoundsInLocal().getHeight() / 2;
    }

    @Override
    public void updateLayout() {
        // Update polygon size to fully accommodate size of expr
        double width = exprPlaceholder.getWidth() + INSET * 2;
        double height = exprPlaceholder.getHeight() + INSET;
        parallelogram.getPoints().remove(0,parallelogram.getPoints().size());
        parallelogram.getPoints().addAll(
                INSET / 2,    0d,
                width,             0d,
                width - INSET / 2, height,
                0d,                 height,
                INSET / 2,         0d
        );
    }

    @Override
    public void setStyleClass(String styleClass) {
        parallelogram.getStyleClass().clear();
        parallelogram.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return (exprPlaceholder.getExpr() != null ? exprPlaceholder.getExpr().isComplete() : false);
    }
}
