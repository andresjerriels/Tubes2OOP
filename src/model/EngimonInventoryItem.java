package model;

import game.Engimon.Engimon;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EngimonInventoryItem extends Pane {
    private Engimon engimon;

    public EngimonInventoryItem(Engimon e) {
        engimon = e;
        setPrefWidth(110);
        setPrefHeight(110);

        ImageView engi = e.getSprite();
        engi.setLayoutX(0);
        engi.setLayoutY(0);

        this.getChildren().add(engi);

    }
}
