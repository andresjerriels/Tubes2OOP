package model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class EngimonGridPane extends GridPane {

    public EngimonGridPane() {
    }

    public Node replaceMapWithImage(final int column, final int row, ImageView other) {
        ObservableList<Node> children = getChildren();
        other.setFitHeight(64);
        other.setFitWidth(64);

        for (Node node : children) {
            if (getRowIndex(node) == row && getColumnIndex(node) == column) {
                getChildren().remove(node);
                add(other, column, row);
                return node;
            }
        }
        return null;
    }

    public void replaceMapWithNode(Node other) {
        ObservableList<Node> children = getChildren();
        int row = getRowIndex(other);
        int column = getColumnIndex(other);

        for (Node node : children) {
            if (getRowIndex(node) == row && getColumnIndex(node) == column) {
                getChildren().remove(node);
                add(other, column, row);
                break;
            }
        }

    }

//
//    public Node getNodeFromGridPane(int col, int row) {
//        for (Node node : this.getChildren()) {
//            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
//                return node;
//            }
//        }
//        return null;
//    }
}
