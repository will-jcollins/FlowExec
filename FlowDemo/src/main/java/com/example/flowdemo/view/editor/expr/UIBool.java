package com.example.flowdemo.view.editor.expr;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class UIBool extends UIExpr {
    private ComboBox<String> comboBox;
    public UIBool(int id) {
        super(id);

        this.comboBox = new ComboBox<>();
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "False",
                        "True"
                );
        comboBox.getItems().addAll(options);
        comboBox.getSelectionModel().select("True");
        comboBox.setMaxWidth(PREF_WIDTH);
        comboBox.setMaxHeight(PREF_HEIGHT);
        getChildren().add(comboBox);

        // Activate mouse target to allow for draggable combo box
        getMouseTarget().toFront();
        setOnMouseClicked(e -> comboBox.show());
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }

    public ReadOnlyObjectProperty<String> getComboxBoxProperty() {
        return comboBox.getSelectionModel().selectedItemProperty();
    }

    public String getValue() {
        return comboBox.getSelectionModel().getSelectedItem();
    }

    @Override
    public double getWidth() {
        return comboBox.getMaxWidth();
    }

    @Override
    public double getHeight() {
        return comboBox.getMaxHeight();
    }

    @Override
    public boolean isComplete() {
        return !comboBox.getSelectionModel().getSelectedItem().equals("");
    }
}
