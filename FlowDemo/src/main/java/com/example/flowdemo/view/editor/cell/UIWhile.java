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
        return Math.min(condition.getTranslateX(), bodyFlow.getTranslateX());
    }

    @Override
    public double attachBotY() {
        return bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING;
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

            // Add line leading from bodyFLow to condition
            getChildren().remove(exitBody);
            double maxPoint = Math.max(condition.getTranslateX() + condition.getWidth(), bodyFlow.getTranslateX() + bodyFlow.getWidth());
            exitBody = new PolylineArrow(new Double[]{
                bodyFlow.getTranslateX() + bodyFlow.attachBotX(), bodyFlow.getTranslateY() + bodyFlow.attachBotY(),
                bodyFlow.getTranslateX() + bodyFlow.attachBotX(), bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING / 2,
                maxPoint, bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING / 2,
                maxPoint, condition.getTranslateY() + condition.attachRightY(),
                condition.getTranslateX() + condition.attachRightX(), condition.getTranslateY() + condition.attachRightY()
            });
            getChildren().add(exitBody);

            // Add line leading from condition to next FlowNode
            getChildren().remove(exitLoop);
            double minPoint = Math.min(condition.getTranslateX(), bodyFlow.getTranslateX());
            exitLoop = new Polyline();
            exitLoop.getStyleClass().add("default-line");
            exitLoop.getPoints().addAll(
                condition.getTranslateX(), condition.getTranslateY() + condition.attachLeftY(),
                    minPoint, condition.getTranslateY() + condition.attachLeftY(),
                    minPoint, bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING
            );
            getChildren().add(exitLoop);

            // Position labels
            trueLabel.setTranslateX(condition.attachBotX() + condition.getTranslateX() + 5);
            trueLabel.setTranslateY(condition.attachBotY() + condition.getTranslateY() + 5);
            falseLabel.setTranslateX(condition.attachLeftX() + condition.getTranslateX() - falseLabel.getBoundsInLocal().getWidth());
            falseLabel.setTranslateY(condition.attachLeftY() + condition.getTranslateY() - falseLabel.getBoundsInLocal().getHeight());
        }
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        condition.setPseudoVisible(visible);
        bodyFlow.setPseudoVisible(visible);
    }

    @Override
    public void setStyleClass(String styleClass) {
        condition.setStyleClass(styleClass);
//        exitBody.setStyleClass(styleClass);

//        exitLoop.getStyleClass().clear();
//        exitLoop.getStyleClass().add(styleClass + CSS_LINE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return bodyFlow.isComplete() && condition.isComplete();
    }
}


