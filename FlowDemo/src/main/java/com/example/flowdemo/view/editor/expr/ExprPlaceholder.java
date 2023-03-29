package com.example.flowdemo.view.editor.expr;

import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class ExprPlaceholder extends UIExpr {

    private Rectangle container;
    private UIExpr expr;
    private int branch;

    public ExprPlaceholder(int id, int branch) {
        super(id);

        this.branch = branch;

        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setColor(Color.web("0x000000"));

        container = new Rectangle(50,25);
        container.setStroke(Color.BLACK);
        container.setStrokeWidth(2.0d);
        container.setFill(Color.WHITE);
        container.setEffect(innerShadow);
        getChildren().add(container);

        getChildren().remove(getMouseTarget());
    }

    public UIExpr getExpr() {
        return expr;
    }

    public int getBranch() {
        return branch;
    }

    public void setExpr(UIExpr e) {
        if (e != null) {
            getChildren().remove(expr);
            expr = e;
            getChildren().add(expr);
            getChildren().remove(container);
        } else {
            clearExpr();
        }
    }

    public void setStroke(Paint color) {
        container.setStroke(color);
    }

    public void clearExpr() {
        getChildren().remove(expr);
        expr = null;

        if (!getChildren().contains(container)) {
            getChildren().add(container);
        }
    }

    @Override
    public double getWidth() {
        return (expr == null) ? container.getWidth() : expr.getWidth();
    }

    @Override
    public double getHeight() {
        return (expr == null) ? container.getHeight() : expr.getHeight();
    }

    @Override
    public boolean isComplete() {
        return true;
    }
}
