package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;
import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * Visual representation of a flow-chart conditional branch
 */
public class UIDiamond extends UICell {

    private ExprPlaceholder exprPlaceholder; // Expression input for condition
    private Polygon back; // Diamond polygon

    public UIDiamond(int cellID, int branch) {
        super(cellID);

        // Define root node to center child nodes on
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);
        getChildren().add(root);

        // Create expression placeholder for expression inputs
        exprPlaceholder = new ExprPlaceholder(cellID, branch);

        // Define diamond polygon and styling
        back = new Polygon();
        updateLayout();

        // Add children to root node
        root.getChildren().add(back);
        root.getChildren().add(exprPlaceholder);

        // Trigger layout function to ensure proper positioning
        layout();
    }

    /**
     * @return ExprPlaceholder containing condition's expression input
     */
    public ExprPlaceholder getExprPlaceholder() {
        return exprPlaceholder;
    }

    @Override
    public void updateLayout() {
        // Update polygon size to fully accommodate size of expr
        double size = exprPlaceholder.getWidth() + INSET * 3;
        back.getPoints().remove(0,back.getPoints().size());
        back.getPoints().addAll(
                size / 2, 0.0d,
                size, size / 2,
                size / 2, size,
                0.0d, size / 2,
                size / 2, 0.0d);
    }

    @Override
    public void setStyleClass(String styleClass) {
        back.getStyleClass().clear();
        back.getStyleClass().add(styleClass + CSS_SHAPE_CLASS);
    }

    @Override
    public boolean isComplete() {
        return (exprPlaceholder.getExpr() != null ? exprPlaceholder.getExpr().isComplete() : false);
    }
}
