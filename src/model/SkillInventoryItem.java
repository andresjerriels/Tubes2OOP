package model;

import game.Engimon.Element;
import game.Skill.SkillItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SkillInventoryItem extends StackPane {
    private SkillItem skillitem;

    public SkillInventoryItem(SkillItem s) {
        skillitem = s;
        setPrefWidth(110);
        setPrefHeight(110);

        // ImageView skillImg = s.getSprite();
        // skillImg.setLayoutX(0);
        // skillImg.setLayoutY(0);
        // skillImg.setFitWidth(110);
        // skillImg.setFitHeight(110);
        // this.getChildren().add(skillImg);

        Text name = new Text(s.getSkill().getName());
        StackPane.setAlignment(name, Pos.BOTTOM_CENTER);
        this.getChildren().add(name);

        Text amount = new Text(Integer.toString(s.getItemAmount()));
        StackPane.setAlignment(amount, Pos.TOP_RIGHT);
        StackPane.setMargin(amount, new Insets(5, 15, 0, 0));
        this.getChildren().add(amount);

        VBox elements = new VBox();

        for (String element : s.getSkill().getSkillElements()) {
            ImageView logo = new ImageView(Element.fromName(element).getImageUrl());
            logo.setFitHeight(25);
            logo.setFitWidth(25);
            elements.getChildren().add(logo);
        }
        StackPane.setAlignment(elements, Pos.TOP_LEFT);
        this.getChildren().add(elements);
        
        Text bp = new Text(Integer.toString(s.getSkill().getBasePower()));
        StackPane.setMargin(bp, new Insets(5, 0, 0, 0));
        StackPane.setAlignment(bp, Pos.TOP_CENTER);
        this.getChildren().add(bp);        
    }

    public SkillItem getSkill() {
        return skillitem;
    }
}
