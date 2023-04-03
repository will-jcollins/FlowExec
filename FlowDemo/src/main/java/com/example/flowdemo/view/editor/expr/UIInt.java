package com.example.flowdemo.view.editor.expr;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UIInt extends UIExpr {
    private static final Pattern intRegex = Pattern.compile("-?\\d*");
    TextField field;

    public UIInt(int id) {
        super(id);
        field = new TextField();
        field.setPromptText("int");
        field.textProperty().addListener((ov, oldValue, newValue) -> {
            Matcher m = intRegex.matcher(newValue);
            if (!m.matches()) {
                field.setText(oldValue);
            }
        });

        getChildren().add(field);
        field.setMaxWidth(PREF_WIDTH);
        field.setMaxHeight(PREF_HEIGHT);

        // Activate mouse target to allow for draggable textfield
        getMouseTarget().toFront();
        setOnMouseClicked(e -> field.requestFocus());
    }

    public StringProperty getTextProperty() {
        return field.textProperty();
    }

    public String getText() {
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
        return field.getText();
    }

    @Override
    public void setStyleClass(String styleClass) {
        field.getStyleClass().clear();
        field.getStyleClass().add(".text-field");
        field.getStyleClass().add(styleClass + "-field");
    }

    @Override
    public boolean isComplete() {
        return !field.getText().equals("");
    }
}
