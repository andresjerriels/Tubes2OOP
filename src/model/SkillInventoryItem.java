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
    private int index;
    public ImageView throwButton;
    public ImageView skillPict;

    public SkillInventoryItem(SkillItem s, int idx, boolean removable) {
        skillitem = s;
        index = idx;
        setPrefWidth(110);
        setPrefHeight(110);

        Text name = new Text(s.getSkill().getName());
        StackPane.setMargin(name, new Insets(0, 0, 5, 0));
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
        
        if (removable) {
            throwButton = new ImageView("/view/resources/delete-circle.png");
            throwButton.setFitWidth(25);
            throwButton.setFitHeight(25);
            StackPane.setAlignment(throwButton, Pos.CENTER_RIGHT);
            StackPane.setMargin(throwButton, new Insets(45, 0, 0, 0));
            this.getChildren().add(throwButton);
        }

        skillPict = new ImageView(s.getSkill().getSprite().getImage());
        skillPict.setFitWidth(50);
        skillPict.setFitHeight(50);
        StackPane.setAlignment(skillPict, Pos.CENTER);
        StackPane.setMargin(skillPict, new Insets(0, 0, 0, 0));
        this.getChildren().add(skillPict);
    }

    public SkillItem getSkill() {
        return skillitem;
    }

    public int getIndex() {
        return index;
    }
}
