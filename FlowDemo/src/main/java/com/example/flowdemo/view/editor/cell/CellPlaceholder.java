package com.example.flowdemo.view.editor.cell;

import javafx.beans.property.BooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Placeholder for a flow of cells
 */
public class CellPlaceholder extends UICell {

    private Rectangle shape;
    private int branch;

    /**
     * @param cellID ID of the cell the placeholder relates to
     * @param branch flow branch index of the placeholder (e.g. for if-else if is 0 and else is 1)
     */
    public CellPlaceholder(int cellID, int branch) {
        super(cellID);

        this.branch = branch;

        // Set visual options for its shape
        shape = new Rectangle();
        shape.setHeight(INSET * 2);
        shape.setWidth(INSET * 4);
        shape.getStrokeDashArray().addAll(2d,12d,12d,2d);
        shape.setStrokeWidth(2.0d);
        shape.setStroke(Color.BLACK);
        shape.setFill(Color.TRANSPARENT);
        getChildren().add(shape);
    }

    /**
     * @return index of the Flow the placeholder is a part of within another FlowNode
     */
    public int getBranch() {
        return branch;
    }

    /**
     * Updates the colour of the shape's stroke
     * @param paint new colour
     */
    public void setStrokeFill(Paint paint) {
        shape.setStroke(paint);
    }

    @Override
    public void updateLayout() {}

    @Override
    public boolean isComplete() {
        return true;
    }
}
