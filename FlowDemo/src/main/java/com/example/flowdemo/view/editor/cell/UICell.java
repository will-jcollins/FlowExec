package com.example.flowdemo.view.editor.cell;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Base class for UI classes that represent FlowNodes in the editor
 */
public abstract class UICell extends Group {
    public static String CSS_SHAPE_CLASS = "-shape"; // Suffix for CSS shape classes
    public static String CSS_LINE_CLASS = "-line"; // Suffix for CSS line classes
    public static int IRRELEVANT_ID = -1; // ID for cells that do not directly relate to a model
    protected static double INSET = 20.0d; // Value cells space elements by
    private final int cellID; // ID of the FlowNode the cell represents
    private Label pseudoLabel = new Label();

    public UICell(int cellID) {
        this.cellID = cellID;
    }

    /**
     * @return ID of the FLowNode the cell represents
     */
    public int getCellID() {
        return cellID;
    }

    /**
     * @return Point lines should attach to at the top of the cell offset from getTranslateX()
     */
    public double attachTopX() {
        return getBoundsInLocal().getWidth() / 2;
    }

    /**
     * @return Point lines should attach to at the bottom of the cell offset from getTranslateX()
     */
    public double attachBotX() {
        return getBoundsInLocal().getWidth() / 2;
    }

    /**
     * @return Point lines should attach to at the top of the cell offset from getTranslateY()
     */
    public double attachTopY() {
        return 0;
    }

    /**
     * @return Point lines should attach to at the bottom of the cell offset from getTranslateY()
     */
    public double attachBotY() {
        layout();
        return getHeight();
    }

    /**
     * @return Point lines should attach to at the left of the cell offset from getTranslateX()
     */
    public double attachLeftX() {
        return 0;
    }

    /**
     * @return Point lines should attach to at the right of the cell offset from getTranslateX()
     */
    public double attachRightX() {
        return getWidth();
    }

    /**
     * @return Point lines should attach to at left top of the cell offset from getTranslateY()
     */
    public double attachLeftY() {
        return getHeight() / 2;
    }

    /**
     * @return Point lines should attach to at the right of the cell offset from getTranslateY()
     */
    public double attachRightY(){
        return getHeight() / 2;
    }

    /**
     * @return Height of the cell
     */
    public double getHeight() {
        return getBoundsInLocal().getHeight();
    }

    /**
     * @return Width of the cell
     */
    public double getWidth() {
        return getBoundsInLocal().getWidth();
    }

    /**
     * Replaces the CSS style class to input string
     * @param styleClass new style class
     */
    public void setStyleClass(String styleClass) {
        getStyleClass().clear();
        getStyleClass().add(styleClass);
    }

    /**
     * Getter for pseudocode label JavaFX object
     * @return pseudocode label
     */
    public Label getPseudoLabel() {
        return pseudoLabel;
    }

    /**
     * Sets pseudocode labels visibility
     * @param visible whether pseudocode labels should be visible
     */
    public void setPseudoVisible(boolean visible) {

    }

    /**
     * Indicates if a cell has all its information completed
     * @return boolean
     */
    public abstract boolean isComplete();

    /**
     * Resizes and repositions elements within the cell to be correct
     */
    public abstract void updateLayout();
}
