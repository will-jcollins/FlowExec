package com.example.flowdemo.view.editor.expr;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

/**
 * Base class for UI classes that represent expressions in the editor
 */
public abstract class UIExpr extends Group {
    public static final int IRRELEVANT_ID = -1;
    public static final double PREF_WIDTH = 100.0d; // Forced width of input widgets (fixes incorrect width reporting)
    public static final double PREF_HEIGHT = 30.0d; // Forced height of input widgets (fixes incorrect height reporting)
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

    public void setStyleClass(String styleClass) {

    }

    public abstract boolean isComplete();


}
