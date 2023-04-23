package com.example.flowdemo.view.editor;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class ZoomableScrollPane extends ScrollPane {

    // https://stackoverflow.com/questions/39827911/javafx-8-scaling-zooming-scrollpane-relative-to-mouse-position

    private double scaleValue = 0.7;
    private double zoomIntensity = 0.0008;
    private Node content;
    private Group zoomGroup;
    private BorderPane contentNode;

    public ZoomableScrollPane(Node content) {
        super();

        this.content = content;
        this.zoomGroup = new Group(content);

        contentNode = new BorderPane(zoomGroup);
        contentNode.setId("grid");
        contentNode.setOnScroll(e -> {
            if (e.isControlDown()) {
                e.consume();
                onScroll(e.getDeltaY(), new Point2D(e.getX(),e.getY()));
            }
        });
        setContent(contentNode);

        setPannable(true);
        setFitToHeight(true);
        setFitToWidth(true);
        updateScale();
    }

    private void updateScale() {
        content.setScaleX(scaleValue);
        content.setScaleY(scaleValue);
    }

    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomGroup.getLayoutBounds();
        // Create a deep copy of viewPortBounds
        Bounds viewPortBounds = new BoundingBox(
                this.getViewportBounds().getMinX(),
                this.getViewportBounds().getMinY(),
                this.getViewportBounds().getWidth(),
                this.getViewportBounds().getHeight());

        // Calculate pixel offset in range 0-1
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewPortBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewPortBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout();

        // Convert content coordinates to zoomGroup's coordinates
        Point2D posInZoomTarget = content.parentToLocal(zoomGroup.parentToLocal(mousePoint));

        // Calculate adjustment of scroll position (pixels)
        Point2D adjustment = content.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // Convert back to 0-1 range (Values outside range are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomGroup.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth()) - viewPortBounds.getWidth());
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight()) - viewPortBounds.getHeight());
    }
}
