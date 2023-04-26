package com.example.flowdemo.factory;

import com.example.flowdemo.model.flow.nodes.Flow;
import com.example.flowdemo.view.editor.EditorView;
import com.example.flowdemo.view.editor.EditorViewModel;
import com.example.flowdemo.view.editor.cell.*;
import com.example.flowdemo.view.editor.expr.UIArray;
import com.example.flowdemo.view.editor.expr.UIBool;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class ViewHandler {

    private Stage stage;

    public ViewHandler() {
        this.stage = new Stage();
    }

    public void start() {
        openEditorView();
        stage.show();
    }

    public void openEditorView() {
        Pane root = new EditorView(new EditorViewModel());
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Load CSS
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(classloader.getResource("style.css").toExternalForm());
    }
}
