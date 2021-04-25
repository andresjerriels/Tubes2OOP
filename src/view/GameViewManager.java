package view;

import Engimon.Charmamon;
import Engimon.Engimon;
import Map.Position;
import Map.Tile;
import Player.Player;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Engimon.EngimonFactory.createEngimon;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();

    // PETA VARIABLES
    private static final int MAP_HEIGHT = 12;
    private static final int MAP_WIDTH = 16;
    private ArrayList<ArrayList<Tile>> matriksPeta;
    private int nWiildEngimon;
    private static int maxWildEngimon = 15;
    private int lvlCapslock;
    private Player player;
    private Position playerPos;


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
    private EngimonButton menuButton;

    private EngimonGridPane gridPane1;
    private EngimonGridPane gridPane2;

    private ArrayList<Node> removedTiles;
    private Node removedTileforPlayer;

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
                String codeString = event.getCode().toString();
                if (!currentlyActiveKeys.containsKey(codeString)) {
                    currentlyActiveKeys.put(codeString, true);
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                currentlyActiveKeys.remove(event.getCode().toString());
            }
        });
    }

    private boolean removeActiveKey(String codeString) {
        Boolean isActive = currentlyActiveKeys.get(codeString);

        if (isActive != null && isActive) {
            currentlyActiveKeys.put(codeString, false);
            return true;
        } else {
            return false;
        }
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
        createMap();
        createPlayer();
        createGameLoop();
        createWildEngimons();
        createGameElements();
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
            gridPane1.setEffect(colorAdjust);
            createInteractButton();
            createInventoryButton();
            createBreedButton();
            createLearnSkillButton();
            createSaveButton();
            createExitButton();
        } else {
            gridPane1.setEffect(null);
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
        removedTiles = new ArrayList<>();

        Engimon wild = null;
        try {
            wild = createEngimon("wild", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView engimon = new ImageView(wild.getImgUrl());
        removedTiles.add(gridPane2.replaceMapWithEngimon(4, 8,engimon));
    }

    private void placeBackTiles() {
        for (Node node : removedTiles) {
            gridPane2.replaceMapWithNode(node);
        }

        // removedTiles.add(gridPane2.replaceMapWithEngimon(4, 0,new ImageView(charmamon.getImgUrl())));
        removedTiles = new ArrayList<>();
    }

    private void createPlayer() {
        player = new Player("Martin", 0);
        playerPos = new Position(6, 8);
        removedTileforPlayer = gridPane2.replaceMapWithEngimon(playerPos.getX(),playerPos.getY(), new ImageView(player.getImgUrl()));
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

    private boolean isPlayerOutOfRange(){
        return playerPos.getX() < 0 || playerPos.getX() >= MAP_WIDTH || playerPos.getY() < 0 || playerPos.getY() >= MAP_HEIGHT;
    }


    private void movePlayer() {
        if (removeActiveKey("A")) {
            player.setImgUrl(PLAYER_LEFT);
            playerPos.setXY("a");
            if (isPlayerOutOfRange()) {
                playerPos.resetXY("a");
            }
            gridPane2.replaceMapWithNode(removedTileforPlayer);
            removedTileforPlayer = gridPane2.replaceMapWithEngimon(playerPos.getX(), playerPos.getY(), new ImageView(player.getImgUrl()));
//            if (player.getLayoutX() > 0) {
//                player.setLayoutX(player.getLayoutX() - 10);
//            }
        }

        if (removeActiveKey("D")) {
            player.setImgUrl(PLAYER_RIGHT);
            playerPos.setXY("d");
            if (isPlayerOutOfRange()) {
                playerPos.resetXY("d");
            }
            gridPane2.replaceMapWithNode(removedTileforPlayer);
            removedTileforPlayer = gridPane2.replaceMapWithEngimon(playerPos.getX(), playerPos.getY(), new ImageView(player.getImgUrl()));
//            if (player.getLayoutX() < 994) {
//                player.setLayoutX(player.getLayoutX() + 10);
//            }
        }

        if(removeActiveKey("W")) {
            player.setImgUrl(PLAYER_UP);
            playerPos.setXY("w");
            if (isPlayerOutOfRange()) {
                playerPos.resetXY("w");
            }
            gridPane2.replaceMapWithNode(removedTileforPlayer);
            removedTileforPlayer = gridPane2.replaceMapWithEngimon(playerPos.getX(), playerPos.getY(), new ImageView(player.getImgUrl()));
//            if (player.getLayoutY() > 0) {
//                player.setLayoutY(player.getLayoutY() - 10);
//            }
        }

        if (removeActiveKey("S")) {
            player.setImgUrl(PLAYER_DOWN);
            playerPos.setXY("s");
            if (isPlayerOutOfRange()) {
                playerPos.resetXY("s");
            }
            gridPane2.replaceMapWithNode(removedTileforPlayer);
            removedTileforPlayer = gridPane2.replaceMapWithEngimon(playerPos.getX(), playerPos.getY(), new ImageView(player.getImgUrl()));
//            if (player.getLayoutY() < 728) {
//                player.setLayoutY(player.getLayoutY() + 10);
//            }
        }
    }

    private void createBackground() {
        gridPane1 = new EngimonGridPane();

        int x = 6;
        for (int j = 0; j <= 5; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgSea = new ImageView(SEA_TILE);
                GridPane.setConstraints(bgSea, i, j);
                gridPane1.getChildren().add(bgSea);
            }
            x--;
        }

        x = 1;
        for (int j = 6; j <= 11; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                gridPane1.getChildren().add(bgTundra);
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
            }
            x--;
        }

        x = 2;
        for (int j = 6; j <= 11; j++) {
            for (int i = 16-x; i <= 16; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                gridPane1.getChildren().add(bgTundra);
                // gridPane2.getChildren().add(bgTundra);
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
            }
            x++; y--;
        }

        gamePane.getChildren().add(gridPane1);
    }

    private Image generateEmptyImage(int width, int height) {
        return new WritableImage(width, height);
    }

    private void createMap() {
        // gridPane1 = new EngimonGridPane();
        gridPane2 = new EngimonGridPane();
        for (int j = 0; j <= 11; j++) {
            for (int i = 0; i <= 17; i++) {
                ImageView block = new ImageView(generateEmptyImage(64,64));
                GridPane.setConstraints(block, i, j);
                gridPane2.getChildren().add(block);
            }
        }
/*
        int x = 6;
        for (int j = 0; j <= 5; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgSea = new ImageView(SEA_TILE);
                GridPane.setConstraints(bgSea, i, j);
                // gridPane1.getChildren().add(bgSea);
                gridPane2.getChildren().add(bgSea);
            }
            x--;
        }

        x = 1;
        for (int j = 6; j <= 11; j++) {
            for (int i = 0; i <= x; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                // gridPane1.getChildren().add(bgTundra);
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
                    // gridPane1.getChildren().add(bgGrassland);
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
                    // gridPane1.getChildren().add(bgGrassland);
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
                // gridPane1.getChildren().add(bgSea);
                gridPane2.getChildren().add(bgSea);
            }
            x--;
        }

        x = 2;
        for (int j = 6; j <= 11; j++) {
            for (int i = 16-x; i <= 16; i++) {
                ImageView bgTundra = new ImageView(TUNDRA_TILE);
                GridPane.setConstraints(bgTundra, i, j);
                // gridPane1.getChildren().add(bgTundra);
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
                // gridPane1.getChildren().add(bgMountain);
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
                // gridPane1.getChildren().add(bgMountain);
                gridPane2.getChildren().add(bgMountain);
            }
            x++; y--;
        }
*/
        // gamePane.getChildren().add(gridPane1);
        gamePane.getChildren().add(gridPane2);
    }
}
