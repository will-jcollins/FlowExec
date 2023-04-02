package com.example.flowdemo.view.editor.expr;

import com.example.flowdemo.view.editor.cell.UICell;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class UIChar extends UIExpr {

    private TextField field;

    public UIChar(int id) {
        super(id);
        field = new TextField();
        field.setPromptText("char");
        field.textProperty().addListener((ov, oldValue, newValue) -> {
            if (field.getText().length() > 1) {
                String s = field.getText().substring(0, 1);
                field.setText(s);
            }
        });
        field.setMaxWidth(PREF_WIDTH);
        field.setMaxHeight(PREF_HEIGHT);
        getChildren().add(field);

        // Activate mouse target to allow for draggable textfield
        getMouseTarget().toFront();
        setOnMouseClicked(e -> field.requestFocus());
    }

    public StringProperty getTextProperty() {
        return field.textProperty();
    }

    public String getValue() {
        return field.getText();
    }

    @Override
    public double getWidth() {
        return field.getMaxWidth();
    }

    @Override
    public double getHeight() {
        return field.getMaxHeight();
    }

    @Override
    public String getPseudoLabel() {
        return "'" + field.getText() + "'";
    }

    @Override
    public boolean isComplete() {
        return !field.getText().equals("");
    }
}
