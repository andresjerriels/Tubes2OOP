package view;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private static final String PLAYER_UP = "view/resources/character/up1.png";
    private static final String PLAYER_DOWN ="view/resources/character/down1.png";
    private static final String PLAYER_RIGHT = "view/resources/character/right1.png";
    private static final String PLAYER_LEFT = "view/resources/character/left1.png";

    private static final String MOUNTAIN_TILE = "view/resources/tiles/mountain.png";
    private static final String TUNDRA_TILE = "view/resources/tiles/tundra.png";
    private static final String GRASSLAND_TILE = "view/resources/tiles/grassland.png";
    private static final String SEA_TILE = "view/resources/tiles/sea.png";

    private static final int MENU_BUTTONS_START_X = 100;
    private static final int MENU_BUTTONS_START_Y = 150;
    List<EngimonGameButton> menuButtons;

    private GameMenuSubScene subSceneToHide;

    private GameMenuSubScene interactSubScene;
    private GameMenuSubScene inventorySubScene;
    private GameMenuSubScene breedSubScene;
    private GameMenuSubScene learnSkillSubScene;
    private GameMenuSubScene saveSubScene;


    private static final int GAME_WIDTH = 1024;
    private static final int GAME_HEIGHT = 768;

    private Stage menuStage;
    private ImageView player;
    private EngimonButton menuButton;

    private EngimonGridPane gridPane1;
    private EngimonGridPane gridPane2;

    private ArrayList<ImageView> wildEngimons;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isUpKeyPressed;
    private boolean isDownKeyPressed;
    private AnimationTimer gameTimer;

    public GameViewManager() {
        menuButtons = new ArrayList<>();
        initializeStage();
        createKeyListeners();
    }

    private void createSubScenes() {
        interactSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(interactSubScene);

        inventorySubScene = new GameMenuSubScene();
        gamePane.getChildren().add(inventorySubScene);

        breedSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(breedSubScene);

        learnSkillSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(learnSkillSubScene);

        saveSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(saveSubScene);
    }

    private void showSubScene(GameMenuSubScene subScene) {
        if (subSceneToHide != null) {
            subSceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        subSceneToHide = subScene;
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.D) {
                    isRightKeyPressed = true;
                } else if (event.getCode() == KeyCode.W) {
                    isUpKeyPressed = true;
                } else if (event.getCode() == KeyCode.S) {
                    isDownKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.A) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.D) {
                    isRightKeyPressed = false;
                } else if (event.getCode() == KeyCode.W) {
                    isUpKeyPressed = false;
                } else if (event.getCode() == KeyCode.S) {
                    isDownKeyPressed = false;
                }
            }
        });
    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createWildEngimons();
        createGameElements();
        createPlayer();
        createGameLoop();
        createSubScenes();
        gameStage.show();
    }

    private void createGameElements() {
        menuButton = new EngimonButton(150, 49,"MENU");
        menuButton.setLayoutX(850);
        menuButton.setLayoutY(25);

        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createMenuButtons();
            }
        });

        gamePane.getChildren().add(menuButton);
    }

    private void addMenuButton(EngimonGameButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        gamePane.getChildren().add(button);
    }

    private void createMenuButtons() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        if (menuButtons.isEmpty()) {
            gridPane2.setEffect(colorAdjust);
            createInteractButton();
            createInventoryButton();
            createBreedButton();
            createLearnSkillButton();
            createSaveButton();
            createExitButton();
        } else {
            gridPane2.setEffect(null);
            closeMenuButtons();
        }
    }

    private void closeMenuButtons() {
        for (EngimonGameButton button : menuButtons) {
            button.setLayoutX(-676);
        }

        menuButtons = new ArrayList<>();
    }

    private void createInteractButton() {
        EngimonGameButton startButton = new EngimonGameButton("INTERACT");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(interactSubScene);
            }
        });
    }

    private void createInventoryButton() {
        EngimonGameButton inventoryButton = new EngimonGameButton("INVENTORY");
        addMenuButton(inventoryButton);

        inventoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(inventorySubScene);
            }
        });
    }

    private void createBreedButton() {
        EngimonGameButton breedButton = new EngimonGameButton("BREED");
        addMenuButton(breedButton);

        breedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(breedSubScene);
            }
        });
    }

    private void createLearnSkillButton() {
        EngimonGameButton learnSkillButton = new EngimonGameButton("LEARN A SKILL");
        addMenuButton(learnSkillButton);

        learnSkillButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(learnSkillSubScene);
            }
        });
    }

    private void createSaveButton() {
        EngimonGameButton saveButton = new EngimonGameButton("SAVE");
        addMenuButton(saveButton);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(saveSubScene);
            }
        });
    }

    private void createExitButton() {
        EngimonGameButton exitButton = new EngimonGameButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameStage.hide();
                menuStage.show();
            }
        });
    }

    private void createWildEngimons() {
        wildEngimons = new ArrayList<>();
        ImageView engimon = new ImageView("view/resources/engimons/tortomon.gif");
        gridPane2.replaceMapWithEngimon(4, 7,engimon);
    }

    private void createPlayer() {
        player = new ImageView(PLAYER_UP);
        player.setLayoutX(GAME_WIDTH / 2);
        player.setLayoutY(GAME_HEIGHT / 2);
        gamePane.getChildren().add(player);
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                movePlayer();
            }
        };

        gameTimer.start();
    }


    private void movePlayer() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            player.setImage(new Image(PLAYER_LEFT));
            if (player.getLayoutX() > 0) {
                player.setLayoutX(player.getLayoutX() - 10);
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed) {
            player.setImage(new Image(PLAYER_RIGHT));
            if (player.getLayoutX() < 994) {
                player.setLayoutX(player.getLayoutX() + 10);
            }
        }

        if(isUpKeyPressed && !isLeftKeyPressed && !isRightKeyPressed && !isDownKeyPressed) {
            player.setImage(new Image(PLAYER_UP));
            if (player.getLayoutY() > 0) {
                player.setLayoutY(player.getLayoutY() - 10);
            }
        }

        if (isDownKeyPressed && !isLeftKeyPressed && !isRightKeyPressed && !isUpKeyPressed) {
            player.setImage(new Image(PLAYER_DOWN));
            if (player.getLayoutY() < 728) {
                player.setLayoutY(player.getLayoutY() + 10);
            }
        }
    }

    private void createBackground() {
        gridPane1 = new EngimonGridPane();
        gridPane2 = new EngimonGridPane();

        int x = 6;
        for (int j = 0; j <= 5; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgSea = new ImageView(SEA_TILE);
                GridPane.setConstraints(bgSea, i, j);
                gridPane1.getChildren().add(bgSea);
                gridPane2.getChildren().add(bgSea);
            }
            x--;
        }

        x = 1;
        for (int j = 6; j <= 11; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                gridPane1.getChildren().add(bgTundra);
                gridPane2.getChildren().add(bgTundra);
            }
            x++;
        }

        x = 7;
        for (int j = 0; j <= 5; j++) {
            if (x == 1) {
                break;
            } else {
                for (int i = x; i < 16-x; i++) {
                    ImageView bgGrassland = new ImageView(GRASSLAND_TILE);
                    GridPane.setConstraints(bgGrassland, i, j);
                    gridPane1.getChildren().add(bgGrassland);
                    gridPane2.getChildren().add(bgGrassland);
                }
                x--;
            }
        }

        x = 2;
        for (int j = 6; j <= 11; j++) {
            if (x == 8) {
                break;
            } else {
                for (int i = x; i < 16-x; i++) {
                    ImageView bgGrassland = new ImageView(GRASSLAND_TILE);
                    GridPane.setConstraints(bgGrassland, i, j);
                    gridPane1.getChildren().add(bgGrassland);
                    gridPane2.getChildren().add(bgGrassland);
                }
                x++;
            }
        }

        x = 7;
        for (int j = 0; j <= 5; j++) {
            for (int i = 16-x; i <= 16; i++) {
                ImageView bgSea = new ImageView(SEA_TILE);
                GridPane.setConstraints(bgSea, i, j);
                gridPane1.getChildren().add(bgSea);
                gridPane2.getChildren().add(bgSea);
            }
            x--;
        }

        x = 2;
        for (int j = 6; j <= 11; j++) {
            for (int i = 16-x; i <= 16; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                gridPane1.getChildren().add(bgTundra);
                gridPane2.getChildren().add(bgTundra);
            }
            x++;
        }

        x = 7;
        int y = 8;
        for (int j = 2; j <= 5; j++) {
            for (int i = x; i <= y; i++) {
                ImageView bgMountain = new ImageView(MOUNTAIN_TILE);
                GridPane.setConstraints(bgMountain, i, j);
                gridPane1.getChildren().add(bgMountain);
                gridPane2.getChildren().add(bgMountain);
            }
            x--; y++;
        }

        x = 4;
        y = 11;
        for (int j = 6; j <= 9; j++) {
            for (int i = x; i <= y; i++) {
                ImageView bgMountain = new ImageView(MOUNTAIN_TILE);
                GridPane.setConstraints(bgMountain, i, j);
                gridPane1.getChildren().add(bgMountain);
                gridPane2.getChildren().add(bgMountain);
            }
            x++; y--;
        }

        gamePane.getChildren().add(gridPane1);
        gamePane.getChildren().add(gridPane2);
    }
}
