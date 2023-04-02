package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.PolylineArrow;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;
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
    private Line paddingPixel = new Line();

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
        double maxWidth = Math.max(bodyFlow.getBoundsInLocal().getWidth(), condition.getBoundsInLocal().getWidth());
        return condition.getTranslateX() + condition.attachLeftX() - maxWidth;
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
        getChildren().remove(paddingPixel);
        if (bodyFlow != null && condition != null) {
            condition.updateLayout();
            bodyFlow.updateLayout();
            super.updateLayout();

            // Add line leading from bodyFLow to condition
            double maxWidth = Math.max(bodyFlow.getBoundsInLocal().getWidth(), condition.getBoundsInLocal().getWidth());
            getChildren().remove(exitBody);
            exitBody = new PolylineArrow(new Double[]{
                    bodyFlow.getTranslateX() + bodyFlow.attachBotX(), bodyFlow.getTranslateY() + bodyFlow.attachBotY(),
                    bodyFlow.getTranslateX() + bodyFlow.attachBotX(), bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING,
                    bodyFlow.getTranslateX() + bodyFlow.attachBotX() + maxWidth, bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING,
                    bodyFlow.getTranslateX() + bodyFlow.attachBotX() + maxWidth, condition.getTranslateY() + condition.attachRightY(),
                    condition.getTranslateX() + condition.attachRightX(), condition.getTranslateY() + condition.attachRightY(),
            });
            getChildren().add(exitBody);

            // Ensure all cells' setTranslateX is positive (ensures correct positioning in child classes)
            double minTranslate = 0;
            for (Node node : getChildren()) {
                minTranslate = Math.min(node.getTranslateX(), minTranslate);
            }

            minTranslate = Math.abs(minTranslate);

            for (Node node : getChildren()) {
                node.setTranslateX(node.getTranslateX() + minTranslate);
            }

            // Add line leading from condition to next FlowNode
            getChildren().remove(exitLoop);
            exitLoop = new Polyline();
            exitLoop.getStyleClass().add("default-line");
            exitLoop.getPoints().addAll(
                condition.getTranslateX() + condition.attachLeftX(), condition.getTranslateY() + condition.attachLeftY(),
                    attachBotX(), condition.getTranslateY() + condition.attachLeftY(),
                    attachBotX(), attachBotY()
            );
            getChildren().add(exitLoop);

            // Ensure all cells' setTranslateX is positive (ensures correct positioning in child classes)
            minTranslate = 0;
            for (Node node : getChildren()) {
                minTranslate = Math.min(node.getTranslateX(), minTranslate);
            }

            minTranslate = Math.abs(minTranslate);

            for (Node node : getChildren()) {
                node.setTranslateX(node.getTranslateX() + minTranslate);
            }

            getChildren().add(paddingPixel);
            paddingPixel.setStartX(bodyFlow.getTranslateX() + bodyFlow.attachBotX() + maxWidth + INSET);
            paddingPixel.setStartY(bodyFlow.getTranslateY() + bodyFlow.attachBotY() + CELL_PADDING + INSET);
            paddingPixel.setEndX(paddingPixel.getStartX());
            paddingPixel.setEndY(paddingPixel.getStartY());
            paddingPixel.setOpacity(0);

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


