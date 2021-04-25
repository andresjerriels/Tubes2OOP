package model;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class EngimonGridPane extends GridPane {
    public EngimonGridPane() {
    }

    public void replaceMapWithEngimon(final int row, final int column, ImageView other) {
        ObservableList<Node> children = getChildren();
        other.setFitHeight(64);
        other.setFitWidth(64);

        for (Node node : children) {
            if (getRowIndex(node) == row && getColumnIndex(node) == column) {
                getChildren().remove(node);
                add(other, column, row);
                break;
            }
        }

    }
}
