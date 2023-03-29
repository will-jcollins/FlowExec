package com.example.flowdemo.view.editor.expr;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIVar extends UIExpr {

    private TextField field;

    public UIVar(int id) {
        super(id);
        field = new TextField();
        field.setPromptText("Variable Name");
        field.setMaxWidth(PREF_WIDTH);
        field.setMaxHeight(PREF_HEIGHT);
        field.textProperty().addListener((observableValue, oldVal, newVal) -> {
            Pattern idfrPattern = Pattern.compile("([a-z][a-zA-Z0-9]*)?");
            Matcher matcher = idfrPattern.matcher(newVal);
            if (!matcher.find()) {
                field.setText(oldVal);
            }
        });
        getChildren().add(field);

        // Activate mouse target to allow for draggable textfield
        getMouseTarget().toFront();
        setOnMouseClicked(e -> field.requestFocus());
    }

    public TextField getField() {
        return field;
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
    public boolean isComplete() {
        return !field.getText().equals("");
    }
}
