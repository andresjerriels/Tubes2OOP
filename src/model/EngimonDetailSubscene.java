package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import game.Engimon.Element;
import game.Engimon.Engimon;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class EngimonDetailSubscene extends GameMenuSubScene {
    private Engimon engimon;
    public EngimonGameButton renameButton;
    public EngimonGameButton releaseButton;
    public EngimonGameButton setActiveButton;
    public EngimonGameButton backButton;
    public TextField renameField;
    private List<EngimonGameButton> buttons;

    public EngimonDetailSubscene() {
        super();

        // setAlwaysOnTop(true);
        this.toFront();

        setSize(900, 650);

        setLayoutX(62);
        setLayoutY(59);
        
        renameField = new TextField();
        renameField.setPromptText("Enter new name...");
        renameField.setMinWidth(190);
        renameField.prefHeight(25);
        renameField.setLayoutX(663);
        renameField.setLayoutY(291);

        renameButton = new EngimonGameButton("RENAME");
        renameButton.setLayoutX(663);
        renameButton.setLayoutY(336);
        // this.getPane().getChildren().add(renameButton);

        setActiveButton = new EngimonGameButton("SET ACTIVE");
        setActiveButton.setLayoutX(663);
        setActiveButton.setLayoutY(411);
        // this.getPane().getChildren().add(setActiveButton);

        releaseButton = new EngimonGameButton("RELEASE");
        releaseButton.setLayoutX(663);
        releaseButton.setLayoutY(486);
        // this.getPane().getChildren().add(releaseButton);

        backButton = new EngimonGameButton("BACK");
        backButton.setLayoutX(663);
        backButton.setLayoutY(561);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                close();
            }
        });
        // this.getPane().getChildren().add(backButton);

        buttons = new ArrayList<EngimonGameButton>();
        buttons.add(setActiveButton);
        buttons.add(renameButton);
        buttons.add(releaseButton);
        buttons.add(backButton);
    }

    private void close() {
        this.setVisible(false);
    }

    public void setEngimon(Engimon e) {
        engimon = e;

        this.getPane().getChildren().clear();

        ImageView engiImage = e.getSprite();
        engiImage.setFitHeight(200);
        engiImage.setFitWidth(200);
        engiImage.setLayoutX(32);
        engiImage.setLayoutY(32);
        this.getPane().getChildren().add(engiImage);

        VBox elements = new VBox();

        for (Element element : e.getElements()) {
            ImageView logo = new ImageView(element.getImageUrl());
            logo.setFitHeight(35);
            logo.setFitWidth(35);
            elements.getChildren().add(logo);
        }
        elements.setLayoutX(40);
        elements.setLayoutY(32);
        this.getPane().getChildren().add(elements);

        GridPane skillsGrid = new GridPane();
        skillsGrid.setPrefHeight(200);
        skillsGrid.setPrefWidth(616);
        skillsGrid.setLayoutX(258);
        skillsGrid.setLayoutY(32);
        for (int i = 0; i < e.getSkills().size(); i++) {
            HBox skillBox = new HBox();

            StackPane skillImgPane = new StackPane();
            skillImgPane.setPrefWidth(100);
            skillImgPane.setPrefHeight(100);

            ImageView skillImg = e.getSkills().get(i).getSprite();
            skillImg.setFitWidth(75);
            skillImg.setFitHeight(75);
            StackPane.setMargin(skillImg, new Insets(0, 0, 10, 0));

            skillImgPane.getChildren().add(skillImg);

            VBox detailBox = new VBox();
            detailBox.setPrefHeight(100);
            Label skillname = new Label(e.getSkills().get(i).getName());
            Label bp = new Label("BP: " + Integer.toString(e.getSkills().get(i).getBasePower()));

            ImageView mastery = e.getSkills().get(i).getMasterySprite();
            mastery.setFitHeight(28);
            mastery.setFitWidth(28);
            
            HBox elmtBox = new HBox();

            try {
                skillname.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 14));
                bp.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 14));
            } catch (FileNotFoundException err) {
                skillname.setFont(Font.font("Verdana", 14));
                bp.setFont(Font.font("Verdana", 14));
            }

            for (String element : e.getSkills().get(i).getSkillElements()) {
                ImageView logo = new ImageView(Element.fromName(element).getImageUrl());
                logo.setFitHeight(28);
                logo.setFitWidth(28);
                elmtBox.getChildren().add(logo);
            }

            detailBox.getChildren().add(skillname);
            detailBox.getChildren().add(bp);
            detailBox.getChildren().add(mastery);
            detailBox.getChildren().add(elmtBox);
            
            skillBox.setPrefWidth(308);
            skillBox.setPrefHeight(100);
            skillBox.getChildren().add(skillImgPane);
            skillBox.getChildren().add(detailBox);

            skillsGrid.add(skillBox, i % 2 , i / 2);
        }
        this.getPane().getChildren().add(skillsGrid);

        Label details = new Label(e.printDetails());
        details.prefHeight(377);
        details.prefWidth(582);
        details.setLayoutX(45);
        details.setLayoutY(233);
        try {
            details.setFont(Font.loadFont(new FileInputStream("src/model/resources/kenvector_future.ttf"), 20));
        } catch (FileNotFoundException err) {
            details.setFont(Font.font("Verdana", 23));
        }
        this.getPane().getChildren().add(details);

        for (EngimonGameButton engimonGameButton : buttons) {
            this.getPane().getChildren().add(engimonGameButton);
        }

        this.getPane().getChildren().add(renameField);
    }

    public Engimon getEngimon() {
        return engimon;
    }
}