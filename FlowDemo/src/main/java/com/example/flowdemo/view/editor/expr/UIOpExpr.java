package com.example.flowdemo.view.editor.expr;

import com.example.flowdemo.model.flow.expression.Operator;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;

public class UIOpExpr extends UIExpr implements UIExprContainer {

    private HBox row;

    private ExprPlaceholder left;
    private ExprPlaceholder right;
    private ComboBox<String> operator;

    public UIOpExpr(int id) {
        super(id);

        this.row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        getChildren().add(row);

        left = new ExprPlaceholder(id, 0);
        right = new ExprPlaceholder(id, 1);

        operator = new ComboBox<>();
        operator.setMaxWidth(PREF_WIDTH);
        operator.setMaxHeight(PREF_HEIGHT);
        ObservableList<String> options = FXCollections.observableArrayList();
        for (Operator op : Operator.values()) {
            options.add(op.toString());
        }
        operator.getItems().addAll(options);

        // Activate mouse target to allow for draggable ComboBox
        row.getChildren().add(operator);
        row.getChildren().add(0, left);
        row.getChildren().add(right);
    }

    public ComboBox<String> getComboBox() {
        return operator;
    }

    public ReadOnlyObjectProperty<String> getComboxBoxProperty() {
        return operator.getSelectionModel().selectedItemProperty();
    }

    public String getValue() {
        return operator.getSelectionModel().getSelectedItem();
    }

    @Override
    public double getWidth() {
        return left != null && right != null ? left.getWidth() + right.getWidth() + operator.getMaxWidth() + row.getSpacing() * 2 : row.getWidth();
    }

    @Override
    public double getHeight() {
        return left != null && right != null ? Math.max(left.getHeight(), Math.max(right.getHeight(), operator.getMaxHeight())) : row.getHeight();
    }

    @Override
    public String getPseudoLabel() {
        return "(" + left.getPseudoLabel() + " " + (operator.getSelectionModel().getSelectedItem() != null ? operator.getSelectionModel().getSelectedItem() : "") + " " + right.getPseudoLabel() + ")";
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(left, right);
    }

    @Override
    public void setStyleClass(String styleClass) {
        if (styleClass.equals("error")) {
            operator.setBorder(Border.stroke(Color.RED));
        } else {
            operator.setBorder(Border.EMPTY);
        }
    }

    @Override
    public boolean isComplete() {
        return (left.getExpr() != null ? left.getExpr().isComplete() : false) && (right.getExpr() != null ? right.getExpr().isComplete() : false) && !operator.getSelectionModel().getSelectedItem().equals("");
    }
}
