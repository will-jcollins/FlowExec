package com.example.flowdemo.view.editor.menu;

import com.example.flowdemo.model.flow.expression.*;
import com.example.flowdemo.model.flow.nodes.*;
import com.example.flowdemo.view.editor.cell.*;
import com.example.flowdemo.view.editor.expr.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class NewItem extends VBox {

    private ItemType type;
    private ImageView imageView = new ImageView();

    public NewItem(ItemType type) {
        super();
        this.type = type;

        setAlignment(Pos.CENTER);

//        UICell cellSnapshot = null;
//        UIExpr exprSnapshot = null;
//
//        imageView.setPreserveRatio(true);
//        getChildren().add(imageView);
//
//        // Convert enumeration to new model object
//        switch (type) {
//            case For:
//                cellSnapshot = new UIFor(UICell.IRRELEVANT_ID);
//                break;
//            case While:
//                cellSnapshot = new UIWhile(UICell.IRRELEVANT_ID);
//                break;
//            case If:
//                cellSnapshot = new UIIf(UICell.IRRELEVANT_ID);
//                break;
//            case Output:
//                cellSnapshot = new UIOutput(UICell.IRRELEVANT_ID);
//                break;
//            case Assign:
//                cellSnapshot = new UIAssign(UICell.IRRELEVANT_ID);
//                break;
//            case DeclareAssign:
//                cellSnapshot = new UIDeclareAssign(UICell.IRRELEVANT_ID);
//                break;
//            case Call:
//                cellSnapshot = new UICall(UICell.IRRELEVANT_ID);
//                break;
//            case Return:
//                cellSnapshot = new UIReturn(UICell.IRRELEVANT_ID);
//                break;
//            case BoolLit:
//                exprSnapshot = new UIBool(UIExpr.IRRELEVANT_ID);
//                break;
//            case CharLit:
//                exprSnapshot = new UIChar(UIExpr.IRRELEVANT_ID);
//                break;
//            case IntLit:
//                exprSnapshot = new UIInt(UIExpr.IRRELEVANT_ID);
//                break;
//            case OpExpr:
//                exprSnapshot = new UIOpExpr(UIExpr.IRRELEVANT_ID);
//                break;
//            case VarExpr:
//                exprSnapshot = new UIVar(UIExpr.IRRELEVANT_ID);
//                break;
//            case ModifierExpr:
//                exprSnapshot = new UIModifierExpr(UIExpr.IRRELEVANT_ID);
//                break;
//            case CallExpr:
//                exprSnapshot = new UICallExpr(UIExpr.IRRELEVANT_ID);
//                break;
//            default:
//                break;
//        }
//
//        SnapshotParameters params = new SnapshotParameters();
//        params.setFill(Color.TRANSPARENT);
//
//        if (cellSnapshot != null) {
//            cellSnapshot.setStyleClass("default");
//            cellSnapshot.applyCss();
//            cellSnapshot.layout();
//            cellSnapshot.updateLayout();
//            getChildren().add(cellSnapshot);
//            UICell finalCellSnapshot = cellSnapshot;
//            Platform.runLater(() -> {
////                imageView.setImage(finalCellSnapshot.snapshot(params, null));
//                getChildren().remove(finalCellSnapshot);
//            });
//        } else if (exprSnapshot != null) {
//            exprSnapshot.layout();
//            getChildren().add(exprSnapshot);
//            UIExpr finalExprSnapshot = exprSnapshot;
//            Platform.runLater(() -> {
////                imageView.setImage(finalExprSnapshot.snapshot(params, null));
//                getChildren().remove(finalExprSnapshot);
//            });
//        }

        getChildren().add(new Label(type.name()));
    }

    public ItemType getValue() {
        return type;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
