package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import game.Engimon.Element;
import game.Engimon.Engimon;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class SkillDetailSubScene extends GameMenuSubScene {
    public EngimonGameButton backButton;

    public SkillDetailSubScene() {
        super();

        this.toFront();
        setSize(300, 340);
        setLayoutX(300);
        setLayoutY(170);
    }

    public void setSkill(SkillInventoryItem e){
        this.getPane().getChildren().clear();
        addButton();

        ImageView skillPicture = new ImageView(e.skillPict.getImage());
        skillPicture.setLayoutY(40);
        skillPicture.setLayoutX(120);
        skillPicture.setFitHeight(60);
        skillPicture.setFitWidth(60);
        StackPane.setAlignment(skillPicture, Pos.CENTER);
        this.getPane().getChildren().add(skillPicture);

        VBox skillBox = new VBox();
        Label skillname = new Label (e.getSkill().getSkill().getName() + "\n");
        Label bp = new Label        ("BP : " + Integer.toString(e.getSkill().getSkill().getBasePower()) + "\n" );
        Label elements = new Label  ("Elements : \n");
        HBox elmtBox = new HBox();

        try {
            skillname.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 17));
            bp.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 17));
            elements.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 17));
        } catch (FileNotFoundException err) {
            skillname.setFont(Font.font("Verdana", 17));
            bp.setFont(Font.font("Verdana", 17));
            elements.setFont(Font.font("Verdana", 17));
        }

        for (String element : e.getSkill().getSkill().getSkillElements()) {
            ImageView logo = new ImageView(Element.fromName(element).getImageUrl());
            logo.setFitHeight(35);
            logo.setFitWidth(35);
            elmtBox.getChildren().add(logo);
        }


        skillBox.getChildren().add(skillname);
        skillBox.getChildren().add(bp);
        skillBox.getChildren().add(elements);
        skillBox.getChildren().add(elmtBox);
        skillBox.setLayoutX(50);
        skillBox.setLayoutY(120);

        this.getPane().getChildren().add(skillBox);
    }

    private void close(){
        this.setVisible(false);
    }
    private void addButton(){

        backButton = new EngimonGameButton("BACK");
        backButton.setLayoutX(55);
        backButton.setLayoutY(240);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close();
            }
        });
        this.getPane().getChildren().add(backButton);
    }

}
