package com.example.flowdemo.view.editor.cell;

import com.example.flowdemo.view.editor.PolylineArrow;
import com.example.flowdemo.view.editor.EditorViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Visual representation of a list of cells in a flow-chart
 */
public class UIFlow extends UICell {
    protected static final double CELL_PADDING = 30.0d; // Distance between cells
    private LinkedList<UICell> cells = new LinkedList<>(); // List of cells in UIFlow
    private LinkedList<PolylineArrow> lines = new LinkedList<>(); // List of lines in UIFlow
    private CellPlaceholder placeholder; // Placeholder that is shown when no cells are in the UIFlow

    public UIFlow(int cellID) {
        super(cellID);
        construct(cellID, 0);
    }

    public UIFlow(int cellID, int branch) {
        super(cellID);
        construct(cellID, branch);
    }

    private void construct(int cellID, int branch) {
        placeholder = new CellPlaceholder(cellID,branch);
        addCell(placeholder, 0);
    }



    /**
     * Inserts a cell into the visual flow and draws necessary flow-lines
     * @param cell cell to be inserted
     * @param index position in flow to insert cell
     */
    public void addCell(UICell cell, int index) {
        // Show placeholder if no cells are on the flow
        if (cells.contains(placeholder)) {
            getChildren().remove(placeholder);
            cells.remove(placeholder);
        }

        // Calculate correct list index to add cell into flow
        index = Math.max(Math.min(index,cells.size()),0);
        cells.add(index,cell);
        getChildren().add(cell);

        // Calculate positions of cells and insert flow-lines
        updateLayout();
    }

    /**
     * Removes a cell from the visual flow and updates flow-lines
     * @param cell cell to be removed
     */
    public void removeCell(UICell cell) {
        // Calculate correct list index and remove from flow
        cells.remove(cell);
        getChildren().remove(cell);

        // Add placeholder if no cells are left, and it's not already on the flow
        if (!cells.contains(placeholder) && cells.size() <= 0) {
            addCell(placeholder, 0);
        } else {
            // Calculate positions of cells and insert flow-lines
            updateLayout();
        }
    }

    /**
     * @return List of cells in the UIFlow
     */
    public List<UICell> getCells() {
        LinkedList<UICell> out = new LinkedList<>(cells);

        // Don't include placeholder as this isn't in the model
        out.remove(placeholder);

        return out;
    }

    /**
     * @return CellPlaceholder object in the UIFlow
     */
    public CellPlaceholder getPlaceholder() {
        return placeholder;
    }

    @Override
    public double attachTopX() {
        return cells.size() > 0 ? cells.getFirst().getTranslateX() + cells.getFirst().attachTopX() : super.attachTopX();
    }

    @Override
    public double attachBotX() {
        return cells.size() > 0 ? cells.getLast().getTranslateX() + cells.getLast().attachBotX() : super.attachBotX();
    }

    @Override
    public double attachBotY() {
        return cells.size() > 0 ? cells.getLast().getTranslateY() + cells.getLast().attachBotY() : super.attachBotY();
    }

    @Override
    public double attachTopY() {
        return cells.size() > 0 ? cells.getFirst().attachTopY() : super.attachTopY();
    }

    @Override
    public void updateLayout() {
        layout(); // Call forces nodes to correctly report their position and size in the group

        // Recursively update layout of cells
        for (UICell cell : cells) {
            cell.updateLayout();
        }

        // Reset lines
        for (int i = 0; i < lines.size(); i++) {
            getChildren().remove(lines.get(i));
        }
        lines.subList(0, lines.size()).clear();

        for (int i = 1; i < cells.size(); i++) {
            // Position cell correctly
            UICell prevCell = cells.get(i-1);
            UICell cell = cells.get(i);
            double xOffset = prevCell.getTranslateX() + (prevCell.attachBotX() - cell.attachTopX());
            double yOffset = prevCell.getTranslateY() + prevCell.attachBotY() + cell.attachTopY() + CELL_PADDING;
            cell.setTranslateX(xOffset);
            cell.setTranslateY(yOffset);

            // Draw flow line
            PolylineArrow flowLine = new PolylineArrow(new Double[]{
                    cell.getTranslateX()+cell.attachTopX(), prevCell.getTranslateY() + prevCell.attachBotY(),
                    cell.getTranslateX()+cell.attachTopX(), yOffset
            });
            lines.add(flowLine);
            getChildren().add(flowLine);
        }

        // Push lines behind cells
        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).toBack();
        }

        // Ensure all cells' setTranslateX is positive (ensures correct positioning in child classes)
        double minTranslate = 0;
        for (Node node : getChildren()) {
            minTranslate = Math.min(node.getTranslateX(), minTranslate);
        }

        minTranslate = Math.abs(minTranslate);

        for (Node node : getChildren()) {
            node.setTranslateX(node.getTranslateX() + minTranslate);
        }

//
//        for (UICell cell : cells) {
//            cell.setTranslateX(cell.getTranslateX() + minTranslate);
//        }
//        for (PolylineArrow line : lines) {
//            line.setTranslateX(line.getTranslateX() + minTranslate);
//        }
    }

    @Override
    public void setPseudoVisible(boolean visible) {
        for (UICell cell : cells) {
            cell.setPseudoVisible(visible);
        }
    }

    @Override
    public boolean isComplete() {
        boolean complete = true;

        for (UICell cell : cells) {
            complete = complete && cell.isComplete();
        }

        return complete;
    }
}