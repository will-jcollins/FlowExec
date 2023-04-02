package com.example.flowdemo.view.editor.expr;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

public abstract class UIExpr extends Group {
    public static final int IRRELEVANT_ID = -1;
    public static final double PREF_WIDTH = 100.0d;
    public static final double PREF_HEIGHT = 30.0d;
    private final int id;
    private Rectangle mouseTarget;

    public UIExpr(int id) {
        this.id = id;
        mouseTarget = new Rectangle();
        mouseTarget.setOpacity(0.0d);
        getChildren().add(mouseTarget);
        getChildren().addListener((ListChangeListener<? super Node>) e -> {
            mouseTarget.setWidth(getWidth());
            mouseTarget.setHeight(getHeight());
        });
    }

    public Node getMouseTarget() {
        return mouseTarget;
    }

    public int getExprId() {
        return id;
    }

    public double getWidth() {
        return getBoundsInLocal().getWidth();
    }

    public double getHeight() {
        return getBoundsInLocal().getHeight();
    }

    public String getPseudoLabel() {
        return "";
    }

    public abstract boolean isComplete();


}
