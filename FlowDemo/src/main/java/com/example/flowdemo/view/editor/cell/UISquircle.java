package com.example.flowdemo.view.editor.cell;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UISquircle extends UICell {

    private Rectangle back;

    public UISquircle(String txtStr, int cellID) {
        super(cellID);
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);

        Text text = new Text(txtStr);

        back = new Rectangle(text.getBoundsInLocal().getWidth() * 2.0, text.getBoundsInLocal().getHeight() * 1.5, Color.WHITE);
        back.setStroke(Color.BLACK);
        back.setArcHeight(25.0);
        back.setArcWidth(25.0);
        root.getChildren().add(back);
        root.getChildren().add(text);

        root.layout();
        layout();
        setStyleClass("default-shape");
    }

    @Override
    public void updateLayout() {

    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return true;
    }
}
