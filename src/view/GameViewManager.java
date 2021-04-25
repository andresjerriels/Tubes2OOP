package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import game.Engimon.Engimon;
import game.Engimon.EngimonFactory;
import game.Map.Peta;
import game.Map.Position;
import game.Map.Tile;
import game.Player.InvalidIndexInventory;
import game.Player.Player;
import game.Save.ResourceManager;
import game.Save.SaveData;
import game.Skill.SkillItem;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.EngiDetailSubscene;
import model.EngimonInventoryPicker;
import model.EngimonButton;
import model.EngimonGameButton;
import model.EngimonGridPane;
import model.EngimonInventoryItem;
import model.GameMenuSubScene;
import model.InfoLabel;
import model.SkillInventoryItem;
import model.SkillInventoryPicker;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private HashMap<String, Boolean> currentlyActiveKeys = new HashMap<>();

    // PETA VARIABLES
    private Player player;
    private Position playerPos;
    private Peta map;

    private Engimon parentA;
    private Engimon parentB;

    private SkillItem skillToLearn;
    private int skillToLearnIdx;
    private Engimon engiToLearn;

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
    private GameMenuSubScene infoSubScene;
    private EngiDetailSubscene engiDetailSubscene;

    // private GridPane engiGrid;

    private InfoLabel infoLabel;

    private static final int GAME_WIDTH = 1024;
    private static final int GAME_HEIGHT = 768;

    private Stage menuStage;
    private EngimonButton menuButton;

    private EngimonGridPane gridPane1;
    private EngimonGridPane gridPane2;

    private AnimationTimer gameTimer;

    public GameViewManager() {
        menuButtons = new ArrayList<>();
        initializeStage();
        createKeyListeners();
    }

    public void createNewGame(Stage menuStage, Engimon chosenEngimon) throws Exception {
        this.menuStage = menuStage;
        this.menuStage.hide();
        player = new Player(chosenEngimon);
        map = new Peta("./src/game/files/map.txt", player);
        player.setActiveEngimon(0);
        // player.addToInvSkill(new SkillItem(1, "Storm Hammer"));
        // player.addToInvSkill(new SkillItem(3, "Ice Spike"));
        // player.addToInvSkill(new SkillItem(2, "Mud Storm"));
        // player.addToInvSkill(new SkillItem(2, "Rock Throw"));
        player.addToInvSkill(new SkillItem(1, "Mud Storm"));
        // player.addToInvSkill(new SkillItem(2, "Surf Wave"));
        // player.addToInvSkill(new SkillItem(2, "Hydro Cannon"));
        // player.addToInvSkill(new SkillItem(1, "Flame Punch"));
        Engimon e = EngimonFactory.createEngimon("3", "Dittimon");
        e.setLevel(10);
        player.addToInvEngimon(e);
        Engimon e2 = EngimonFactory.createEngimon("4", "Dittimon");
        e2.setLevel(10);
        player.addToInvEngimon(e2);
        player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
        player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
        player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
        player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
        player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
        createBackground();
        createMap();
        createPlayer();
        createGameLoop();
        createWildEngimons();
        createGameElements();
        createSubScenes();
        gameStage.setResizable(false);
        gameStage.show();
    }

    public void loadGame(Stage menuStage, SaveData saveData) throws Exception {
        this.menuStage = menuStage;
        this.menuStage.hide();
        player = saveData.player;
        map = saveData.map;
        createBackground();
        createMap();
        createPlayer();
        createGameLoop();
        createWildEngimons();
        createGameElements();
        createSubScenes();
        gameStage.setResizable(false);
        gameStage.show();
    }

    private void createSubScenes() throws Exception {
        createEngimonsSubscene();

        createSkillsSubscene();

        createBreedSubscene();

        createLearnSubscene();

        engiDetailSubscene = new EngiDetailSubscene();
        gamePane.getChildren().add(engiDetailSubscene);
        engiDetailSubscene.setVisible(false);
        
        createInfoSubscene();
    }

    private void createLearnSubscene() throws InvalidIndexInventory {
        learnSkillSubScene = new GameMenuSubScene();

        refreshLearnSubScene();

        gamePane.getChildren().add(learnSkillSubScene);
    }

    private void refreshLearnSubScene() throws InvalidIndexInventory {
        learnSkillSubScene.getPane().getChildren().clear();

        InfoLabel EngimonLearnLabel = new InfoLabel("Engimon");
        EngimonLearnLabel.setFontSize(20);
        EngimonLearnLabel.setLayoutX(25);
        EngimonLearnLabel.setLayoutY(25);

        learnSkillSubScene.getPane().getChildren().add(EngimonLearnLabel);

        ScrollPane EngimonLearnScroll = new ScrollPane();
        EngimonLearnScroll.setPrefHeight(150);
        EngimonLearnScroll.setPrefWidth(550);
        EngimonLearnScroll.setLayoutX(25);
        EngimonLearnScroll.setLayoutY(85);
        EngimonLearnScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox EngimonLearnHBox = new HBox();
        
        for (int i = 0; i < player.getInventoryEngimon().countItemInInventory(); i++) {
            EngimonInventoryPicker e = new EngimonInventoryPicker(player.getEngiRefFromIndex(i), i);
            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : EngimonLearnHBox.getChildren()) {
                        EngimonInventoryPicker engimonPicker = (EngimonInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    e.setIsCircleChosen(true);
                    engiToLearn = e.getEngimon();
                }
            });

            EngimonLearnHBox.getChildren().add(e);
        }

        // Default
        ((EngimonInventoryPicker) EngimonLearnHBox.getChildren().get(0)).setIsCircleChosen(true);
        engiToLearn = ((EngimonInventoryPicker) EngimonLearnHBox.getChildren().get(0)).getEngimon();

        EngimonLearnScroll.setContent(EngimonLearnHBox);
        // // engiGrid.setLayoutX(0);
        // // engiGrid.setLayoutY(0);

        learnSkillSubScene.getPane().getChildren().add(EngimonLearnScroll);

        InfoLabel skillItemLabel = new InfoLabel("SKILL");
        skillItemLabel.setFontSize(20);
        skillItemLabel.setLayoutX(25);
        skillItemLabel.setLayoutY(250);

        learnSkillSubScene.getPane().getChildren().add(skillItemLabel);

        ScrollPane skillItemScroll = new ScrollPane();
        skillItemScroll.setPrefHeight(150);
        skillItemScroll.setPrefWidth(550);
        skillItemScroll.setLayoutX(25);
        skillItemScroll.setLayoutY(310);
        skillItemScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox skillItemHBox = new HBox();
        
        for (int i = 0; i < player.getInventorySkill().countItemInInventory(); i++) {

            SkillInventoryPicker e = new SkillInventoryPicker(player.getSkillRefFromIndex(i), i);
            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : skillItemHBox.getChildren()) {
                        SkillInventoryPicker engimonPicker = (SkillInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    e.setIsCircleChosen(true);
                    skillToLearn = e.getSkill();
                    skillToLearnIdx = e.getIndex();
                }
            });

            skillItemHBox.getChildren().add(e);
        }

        // Default
        if (player.getInventorySkill().countItemInInventory() > 0) {
            ((SkillInventoryPicker) skillItemHBox.getChildren().get(0)).setIsCircleChosen(true);
            skillToLearn = ((SkillInventoryPicker) skillItemHBox.getChildren().get(0)).getSkill();
        }

        skillItemScroll.setContent(skillItemHBox);
        // engiGrid.setLayoutX(0);
        // engiGrid.setLayoutY(0);

        learnSkillSubScene.getPane().getChildren().add(skillItemScroll);

        EngimonGameButton executeLearnButton = new EngimonGameButton("LEARN");
        executeLearnButton.setLayoutX(205);
        executeLearnButton.setLayoutY(500);

        executeLearnButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if (skillToLearn.learn(engiToLearn) == 0) {
                        player.removeSkillByIndex(skillToLearnIdx);
                    }
                    refreshLearnSubScene();
                    refreshSkillsSubScene();
                    if (player.getInventorySkill().countItemInInventory() == 0) {
                        learnSkillSubScene.moveSubScene();
                        subSceneToHide = null;
                    }
                } catch (Exception e) {
                    showInfo(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        learnSkillSubScene.getPane().getChildren().add(executeLearnButton);
    }

    private void createBreedSubscene() throws InvalidIndexInventory {
        breedSubScene = new GameMenuSubScene();

        refreshBreedSubScene();

        gamePane.getChildren().add(breedSubScene);
    }

    private void refreshBreedSubScene() throws InvalidIndexInventory {
        breedSubScene.getPane().getChildren().clear();

        InfoLabel parentALabel = new InfoLabel("Parent A");
        parentALabel.setFontSize(20);
        parentALabel.setLayoutX(25);
        parentALabel.setLayoutY(25);

        breedSubScene.getPane().getChildren().add(parentALabel);

        ScrollPane parentAScroll = new ScrollPane();
        parentAScroll.setPrefHeight(150);
        parentAScroll.setPrefWidth(550);
        parentAScroll.setLayoutX(25);
        parentAScroll.setLayoutY(85);
        parentAScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox parentAHBox = new HBox();
        
        for (int i = 0; i < player.getInventoryEngimon().countItemInInventory(); i++) {

            EngimonInventoryPicker e = new EngimonInventoryPicker(player.getEngiRefFromIndex(i), i);
            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : parentAHBox.getChildren()) {
                        EngimonInventoryPicker engimonPicker = (EngimonInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    e.setIsCircleChosen(true);
                    parentA = e.getEngimon();
                }
            });

            parentAHBox.getChildren().add(e);
        }

        // Default
        ((EngimonInventoryPicker) parentAHBox.getChildren().get(0)).setIsCircleChosen(true);
        parentA = ((EngimonInventoryPicker) parentAHBox.getChildren().get(0)).getEngimon();

        parentAScroll.setContent(parentAHBox);
        // // engiGrid.setLayoutX(0);
        // // engiGrid.setLayoutY(0);

        breedSubScene.getPane().getChildren().add(parentAScroll);

        InfoLabel parentBLabel = new InfoLabel("Parent B");
        parentBLabel.setFontSize(20);
        parentBLabel.setLayoutX(25);
        parentBLabel.setLayoutY(250);

        breedSubScene.getPane().getChildren().add(parentBLabel);

        ScrollPane parentBScroll = new ScrollPane();
        parentBScroll.setPrefHeight(150);
        parentBScroll.setPrefWidth(550);
        parentBScroll.setLayoutX(25);
        parentBScroll.setLayoutY(310);
        parentBScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        HBox parentBHBox = new HBox();
        
        for (int i = 0; i < player.getInventoryEngimon().countItemInInventory(); i++) {

            EngimonInventoryPicker e = new EngimonInventoryPicker(player.getEngiRefFromIndex(i), i);
            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : parentBHBox.getChildren()) {
                        EngimonInventoryPicker engimonPicker = (EngimonInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    e.setIsCircleChosen(true);
                    parentB = e.getEngimon();
                }
            });

            parentBHBox.getChildren().add(e);
        }

        // Default
        ((EngimonInventoryPicker) parentBHBox.getChildren().get(0)).setIsCircleChosen(true);
        parentB = ((EngimonInventoryPicker) parentBHBox.getChildren().get(0)).getEngimon();

        parentBScroll.setContent(parentBHBox);
        // engiGrid.setLayoutX(0);
        // engiGrid.setLayoutY(0);

        breedSubScene.getPane().getChildren().add(parentBScroll);

        EngimonGameButton executeBreedButton = new EngimonGameButton("BREED");
        executeBreedButton.setLayoutX(205);
        executeBreedButton.setLayoutY(500);

        executeBreedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    player.addToInvEngimon(parentA.breed(parentB));
                    refreshBreedSubScene();
                    refreshEngiInventory();
                } catch (Exception e) {
                    showInfo(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        breedSubScene.getPane().getChildren().add(executeBreedButton);
    }

    private void createSkillsSubscene() throws InvalidIndexInventory {
        skillsSubScene = new GameMenuSubScene();

        refreshSkillsSubScene();

        gamePane.getChildren().add(skillsSubScene);
    }

    private void refreshSkillsSubScene() throws InvalidIndexInventory {
        skillsSubScene.getPane().getChildren().clear();

        ScrollPane skillsScroll = new ScrollPane();
        skillsScroll.setPrefHeight(550);
        skillsScroll.setPrefWidth(550);
        skillsScroll.setLayoutX(25);
        skillsScroll.setLayoutY(25);
        skillsScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        GridPane skillsGrid = new GridPane();
        
        for (int i = 0; i < player.getInventorySkill().countItemInInventory(); i++) {
            SkillInventoryItem e = new SkillInventoryItem(player.getInventorySkill().getContainer().get(i));

            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    e.getSkill().printDetails();
                }
            });

            skillsGrid.add(e, i % 5, i / 5);
        }

        skillsScroll.setContent(skillsGrid);
        // engiGrid.setLayoutX(0);
        // engiGrid.setLayoutY(0);

        skillsSubScene.getPane().getChildren().add(skillsScroll);
    }

    private void createEngimonsSubscene() throws InvalidIndexInventory {
        engimonsSubScene = new GameMenuSubScene();

        refreshEngiInventory();

        gamePane.getChildren().add(engimonsSubScene);
    }

    private void refreshEngiInventory() throws InvalidIndexInventory {
        engimonsSubScene.getPane().getChildren().clear();

        ScrollPane engiScroll = new ScrollPane();
        engiScroll.setPrefHeight(550);
        engiScroll.setPrefWidth(550);
        engiScroll.setLayoutX(25);
        engiScroll.setLayoutY(25);
        engiScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        GridPane engiGrid = new GridPane();
        
        for (int i = 0; i < player.getInventoryEngimon().countItemInInventory(); i++) {
            EngimonInventoryItem e = new EngimonInventoryItem(player.getEngiRefFromIndex(i), player.getEngiRefFromIndex(i) == player.getActiveEngimon(), i);

            e.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openEngiDetails(e.getEngimon(), e.getIndex());
                }
            });

            engiGrid.add(e, i % 5, i / 5);
        }

        engiScroll.setContent(engiGrid);
        // engiGrid.setLayoutX(0);
        // engiGrid.setLayoutY(0);

        engimonsSubScene.getPane().getChildren().add(engiScroll);
    }

    private void openEngiDetails(Engimon e, int idx) {
        engiDetailSubscene.setEngimon(e);

        try {
            if (player.getEngiRefFromIndex(idx) == player.getActiveEngimon()) {
                engiDetailSubscene.setActiveButton.setDisable(true);
            } else {
                engiDetailSubscene.setActiveButton.setDisable(false);
            }
        } catch (InvalidIndexInventory e1) {
            e1.printStackTrace();
        }
        engiDetailSubscene.setActiveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.setActiveEngimon(idx);
                engiDetailSubscene.setActiveButton.setDisable(true);
                refreshMap();
                try {
                    refreshEngiInventory();
                } catch (InvalidIndexInventory e) {
                    e.printStackTrace();
                }
            }
        });

        engiDetailSubscene.renameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.renameEngimon(idx, engiDetailSubscene.renameField.getText());
                engiDetailSubscene.renameField.setText("");
                engiDetailSubscene.setEngimon(engiDetailSubscene.getEngimon());
                try {
                    refreshEngiInventory();
                } catch (InvalidIndexInventory e) {
                    e.printStackTrace();
                }
            }
        });

        engiDetailSubscene.releaseButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (player.getInventoryEngimon().countItemInInventory() > 1) {
                    try {
                        if (player.getEngiRefFromIndex(idx) == player.getActiveEngimon()) {
                            player.setActiveEngimonNull();
                            refreshMap();
                        }
                        player.removeEngimonByIndex(idx);
                        refreshEngiInventory();
                    } catch (InvalidIndexInventory e) {
                        e.printStackTrace();
                    }
                    engiDetailSubscene.setVisible(false);
                } else {
                    showInfo("You only have 1 engimon");
                }
            }
        });

        engiDetailSubscene.setVisible(true);
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

    private void createGameElements() {
        menuButton = new EngimonButton(150, 49,"MENU");
        menuButton.setLayoutX(850);
        menuButton.setLayoutY(25);
        menuButton.setOpacity(0.3);

        createEngimonsButton();
        createSkillsButton();
        createBreedButton();
        createLearnSkillButton();
        createSaveButton();
        createExitButton();
        menuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                toggleMenuButtons();
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

    private void toggleMenuButtons() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(-0.5);
        if (!menuButtons.get(0).isVisible()) {
            gridPane1.setEffect(colorAdjust);
        } else {
            gridPane1.setEffect(null);
        }

        for (EngimonGameButton button : menuButtons) {
            button.setVisible(!button.isVisible());
        }
        if (subSceneToHide != null) {
            subSceneToHide.moveSubScene();
            subSceneToHide = null;
        }
    }

    private void createEngimonsButton() {
        EngimonGameButton engimonsButton = new EngimonGameButton("ENGIMONS");
        engimonsButton.setVisible(false);
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
        skillsButton.setVisible(false);
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
        breedButton.setVisible(false);
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
        learnSkillButton.setVisible(false);
        addMenuButton(learnSkillButton);

        learnSkillButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (player.getInventorySkill().countItemInInventory() > 0) {
                    showSubScene(learnSkillSubScene);
                } else {
                    showInfo("You do not have any skill items");
                }
            }
        });
    }

    private void createSaveButton() {
        EngimonGameButton saveButton = new EngimonGameButton("SAVE");
        saveButton.setVisible(false);
        addMenuButton(saveButton);

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SaveData data = new SaveData();
                data.map = map;
                data.player = player;
                try {
                    ResourceManager.save(data, "saveData.engi");
                    System.out.println("Game saved to saveData.engi");
                    showInfo("Game saved to saveData.engi");
                } catch (Exception e) {
                    System.out.println("Tidak dapat melakukan save: "+ e.getMessage());
                }
            }
        });
    }

    private void createExitButton() {
        EngimonGameButton exitButton = new EngimonGameButton("EXIT");
        exitButton.setVisible(false);
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
                    showInfo(e.getMessage());
                }
            }
        };

        gameTimer.start();
    }

    private void showInfo(String info) {
        infoLabel.setText(info);
        infoSubScene.setVisible(true);
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
