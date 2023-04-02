package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.PolylineArrow;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIExprContainer;
import javafx.scene.control.Label;
import javafx.scene.shape.Polyline;

import java.util.Arrays;
import java.util.List;

/**
 * Visual representation of a ForNode in the Flow model
 */
public class UIIf extends UIFlow implements UIFlowContainer, UIExprContainer {

    private UIFlow ifFlow; // List of cells in the if branch
    private UIFlow elseFlow; // List of cells in the else branch
    private UIDiamond condition; // Conditional expression container
    private PolylineArrow enterIfLine; // Line pointing into the if branch
    private PolylineArrow exitIfLine; // Line pointing out of the if branch
    private Polyline exitElseLine; // Line pointing out of the if-else statement
    // Condition labels
    private Label trueLabel = new Label("True");
    private Label falseLabel = new Label("False");

    public UIIf(int cellID) {
        super(cellID);

        // ifFlow is placed to the right of the elseFlow so don't add it via superclass method addCell
        // Instead place it on javaFX group directly and it will be positioned manually in updateLayout() method
        ifFlow = new UIFlow(cellID,0);
        getChildren().add(ifFlow);

        condition = new UIDiamond(cellID, 0);
        addCell(condition,0);

        elseFlow = new UIFlow(cellID, 1);
        addCell(elseFlow,1);

        trueLabel.setStyle("-fx-font-weight: bold;");
        falseLabel.setStyle("-fx-font-weight: bold;");
        getChildren().add(trueLabel);
        getChildren().add(falseLabel);

        updateLayout();
    }

    @Override
    public double attachTopX() {
        return condition.getTranslateX() + condition.attachTopX();
    }

    @Override
    public double attachBotX() {
        return elseFlow.getTranslateX() + elseFlow.attachBotX();
    }

    @Override
    public double attachBotY() {
        return Math.max(elseFlow.getTranslateY() + elseFlow.attachBotY() + CELL_PADDING * 2,ifFlow.getTranslateY() + ifFlow.attachBotY() + CELL_PADDING * 2);
    }

    @Override
    public double attachTopY() {
        return condition.getTranslateY() + condition.attachTopY();
    }

    @Override
    public List<UIFlow> getFlows() {
        return Arrays.asList(ifFlow, elseFlow);
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(condition.getExprPlaceholder());
    }

    @Override
    public void updateLayout() {
        if (ifFlow != null && elseFlow != null && condition != null) {
            ifFlow.updateLayout();
            elseFlow.updateLayout();
            condition.updateLayout();
            super.updateLayout();

            // Position labels
            falseLabel.setTranslateX(condition.attachBotX() + condition.getTranslateX() + 5);
            falseLabel.setTranslateY(condition.attachBotY() + condition.getTranslateY() + 5);
            trueLabel.setTranslateX(condition.attachRightX() + condition.getTranslateX() + 5);
            trueLabel.setTranslateY(condition.attachRightY() + condition.getTranslateY() + 5);

            // Position line of if cells to the right
            ifFlow.setTranslateX(Math.max(condition.getTranslateX() + condition.attachRightX(), elseFlow.getTranslateX() + elseFlow.getWidth() + INSET));
            ifFlow.setTranslateY(condition.getTranslateY() + condition.attachRightY() + CELL_PADDING);

            // Add flow-line into if branch
            getChildren().remove(enterIfLine);
            enterIfLine = new PolylineArrow(new Double[]{
                    condition.getTranslateX() + condition.attachRightX(), condition.getTranslateY() + condition.attachRightY(),
                    ifFlow.getTranslateX() + ifFlow.attachTopX(), condition.getTranslateY() + condition.attachRightY(),
                    ifFlow.getTranslateX() + ifFlow.attachTopX(), ifFlow.getTranslateY() + ifFlow.attachTopY()
            });
            getChildren().add(enterIfLine);

            // Add flow-line out of if branch
            getChildren().remove(exitIfLine);
            exitIfLine = new PolylineArrow(new Double[]{
                    ifFlow.getTranslateX() + ifFlow.attachBotX(), ifFlow.getTranslateY() + ifFlow.attachBotY(),
                    ifFlow.getTranslateX() + ifFlow.attachBotX(), Math.max(elseFlow.getTranslateY() + elseFlow.attachBotY() + CELL_PADDING, ifFlow.getTranslateY() + ifFlow.attachBotY() + CELL_PADDING),
                    elseFlow.getTranslateX() + elseFlow.attachBotX(), Math.max(elseFlow.getTranslateY() + elseFlow.attachBotY() + CELL_PADDING, ifFlow.getTranslateY() + ifFlow.attachBotY() + CELL_PADDING)
            });
            getChildren().add(exitIfLine);

            // Add flow-line out of cell
            getChildren().remove(exitElseLine);
            exitElseLine = new Polyline();
            exitElseLine.getPoints().addAll(
                    elseFlow.getTranslateX() + elseFlow.attachBotX(), elseFlow.getTranslateY() + elseFlow.attachBotY(),
                    elseFlow.getTranslateX() + elseFlow.attachBotX(), Math.max(elseFlow.getTranslateY() + elseFlow.attachBotY() + CELL_PADDING * 2, ifFlow.getTranslateY() + ifFlow.attachBotY() + CELL_PADDING * 2
                    ));
            getChildren().add(exitElseLine);
        }
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        ifFlow.setPseudoVisible(visible);
        elseFlow.setPseudoVisible(visible);
        condition.setPseudoVisible(visible);
    }

    @Override
    public void setStyleClass(String styleClass) {
        condition.setStyleClass(styleClass);
//        enterIfLine.setStyleClass(styleClass);
//        exitIfLine.setStyleClass(styleClass);
//
//        exitElseLine.getStyleClass().clear();
//        exitElseLine.getStyleClass().add(styleClass + CSS_LINE_CLASS);
    }

    @Override
    public boolean isComplete() {
        boolean condition = ifFlow.isComplete();
        condition = condition && elseFlow.isComplete();
        condition = condition && this.condition.isComplete();

        return condition;
    }
}
