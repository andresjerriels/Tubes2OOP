package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 250;

    private MainMenuSubScene subSceneToHide;

    private MainMenuSubScene engimonStarterChooserSubScene;
    private MainMenuSubScene loadSubScene;
    private MainMenuSubScene creditsSubScene;
    private MainMenuSubScene helpSubScene;

    List<EngimonButton> menuButtons;
    List<EngimonStarterPicker> engimonStartersList;
    private ENGIMON chosenEngimon;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene (mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();
    }

    private void createSubScenes() {
        loadSubScene = new MainMenuSubScene();
        mainPane.getChildren().add(loadSubScene);

        creditsSubScene = new MainMenuSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new MainMenuSubScene();
        mainPane.getChildren().add(helpSubScene);

        createEngimonStarterChooserSubScene();
    }

    private void createEngimonStarterChooserSubScene() {
        engimonStarterChooserSubScene = new MainMenuSubScene();
        mainPane.getChildren().add(engimonStarterChooserSubScene);

        InfoLabel chooseEngimonStarterLabel = new InfoLabel("CHOOSE YOUR STARTING ENGIMON");
        chooseEngimonStarterLabel.setLayoutX(25);
        chooseEngimonStarterLabel.setLayoutY(25);
        engimonStarterChooserSubScene.getPane().getChildren().add(chooseEngimonStarterLabel);
        engimonStarterChooserSubScene.getPane().getChildren().add(createEngimonsToChoose());
        engimonStarterChooserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private HBox createEngimonsToChoose() {
        HBox box = new HBox();
        box.setSpacing(20);
        engimonStartersList = new ArrayList<>();
        for (ENGIMON engimon : ENGIMON.values()) {
            EngimonStarterPicker engimonToPick = new EngimonStarterPicker(engimon);
            engimonStartersList.add(engimonToPick);
            box.getChildren().add(engimonToPick);
            engimonToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (EngimonStarterPicker engimon : engimonStartersList) {
                        engimon.setIsCircleChosen(false);
                    }
                    engimonToPick.setIsCircleChosen(true);
                    chosenEngimon = engimonToPick.getEngimon();
                }
            });
        }
        box.setLayoutX(300 - (118 * 2));
        box.setLayoutY(100);
        return box;
    }

    private EngimonButton createButtonToStart() {
        EngimonButton startButton = new EngimonButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(mainStage);
            }
        });

        return startButton;
    }

    private void showSubScene(MainMenuSubScene subScene) {
        if (subSceneToHide != null) {
            subSceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        subSceneToHide = subScene;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(EngimonButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createLoadButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
        EngimonButton startButton = new EngimonButton("START");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(engimonStarterChooserSubScene);
            }
        });
    }

    private void createLoadButton() {
        EngimonButton loadButton = new EngimonButton("LOAD");
        addMenuButton(loadButton);

        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(loadSubScene);
            }
        });
    }

    private void createHelpButton() {
        EngimonButton helpButton = new EngimonButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        EngimonButton creditsButton = new EngimonButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });
    }

    private void createExitButton() {
        EngimonButton exitButton = new EngimonButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("view/resources/bg.jpg", 1024, 768, false, true);
        BackgroundImage bg = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        mainPane.setBackground(new Background(bg));
    }

    private void createLogo() {
        ImageView logo = new ImageView("view/resources/engimon_logo.png");
        logo.setLayoutX(450);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }
}