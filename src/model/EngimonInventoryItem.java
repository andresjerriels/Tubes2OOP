package model;

import game.Engimon.Element;
import game.Engimon.Engimon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class EngimonInventoryItem extends StackPane {
    private Engimon engimon;
    private int index;

    public EngimonInventoryItem(Engimon e, boolean active, int idx) {
        index = idx;
        engimon = e;
        setPrefWidth(110);
        setPrefHeight(110);

        ImageView engi = e.getSprite();
        engi.setLayoutX(0);
        engi.setLayoutY(0);
        engi.setFitWidth(100);
        engi.setFitHeight(100);
        this.getChildren().add(engi);

        Text name = new Text(e.getName());
        StackPane.setMargin(name, new Insets(0, 0, 2, 0));
        StackPane.setAlignment(name, Pos.BOTTOM_CENTER);
        this.getChildren().add(name);

        Text lvl = new Text(Integer.toString(e.getLevel()));
        StackPane.setMargin(lvl, new Insets(5, 15, 0, 0));
        StackPane.setAlignment(lvl, Pos.TOP_RIGHT);
        this.getChildren().add(lvl);

        VBox elements = new VBox();

        for (Element element : e.getElements()) {
            ImageView logo = new ImageView(element.getImageUrl());
            logo.setFitHeight(25);
            logo.setFitWidth(25);
            elements.getChildren().add(logo);
        }
        StackPane.setAlignment(elements, Pos.TOP_LEFT);
        this.getChildren().add(elements);

        if (active) {
            ImageView activeLogo = new ImageView("view/resources/active.png");
            activeLogo.setFitHeight(25);
            activeLogo.setFitWidth(25);
            this.getChildren().add(activeLogo);
            StackPane.setAlignment(activeLogo, Pos.TOP_CENTER);
        }
        
    }

    public int getIndex() {
        return index;
    }

    public Engimon getEngimon() {
        return engimon;
    }
}
