package view;

import java.util.ArrayList;
import java.util.List;

import game.Engimon.Engimon;
import game.Engimon.EngimonFactory;
import game.Save.ResourceManager;
import game.Save.SaveData;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import model.EngimonButton;
import model.EngimonPicker;
import model.InfoLabel;
import model.MainMenuSubScene;

public class ViewManager {

    private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;
    private AnchorPane mainPane;
    private ImageView backgroundImageView;
    private ImageView backgroundImageView2;
    private Pane backgroundLayer;
    private Scene mainScene;
    private Stage mainStage;
    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 250;

    private ParallelTransition parallelTransition;

    private MainMenuSubScene subSceneToHide;

    private MainMenuSubScene engimonStarterChooserSubScene;
    private MainMenuSubScene loadSubScene;
    private MainMenuSubScene creditsSubScene;
    private MainMenuSubScene helpSubScene;

    List<EngimonButton> menuButtons;
    List<EngimonPicker> engimonStartersList;
    private Engimon chosenEngimon;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene (mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);

        mainStage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                parallelTransition.play();
                if (subSceneToHide != null) {
                    subSceneToHide.moveSubScene();
                    subSceneToHide = null;
                }
            }
        });

        mainStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                parallelTransition.stop();
            }
        });

        createBackground();
        createSubScenes();
        createButtons();
        createLogo();
        initAnimation();
        parallelTransition.play();
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
        try {
            engimonStartersList.add(new EngimonPicker(EngimonFactory.createEngimon("Charmamon", 0)));
            engimonStartersList.add(new EngimonPicker(EngimonFactory.createEngimon("Pikamon", 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Default
        engimonStartersList.get(0).setIsCircleChosen(true);
        chosenEngimon = engimonStartersList.get(0).getEngimon();

        for (EngimonPicker engimonPicker : engimonStartersList) {
            box.getChildren().add(engimonPicker);
            engimonPicker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (EngimonPicker engimonPicker : engimonStartersList) {
                        engimonPicker.setIsCircleChosen(false);
                    }
                    engimonPicker.setIsCircleChosen(true);
                    chosenEngimon = engimonPicker.getEngimon();
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
                try {
                    parallelTransition.pause();
                    gameManager.createNewGame(mainStage, chosenEngimon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                try {
                    SaveData data = (SaveData) ResourceManager.load("saveData.engi");
                    GameViewManager gameManager = new GameViewManager();
                    parallelTransition.stop();
                    gameManager.loadGame(mainStage, data);
                } catch (Exception e) {
                    System.out.println("Tidak dapat melakukan load: "+e.getMessage());
                }
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
        backgroundImageView = new ImageView("view/resources/bg.jpg");
        backgroundImageView.setFitHeight(768);
        backgroundLayer = new Pane();
        backgroundLayer.getChildren().add(backgroundImageView);

        backgroundImageView2 = new ImageView("view/resources/bg.jpg");
        backgroundImageView2.setFitHeight(768);
        backgroundImageView2.setX(1920);
        backgroundLayer.getChildren().add(backgroundImageView2);
        
        mainPane.getChildren().add(backgroundLayer);
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
    
    private void initAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(150000), backgroundLayer.getChildren().get(0));
        translateTransition.setFromX(0);
        translateTransition.setToX(-1 * 1920);
        translateTransition.setInterpolator(Interpolator.LINEAR);
        
        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(150000),  backgroundLayer.getChildren().get(1));
        translateTransition2.setFromX(0);
        translateTransition2.setToX(-1 * 1920);
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        parallelTransition = new ParallelTransition( translateTransition, translateTransition2 );
        parallelTransition.setCycleCount(Animation.INDEFINITE);
              
       }
      
}
