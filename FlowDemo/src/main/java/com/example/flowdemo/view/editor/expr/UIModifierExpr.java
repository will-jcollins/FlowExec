package com.example.flowdemo.view.editor.expr;

import com.example.flowdemo.model.flow.expression.Modifier;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

import java.util.Arrays;
import java.util.List;

public class UIModifierExpr extends UIExpr implements UIExprContainer {

    private HBox row;
    private ExprPlaceholder expr;
    private ComboBox<String> modifier;

    public UIModifierExpr(int id) {
        super(id);

        this.row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        getChildren().add(row);

        expr = new ExprPlaceholder(id, 0);

        modifier = new ComboBox<>();
        modifier.setMaxWidth(PREF_WIDTH * 0.75);
        modifier.setMaxHeight(PREF_HEIGHT);
        ObservableList<String> options = FXCollections.observableArrayList();
        for (Modifier mod : Modifier.values()) {
            options.add(mod.toString());
        }
        modifier.getItems().addAll(options);

        // Activate mouse target to allow for draggable textfield
        row.getChildren().add(modifier);
//        getMouseTarget().toFront();
//        setOnMouseClicked(e -> operator.show());

        row.getChildren().add(expr);
    }

    public ComboBox<String> getComboBox() {
        return modifier;
    }

    public ReadOnlyObjectProperty<String> getComboxBoxProperty() {
        return modifier.getSelectionModel().selectedItemProperty();
    }

    public String getModifier() {
        return modifier.getSelectionModel().getSelectedItem();
    }

    @Override
    public double getWidth() {
        return expr != null ? expr.getWidth() + modifier.getMaxWidth() + row.getSpacing() : row.getWidth();
    }

    @Override
    public double getHeight() {
        return expr != null ? Math.max(expr.getHeight(), modifier.getMaxHeight()) : row.getHeight();
    }

    @Override
    public List<ExprPlaceholder> getExprPlaceholders() {
        return Arrays.asList(expr);
    }

    @Override
    public boolean isComplete() {
        return (expr.getExpr() != null ? expr.getExpr().isComplete() : false) && !modifier.getSelectionModel().getSelectedItem().equals("");
    }
}
