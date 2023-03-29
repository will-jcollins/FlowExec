package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.model.flow.expression.Operator;
import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import com.example.flowdemo.view.editor.expr.UIInt;
import com.example.flowdemo.view.editor.expr.UIOpExpr;
import com.example.flowdemo.view.editor.expr.UIVar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

/**
 * Visual representation of a ForNode in the Flow model
 */
public class UIFor extends UIWhile {
    private UIDeclareAssign declaration; // Declaration of counter
    private UIAssign increment; // Increment of counter
    private UIOpExpr lessThanExpr; // Condition expression
    private UIFlow bodyFlow;

    public UIFor(int cellID) {
        super(cellID);

        // Declaration of counter variable
        declaration = new UIDeclareAssign(cellID);
        // Set declaration type to only be an integer
        declaration.getTypes().getSelectionModel().select("Integer");
        declaration.getTypes().setDisable(true);
        // Add declaration to start of flow
        addCell(declaration, 0);

        // Increment of counter variable
        increment = new UIAssign(cellID);
        increment.getVarName().setDisable(true); // Should not be editable

        UIVar incrementVar = new UIVar(cellID);
        UIInt incrementInt = new UIInt(cellID);
        incrementInt.getTextProperty().set("1");

        // Create UIOpExpr in format incrementVar < incrementInt
        UIOpExpr incrementExpr = new UIOpExpr(cellID);
        // Set operator to only be addition
        incrementExpr.getComboBox().getSelectionModel().select(Operator.Add.toString());
        incrementExpr.getComboBox().setDisable(true);
        // Set left and right expression to be incrementVar and incrementInt
        incrementExpr.getExprPlaceholders().get(0).setExpr(incrementVar);
        incrementExpr.getExprPlaceholders().get(1).setExpr(incrementInt);

        // Set increment assignment to built incrementExpr and disable it so it can't be changed
        increment.getExprPlaceholders().get(0).setExpr(incrementExpr);
        increment.setDisable(true);
        // Add increment to end of body flow
        super.getFlows().get(0).addCell(increment, 0);

        // Set parent while loop condition to a less than expression
        ExprPlaceholder condition = super.getExprPlaceholders().get(0);
        // Build lessThanExpr in format variableExpr + [user input expression]
        lessThanExpr = new UIOpExpr(cellID);
        // Make variableExpr uneditable
        UIVar varExpr = new UIVar(cellID);
        varExpr.getField().setDisable(true);
        // Set lessThanExpr to use less than operator that can't be changed
        lessThanExpr.getComboBox().getSelectionModel().select(Operator.Less.toString());
        lessThanExpr.getComboBox().setDisable(true);
        lessThanExpr.getExprPlaceholders().get(0).setExpr(varExpr);
        // set expression to built lessThanExpr
        condition.setExpr(lessThanExpr);

        // Make variable identifier input in declaration change variable identifier input in increment and condition
        declaration.getVarName().textProperty().addListener(e -> {
            increment.getVarName().setText(declaration.getVarName().getText());
            varExpr.getField().setText(declaration.getVarName().getText());
            incrementVar.getTextProperty().set(declaration.getVarName().getText());
        });

        // Add bodyFlow user can add cells to
        bodyFlow = new UIFlow(cellID);
        super.getFlows().get(0).addCell(bodyFlow,0);
    }

    /**
     * @return StringProperty of the counter variable identifier TextField
     */
    public StringProperty getIdentifier() {
        return declaration.getVarName().textProperty();
    }

    @Override
    public double attachTopX() {
        return declaration.getTranslateX() + declaration.attachTopX();
    }

    @Override
    public double attachTopY() {
        return declaration.attachTopY();
    }

    @Override
    public List<UIFlow> getFlows() {
        return Arrays.asList(bodyFlow);
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(declaration.getExprPlaceholders().get(0), lessThanExpr.getExprPlaceholders().get(1));
    }

    @Override
    public void updateLayout() {
        super.updateLayout();
    }

    @Override
    public void setStyleClass(String styleClass) {
        super.setStyleClass(styleClass);
        declaration.setStyleClass(styleClass);
        increment.setStyleClass(styleClass);
    }

    @Override
    public boolean isComplete() {
        boolean complete = bodyFlow.isComplete();
        complete = complete && declaration.isComplete();
        complete = complete && increment.isComplete();

        return complete;
    }
}
