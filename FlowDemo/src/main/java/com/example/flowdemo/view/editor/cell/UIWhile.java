package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.PolylineArrow;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Polyline;

import java.util.Arrays;
import java.util.List;

public class UIWhile extends UIFlow implements UIFlowContainer, UIExprContainer {

    private UIDiamond condition;
    private UIFlow bodyFlow;
    private PolylineArrow exitBody;
    private Polyline exitLoop;
    private Label trueLabel = new Label("True");
    private Label falseLabel = new Label("False");

    public UIWhile(int cellID) {
        super(cellID);
        condition = new UIDiamond(cellID, 0);
        bodyFlow = new UIFlow(cellID);
        addCell(condition,0);
        addCell(bodyFlow,1);
        getChildren().add(trueLabel);
        getChildren().add(falseLabel);
    }

    @Override
    public double attachTopX() {
        return condition.getTranslateX() + condition.attachTopX();
    }

    @Override
    public double attachTopY() {
        return condition.attachTopY();
    }

    @Override
    public double attachBotX() {
        return condition.getTranslateX() - INSET - Math.max(0, condition.getTranslateX() - bodyFlow.getTranslateX());
    }

    @Override
    public double attachBotY() {
        return bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING / 2;
    }

    @Override
    public List<UIFlow> getFlows() {
        return Arrays.asList(bodyFlow);
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(condition.getExprPlaceholder());
    }

    @Override
    public void updateLayout() {
        if (bodyFlow != null && condition != null) {
            condition.updateLayout();
            bodyFlow.updateLayout();
            super.updateLayout();

            // Add loop flowlines
            double exitBodyPadding = INSET + Math.max(bodyFlow.attachRightX() - bodyFlow.attachBotX(), condition.getTranslateX() + condition.attachRightX() - (bodyFlow.getTranslateX() + bodyFlow.attachBotX())); // Space between line leading out of loop body into condition and body flow
            ObservableList<String> styleClass = exitLoop != null ? exitLoop.getStyleClass() : new SimpleListProperty<>();
            getChildren().remove(exitBody);
            exitBody = new PolylineArrow(new Double[]{
                    super.attachBotX(), super.attachBotY(),
                    super.attachBotX(), super.attachBotY() + CELL_PADDING / 2,
                    super.attachBotX() + exitBodyPadding, super.attachBotY() + CELL_PADDING / 2,
                    super.attachBotX() + exitBodyPadding, condition.getTranslateY() + condition.attachRightY(),
                    condition.getTranslateX() + condition.attachRightX(), condition.getTranslateY() + condition.attachRightY(),
            });
            exitBody.getStyleClass().addAll(styleClass);
            getChildren().add(exitBody);


            styleClass = exitLoop != null ? exitLoop.getStyleClass() : new SimpleListProperty<>();
            getChildren().remove(exitLoop);

            double exitLoopPadding = INSET + Math.max(0, condition.getTranslateX() - bodyFlow.getTranslateX());
            exitLoop = new Polyline();
            exitLoop.getStyleClass().addAll(styleClass);
            exitLoop.getPoints().addAll(
                    condition.getTranslateX() + condition.attachLeftX(), condition.getTranslateY() + condition.attachLeftY(),
                    condition.getTranslateX() + condition.attachLeftX() - exitLoopPadding, condition.getTranslateY() + condition.attachLeftY(),
                    condition.getTranslateX() + condition.attachLeftX() - exitLoopPadding, bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING / 2);
            getChildren().add(exitLoop);


            // Position labels
            trueLabel.setTranslateX(condition.attachBotX() + condition.getTranslateX() + 5);
            trueLabel.setTranslateY(condition.attachBotY() + condition.getTranslateY() + 5);
            falseLabel.setTranslateX(condition.attachLeftX() + condition.getTranslateX() - Math.max(exitLoopPadding,falseLabel.getBoundsInLocal().getWidth()));
            falseLabel.setTranslateY(condition.attachLeftY() + condition.getTranslateY() - falseLabel.getBoundsInLocal().getHeight());

            double minTranslate = 0;
            for (Node node : getChildren()) {
                minTranslate = Math.min(node.getTranslateX(), minTranslate);
            }

            for (Node node : getChildren()) {
                node.setTranslateX(node.getTranslateX() + Math.abs(minTranslate));
            }
        }
    }

    @Override
    public void setStyleClass(String styleClass) {
        condition.setStyleClass(styleClass);
        exitBody.setStyleClass(styleClass);

        exitLoop.getStyleClass().clear();
        exitLoop.getStyleClass().add(styleClass + CSS_LINE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return bodyFlow.isComplete() && condition.isComplete();
    }
}


