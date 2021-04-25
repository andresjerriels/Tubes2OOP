package model;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class EngimonStarterPicker extends VBox {

    private ImageView circleImage;
    private ImageView engimonImage;

    private String circleNotChosen = "view/resources/grey_circle.png";
    private String circleChosen = "view/resources/yellow_boxTick.png";

    private ENGIMON engimon;
    private boolean isCircleChosen;

    public EngimonStarterPicker(ENGIMON engimon) {
        circleImage = new ImageView(circleNotChosen);
        engimonImage = new ImageView(engimon.urlImg);
        this.engimon = engimon;
        isCircleChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(engimonImage);

    }

    public ENGIMON getEngimon() {
        return engimon;
    }

    public boolean getIsCircleChosen() {
        return isCircleChosen;
    }

    public void setIsCircleChosen(boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
        circleImage.setImage(new Image(imageToSet));
    }
}
