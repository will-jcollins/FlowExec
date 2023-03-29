package com.example.flowdemo.view.editor;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;

public class PolylineArrow extends Group {
    Polyline line = new Polyline();
    Polygon head = new Polygon();
    public PolylineArrow(Double[] points) {
        super();

        // Include arrow if more than 2 points exist
        if (4 <= points.length) {
            head = new Polygon();
            getChildren().add(head);

            // Derive arrow information from Polyline vertices
            double lastX2 = points[points.length - 4];
            double lastY2 = points[points.length - 3];
            double lastX = points[points.length - 2];
            double lastY = points[points.length - 1];

            if (lastX < lastX2) {
                // Arrow needs to face east
                head.getPoints().addAll(
                        lastX + 5, lastY - 5,
                        lastX + 5, lastY + 5,
                        lastX, lastY);
            } else if (lastX > lastX2) {
                // Arrow needs to face west
                head.getPoints().addAll(
                        lastX - 5, lastY - 5,
                        lastX - 5, lastY + 5,
                        lastX, lastY);
            } else if (lastY < lastY2) {
                // Arrow needs to face north
                head.getPoints().addAll(
                        lastX - 5, lastY + 5,
                        lastX + 5, lastY + 5,
                        lastX, lastY);
            } else {
                // Arrow needs to face south
                head.getPoints().addAll(
                        lastX - 5, lastY - 5,
                        lastX + 5, lastY - 5,
                        lastX, lastY);
            }
        }

        line.getPoints().addAll(points);
        line.setStrokeWidth(2.0);
        getChildren().add(line);
    }

    public void setStroke(Paint color) {
        line.setStroke(color);
        head.setStroke(color);
    }

    public void setStyleClass(String styleClass) {
        line.getStyleClass().clear();
        line.getStyleClass().add(styleClass + "-line");

        head.getStyleClass().clear();
        head.getStyleClass().add(styleClass + "-shape");
    }
}
