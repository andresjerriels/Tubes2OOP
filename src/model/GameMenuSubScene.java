package model;

import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameMenuSubScene extends SubScene {

    private static final String FONT_PATH = "src/model/resources/kenvector_future.ttf";
    private static final String BACKGROUND_IMAGE = "model/resources/yellow_panel.png";

    private boolean isHidden;

    public GameMenuSubScene() {
        super(new AnchorPane(), 600, 600);
        prefWidth(600);
        prefHeight(600);

        BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 600, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(new Background(image));

        isHidden = true;
        setLayoutX(1024);
        setLayoutY(150);
    }

    public void moveSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if (isHidden) {
            transition.setToX(-676);
            isHidden = false;
        } else {
            transition.setToX(0);
            isHidden = true;
        }


        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
