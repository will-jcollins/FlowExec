package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIAssignArray extends UIAssign {

    private Label openLabel;
    private Label closeLabel;
    private ExprPlaceholder indexExpr;

    public UIAssignArray(int cellID) {
        super(cellID);

        closeLabel = new Label("]");
        closeLabel.setId("item-list");
        row.getChildren().add(1, closeLabel);

        // Create textField for variable identifier
        indexExpr = new ExprPlaceholder(cellID, 1);
        row.getChildren().add(1, indexExpr);

        openLabel = new Label("[");
        openLabel.setId("item-list");
        row.getChildren().add(1, openLabel);
    }

    @Override
    public void updateLayout() {
        super.updateLayout();
        if (!root.getChildren().contains(getPseudoLabel())) {
            back.setWidth(back.getWidth() + row.getSpacing() * 3 + indexExpr.getWidth() + closeLabel.getWidth() + openLabel.getWidth());
        }
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        if (visible && !root.getChildren().contains(getPseudoLabel())) {
            // Remove input fields
            root.getChildren().remove(row);

            // Add pseudocode label
            getPseudoLabel().setText("Set " + getVarName().getText() + "[ " + indexExpr.getPseudoLabel() + "]" + " to " + super.getExprPlaceholders().get(0).getPseudoLabel());
            root.getChildren().add(getPseudoLabel());
        } else if (!visible && !root.getChildren().contains(row)) {
            // Add input fields
            root.getChildren().add(row);

            // Remove pseudocode label
            root.getChildren().remove(getPseudoLabel());
        }
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        List<ExprPlaceholder> out = new ArrayList<>();
        out.add(super.getExprPlaceholders().get(0));
        out.add(indexExpr);
        return out;
    }

    @Override
    public boolean isComplete() {
        return super.isComplete() && indexExpr.isComplete();
    }
}
