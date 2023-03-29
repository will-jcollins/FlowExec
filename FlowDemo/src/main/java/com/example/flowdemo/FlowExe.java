package com.example.flowdemo;

import com.example.flowdemo.factory.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class FlowExe extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ViewHandler vh = new ViewHandler();
        vh.start();
    }
}
