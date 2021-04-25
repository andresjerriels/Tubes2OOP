package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.Engimon.Engimon;
import game.Engimon.EngimonFactory;
import game.Map.Peta;
import game.Map.Position;
import game.Map.Tile;
import game.Player.Player;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.EngimonButton;
import model.EngimonGameButton;
import model.EngimonGridPane;
import model.EngimonInventoryItem;
import model.GameMenuSubScene;
import model.InfoLabel;

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
    private Peta map;


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

    private GameMenuSubScene engimonsSubScene;
    private GameMenuSubScene skillsSubScene;
    private GameMenuSubScene breedSubScene;
    private GameMenuSubScene learnSkillSubScene;
    private GameMenuSubScene saveSubScene;
    private GameMenuSubScene infoSubScene;

    private InfoLabel infoLabel;

    private static final int GAME_WIDTH = 1024;
    private static final int GAME_HEIGHT = 768;

    private Stage menuStage;
    private EngimonButton menuButton;

    private EngimonGridPane gridPane1;
    private EngimonGridPane gridPane2;

    private ArrayList<Node> removedTiles;

    private AnimationTimer gameTimer;

    public GameViewManager() {
        menuButtons = new ArrayList<>();
        initializeStage();
        createKeyListeners();
    }

    private void createSubScenes() throws Exception {
        createEngimonSubscene();

        skillsSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(skillsSubScene);

        breedSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(breedSubScene);

        learnSkillSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(learnSkillSubScene);

        saveSubScene = new GameMenuSubScene();
        gamePane.getChildren().add(saveSubScene);
        
        createInfoSubscene();
    }

    private void createEngimonSubscene() throws Exception {
        engimonsSubScene = new GameMenuSubScene();

        ScrollPane engiScroll = new ScrollPane();
        engiScroll.setPrefHeight(550);
        engiScroll.setPrefWidth(550);
        engiScroll.setLayoutX(25);
        engiScroll.setLayoutY(25);
        engiScroll.setStyle("-fx-background-color: transparent");

        GridPane engiGrid = new GridPane();
        // engiGrid.setPrefHeight(550);
        // engiGrid.setPrefHeight(550);
        engiGrid.setHgap(0);
        engiGrid.setVgap(0);
        
        // for (int i = 0; i < 5; i++) {
        //     engiGrid.getColumnConstraints().add(new ColumnConstraints(100));
        // }
        // for (int i = 0; i < 5; i++) {
        //     engiGrid.getRowConstraints().add(new RowConstraints(100));
        // }
        
        EngimonInventoryItem e = new EngimonInventoryItem(player.getActiveEngimon());

        // GridPane.setConstraints(e, 0, 0);
        // engiGrid.getChildren().add(e);

        engiGrid.add(new EngimonInventoryItem(player.getActiveEngimon()), 0, 0);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 1);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 2);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 3);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 4);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 5);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 0, 6);
        engiGrid.add(new EngimonInventoryItem(EngimonFactory.createEngimon(0)), 1, 0);

        engiScroll.setContent(engiGrid);
        engiGrid.setLayoutX(0);
        engiGrid.setLayoutY(0);

        engimonsSubScene.getPane().getChildren().add(engiScroll);

        gamePane.getChildren().add(engimonsSubScene);
    }

    private void createInfoSubscene() {
        infoSubScene = new GameMenuSubScene();
        infoSubScene.setSize(600, 150);
        infoSubScene.setLayoutX(GAME_WIDTH/5);
        infoSubScene.setLayoutY(GAME_HEIGHT/5);
        infoLabel = new InfoLabel("");
        infoLabel.setFontSize(12);
        infoLabel.setLayoutX(25);
        infoLabel.setLayoutY(25);
        infoSubScene.getPane().getChildren().add(infoLabel);

        EngimonButton backButton = new EngimonButton("BACK");
        backButton.setLayoutX(200);
        backButton.setLayoutY(85);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                infoSubScene.setVisible(false);
            }
        });
        infoSubScene.getPane().getChildren().add(backButton);

        gamePane.getChildren().add(infoSubScene);
        infoSubScene.setVisible(false);
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

    public void createNewGame(Stage menuStage, Engimon chosenEngimon) throws Exception {
        this.menuStage = menuStage;
        this.menuStage.hide();
        player = new Player(chosenEngimon);
        player.setActiveEngimon(0);
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
        menuButton.setOpacity(0.3);

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
            createEngimonsButton();
            createSkillsButton();
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
        if (subSceneToHide != null) {
            subSceneToHide.moveSubScene();
            subSceneToHide = null;
        }

        menuButtons = new ArrayList<>();
    }

    private void createEngimonsButton() {
        EngimonGameButton engimonsButton = new EngimonGameButton("ENGIMONS");
        addMenuButton(engimonsButton);

        engimonsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(engimonsSubScene);
            }
        });
    }

    private void createSkillsButton() {
        EngimonGameButton skillsButton = new EngimonGameButton("SKILLS");
        addMenuButton(skillsButton);

        skillsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(skillsSubScene);
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

    private void createWildEngimons() throws Exception {

        for (int i = 0; i < map.getLength(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Tile curTile = map.getTile(i, j);
                if (curTile.containWildEngimon()) {
                    Engimon eng = curTile.getWildEngimon();
                    ImageView engimon = eng.getSprite();
                    gridPane2.replaceMapWithImage(j, i, engimon);
                }
            }
        }

        map.PrintPeta();
    }

    private void placeBackTiles() {
        for (Node node : removedTiles) {
            gridPane2.replaceMapWithNode(node);
        }

        // removedTiles.add(gridPane2.replaceMapWithEngimon(4, 0,new ImageView(charmamon.getImgUrl())));
        removedTiles = new ArrayList<>();
    }

    private void createPlayer() {
        // player = new Player("Martin", 0);
        playerPos = map.getPlayerPosition();
        gridPane2.replaceMapWithImage(playerPos.getX(),playerPos.getY(), new ImageView(player.getImgUrl()));
        if (player.getActiveEngimon() != null) {
            gridPane2.replaceMapWithImage(map.getActiveEngiPosition().getX(), map.getActiveEngiPosition().getY(), player.getActiveEngimon().getSprite());
        }
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    processKeypress();
                } catch (Exception e) {
                    refreshMap();
                    e.printStackTrace(System.err);
                    infoLabel.setText(e.getMessage());
                    infoSubScene.setVisible(true);
                }
            }
        };

        gameTimer.start();
    }


    private void processKeypress() throws Exception {
        if (removeActiveKey("A")) {
            player.setImgUrl(PLAYER_LEFT);
            map.move("a");
            refreshMap();
//            if (player.getLayoutX() > 0) {
//                player.setLayoutX(player.getLayoutX() - 10);
//            }
        }

        if (removeActiveKey("D")) {
            player.setImgUrl(PLAYER_RIGHT);
            map.move("d");
            refreshMap();
//            if (player.getLayoutX() < 994) {
//                player.setLayoutX(player.getLayoutX() + 10);
//            }
        }

        if(removeActiveKey("W")) {
            player.setImgUrl(PLAYER_UP);
            map.move("w");
            refreshMap();
//            if (player.getLayoutY() > 0) {
//                player.setLayoutY(player.getLayoutY() - 10);
//            }
        }

        if (removeActiveKey("S")) {
            player.setImgUrl(PLAYER_DOWN);
            map.move("s");
            refreshMap();
//            if (player.getLayoutY() < 728) {
//                player.setLayoutY(player.getLayoutY() + 10);
//            }
        }

        if (removeActiveKey("I")) {
            if (infoSubScene.isVisible()) {
                infoSubScene.setVisible(false);
            } else {
                if (player.getActiveEngimon() != null) {
                    infoLabel.setText(player.getActiveEngimon().interact());
                } else {
                    System.out.println("You don't have an active engimon right now");
                    infoLabel.setText("You don't have an active engimon right now");
                }
                infoSubScene.setVisible(true);
            }
        }
        
    }


    private void refreshMap() {
        gridPane2.getChildren().clear();

        
        for (int j = 0; j <= map.getLength(); j++) {
            for (int i = 0; i <= map.getWidth(); i++) {
                ImageView block = new ImageView(generateEmptyImage(64,64));
                GridPane.setConstraints(block, i, j);
                gridPane2.getChildren().add(block);
            }
        }
        createPlayer();
        try {
            createWildEngimons();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createBackground() {
        map = new Peta("./src/game/files/map.txt", player);
        gridPane1 = new EngimonGridPane();

        for (int i = 0; i < map.getLength(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Tile curTile = map.getTile(i, j);
                switch (curTile.getType()) {
                    case Mountain:
                        ImageView bgMountain = new ImageView(MOUNTAIN_TILE);
                        GridPane.setConstraints(bgMountain, j, i);
                        gridPane1.getChildren().add(bgMountain);
                        break;
                    case Sea:
                        ImageView bgSea = new ImageView(SEA_TILE);
                        GridPane.setConstraints(bgSea, j, i);
                        gridPane1.getChildren().add(bgSea);
                        break;
                    case Grassland:
                        ImageView bgGrassland = new ImageView(GRASSLAND_TILE);
                        GridPane.setConstraints(bgGrassland, j, i);
                        gridPane1.getChildren().add(bgGrassland);
                        break;
                    case Tundra:
                        ImageView bgTundra = new ImageView(TUNDRA_TILE);
                        GridPane.setConstraints(bgTundra, j, i);
                        gridPane1.getChildren().add(bgTundra);
                        break;
                    default:
                        break;
                }
            }
        }

        gamePane.getChildren().add(gridPane1);
    }

    private Image generateEmptyImage(int width, int height) {
        return new WritableImage(width, height);
    }

    private void createMap() {
        // gridPane1 = new EngimonGridPane();
        gridPane2 = new EngimonGridPane();
        for (int j = 0; j <= map.getLength(); j++) {
            for (int i = 0; i <= map.getWidth(); i++) {
                ImageView block = new ImageView(generateEmptyImage(64,64));
                GridPane.setConstraints(block, i, j);
                gridPane2.getChildren().add(block);
            }
        }
        gamePane.getChildren().add(gridPane2);
    }
}
