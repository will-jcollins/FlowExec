module com.example.flowdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.antlr.antlr4.runtime;

    opens com.example.flowdemo to javafx.graphics;

    exports com.example.flowdemo.view.editor.expr;
    opens com.example.flowdemo.view.editor.expr to javafx.fxml;
    exports com.example.flowdemo.view.editor.cell;
    opens com.example.flowdemo.view.editor.cell to javafx.fxml;
    exports com.example.flowdemo.view.editor;
    opens com.example.flowdemo.view.editor to javafx.fxml;
    exports com.example.flowdemo.view.editor.menu;
    opens com.example.flowdemo.view.editor.menu to javafx.fxml;
    opens com.example.flowdemo.model.transpiler to javafx.graphics;
}