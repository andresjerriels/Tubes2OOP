package model;

import game.Engimon.Element;
import game.Engimon.Engimon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
        engi.setFitWidth(110);
        engi.setFitHeight(110);

        Text name = new Text(e.getName());
        Text lvl = new Text(Integer.toString(e.getLevel()));
        StackPane.setMargin(lvl, new Insets(5, 15, 0, 0));

        VBox elements = new VBox();

        for (Element element : e.getElements()) {
            ImageView logo = new ImageView(element.getImageUrl());
            logo.setFitHeight(25);
            logo.setFitWidth(25);
            elements.getChildren().add(logo);
        }

        if (active) {
            ImageView activeLogo = new ImageView("view/resources/active.png");
            activeLogo.setFitHeight(25);
            activeLogo.setFitWidth(25);
            this.getChildren().add(activeLogo);
            StackPane.setAlignment(activeLogo, Pos.TOP_CENTER);
        }
        
        this.getChildren().add(engi);
        this.getChildren().add(name);
        this.getChildren().add(lvl);
        this.getChildren().add(elements);
        StackPane.setAlignment(name, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(lvl, Pos.TOP_RIGHT);
        StackPane.setAlignment(elements, Pos.TOP_LEFT);
    }

    public int getIndex() {
        return index;
    }

    public Engimon getEngimon() {
        return engimon;
    }
}
