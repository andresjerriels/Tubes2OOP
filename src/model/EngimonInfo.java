package model;

import game.Engimon.Element;
import game.Engimon.Engimon;
import game.Skill.Skill;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class EngimonInfo extends VBox {

    private final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private ImageView engimonImage;
    ArrayList<Text> engimonInfo;
    private Engimon engimon;

    public EngimonInfo(Engimon playerEngimon, Engimon engimon) {
        engimonImage = engimon.getSprite();
        this.engimon = engimon;

        engimonInfo = new ArrayList<>();
        engimonInfo.add(new Text(engimon.getName()));
        engimonInfo.add(new Text(engimon.getSpecies()));
        engimonInfo.add(new Text("Element(s)"));
        engimonInfo.add(new Text("Level: " + engimon.getLevel()));
        engimonInfo.add(new Text("Lives: " + engimon.getLives()));
        engimonInfo.add(new Text("Skills"));
        engimonInfo.add(new Text("Power Level: " + engimon.getPowerLevel(playerEngimon)));

        for (int i = 0; i < engimonInfo.size(); i++) {
            try {
                engimonInfo.get(i).setFont(Font.loadFont(new FileInputStream(FONT_PATH), 14));
            } catch (FileNotFoundException e) {
                engimonInfo.get(i).setFont(Font.font("Verdana", 14));
            }
        }


        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().add(engimonImage);
        this.getChildren().add(engimonInfo.get(6));
        this.getChildren().add(engimonInfo.get(0));
        this.getChildren().add(engimonInfo.get(1));
        this.getChildren().add(engimonInfo.get(2));

        ArrayList<Element> elements = engimon.getElements();
        HBox elementsBox = new HBox();
        for (int i = 0; i < elements.size(); i++) {
            ImageView icon = new ImageView("view/resources/elements/" + elements.get(i).getName().toLowerCase() + ".png");
            icon.setFitHeight(20);
            icon.setFitWidth(20);
            elementsBox.getChildren().add(icon);
        }
        elementsBox.setSpacing(10);
        elementsBox.setAlignment(Pos.CENTER);
        this.getChildren().add(elementsBox);

        this.getChildren().add(engimonInfo.get(3));
        this.getChildren().add(engimonInfo.get(4));
        this.getChildren().add(engimonInfo.get(5));

        ArrayList<Skill> engimonSkills = engimon.getSkills();

        for (int i = 0; i < engimonSkills.size(); i++) {
            HBox box = new HBox();
            String name = engimonSkills.get(i).getName().replaceAll("\\s+","");
            ImageView masteryIcon = new ImageView("view/resources/skill/lv" + engimonSkills.get(i).getMastery() + ".png");
            masteryIcon.setFitWidth(20);
            masteryIcon.setFitHeight(20);
            ImageView icon = new ImageView("view/resources/skill/" + name.toLowerCase() + ".png");
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            Text nameText = new Text(engimonSkills.get(i).getName());
            Text basePower = new Text(String.valueOf(engimonSkills.get(i).getBasePower()));

            try {
                nameText.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 14));
                basePower.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 14));
            } catch (FileNotFoundException e) {
                nameText.setFont(Font.font("Verdana", 14));
            }

            box.getChildren().addAll(masteryIcon, icon, basePower, nameText);
            box.setSpacing(10);
            this.getChildren().add(box);
        }

    }

    public Engimon getEngimon() {
        return engimon;
    }

}
