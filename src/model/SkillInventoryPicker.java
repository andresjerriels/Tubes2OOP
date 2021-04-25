package model;

import game.Engimon.Engimon;
import game.Skill.SkillItem;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SkillInventoryPicker extends VBox {
    private int index;

    private ImageView circleImage;

    private String circleNotChosen = "view/resources/grey_circle.png";
    private String circleChosen = "view/resources/yellow_boxTick.png";

    private SkillInventoryItem skillitem;
    private boolean isCircleChosen;

    public SkillInventoryPicker(SkillItem s, int i) {
        index = i;
        circleImage = new ImageView(circleNotChosen);
        this.skillitem = new SkillInventoryItem(s);
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(5);
        circleImage.setFitWidth(20);
        circleImage.setFitHeight(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(skillitem);

    }

    public SkillItem getSkill() {
        return skillitem.getSkill();
    }

    public boolean getIsCircleChosen() {
        return isCircleChosen;
    }

    public int getIndex() {
        return index;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
