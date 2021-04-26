package view;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import game.Engimon.Engimon;
import game.Engimon.EngimonFactory;
import game.Map.Peta;
import game.Map.Position;
import game.Map.Tile;
import game.Player.InvalidIndexInventory;
import game.Player.Player;
import game.Save.ResourceManager;
import game.Save.SaveData;
import game.Skill.InvalidSkillNameException;
import game.Skill.SkillItem;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

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

    private boolean inMenu;

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
    private EngimonDetailSubscene engiDetailSubscene;
    private SkillDetailSubScene skillDetailSubScene;


    // Engimon inventory variables
    private GridPane engiGrid;
    private HBox parentAHBox;
    private HBox parentBHBox;
    private HBox EngimonLearnHBox;

    // Skill inventory variables
    private GridPane skillsGrid;
    private HBox skillItemHBox;

    // Battle Variables
    private BattleMenuSubScene wildEngimonChooserSubScene;
    private BattleMenuSubScene battleSubScene;
    private List<WildEngimonPicker> wildEngimonList;
    private List<EngimonInfo> battleEngimon;
    private Engimon chosenWildEngimon;
    private ArrayList<Tile> aroundTiles;
    private Tile tileWithEngimon;
    private GameMenuSubScene messageSubScene;
    private InfoLabel messageLabel;
    private Stage battleStage;

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
        inMenu = false;
        player.setActiveEngimon(0);
//         player.addToInvSkill(new SkillItem(1, "Storm Hammer"));
//         player.addToInvSkill(new SkillItem(3, "Ice Spike"));
//         player.addToInvSkill(new SkillItem(2, "Mud Storm"));
//         player.addToInvSkill(new SkillItem(2, "Rock Throw"));
//         player.addToInvSkill(new SkillItem(1, "Mud Storm"));
//         player.addToInvSkill(new SkillItem(2, "Surf Wave"));
//         player.addToInvSkill(new SkillItem(2, "Hydro Cannon"));
//         player.addToInvSkill(new SkillItem(1, "Flame Punch"));
//         Engimon e = EngimonFactory.createEngimon("3", "Dittimon");
//         e.setLevel(10);
//         player.addToInvEngimon(e);
//         Engimon e2 = EngimonFactory.createEngimon("4", "Dittimon");
//         e2.setLevel(10);
//         player.addToInvEngimon(e2);
//         player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
//         player.addToInvEngimon(EngimonFactory.createEngimon("3", "Electromon"));
//         player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
//         player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
//         player.addToInvEngimon(EngimonFactory.createEngimon("3", "Dittimon"));
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
        inMenu = false;
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
        
        refreshEngiInventory();
        refreshSkillInventory();

        skillDetailSubScene = new SkillDetailSubScene();
        gamePane.getChildren().add(skillDetailSubScene);
        skillDetailSubScene.setVisible(false);

        engiDetailSubscene = new EngimonDetailSubscene();
        gamePane.getChildren().add(engiDetailSubscene);
        engiDetailSubscene.setVisible(false);

        createInfoSubscene();
    }

    private void createLearnSubscene() throws InvalidIndexInventory {
        learnSkillSubScene = new GameMenuSubScene();

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

        EngimonLearnHBox = new HBox();

        EngimonLearnScroll.setContent(EngimonLearnHBox);

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

        skillItemHBox = new HBox();

        skillItemScroll.setContent(skillItemHBox);

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
                    refreshSkillInventory();
                    if (engiToLearn == player.getActiveEngimon()) {
                        refreshMap();
                    }
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

        gamePane.getChildren().add(learnSkillSubScene);
    }

    private void createBreedSubscene() throws InvalidIndexInventory {
        breedSubScene = new GameMenuSubScene();

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

        parentAHBox = new HBox();

        parentAScroll.setContent(parentAHBox);

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

        parentBHBox = new HBox();

        parentBScroll.setContent(parentBHBox);

        breedSubScene.getPane().getChildren().add(parentBScroll);

        EngimonGameButton executeBreedButton = new EngimonGameButton("BREED");
        executeBreedButton.setLayoutX(205);
        executeBreedButton.setLayoutY(500);

        executeBreedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    player.addToInvEngimon(parentA.breed(parentB));
                    refreshEngiInventory();
                } catch (Exception e) {
                    showInfo(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        breedSubScene.getPane().getChildren().add(executeBreedButton);

        gamePane.getChildren().add(breedSubScene);
    }

    private void createSkillsSubscene() throws InvalidIndexInventory {
        skillsSubScene = new GameMenuSubScene();

        ScrollPane skillsScroll = new ScrollPane();
        skillsScroll.setPrefHeight(550);
        skillsScroll.setPrefWidth(550);
        skillsScroll.setLayoutX(25);
        skillsScroll.setLayoutY(25);
        skillsScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        skillsGrid = new GridPane();

        skillsScroll.setContent(skillsGrid);

        skillsSubScene.getPane().getChildren().add(skillsScroll);

        gamePane.getChildren().add(skillsSubScene);
    }

    private void createEngimonsSubscene() throws InvalidIndexInventory {
        engimonsSubScene = new GameMenuSubScene();

        ScrollPane engiScroll = new ScrollPane();
        engiScroll.setPrefHeight(550);
        engiScroll.setPrefWidth(550);
        engiScroll.setLayoutX(25);
        engiScroll.setLayoutY(25);
        engiScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        engiGrid = new GridPane();

        engiScroll.setContent(engiGrid);

        engimonsSubScene.getPane().getChildren().add(engiScroll);
        
        gamePane.getChildren().add(engimonsSubScene);
    }

    private void refreshEngiInventory() throws InvalidIndexInventory {
        parentAHBox.getChildren().clear();
        parentBHBox.getChildren().clear();
        engiGrid.getChildren().clear();
        EngimonLearnHBox.getChildren().clear();

        for (int i = 0; i < player.getInventoryEngimon().countItemInInventory(); i++) {
            // Inventory
            EngimonInventoryItem eitem = new EngimonInventoryItem(player.getEngiRefFromIndex(i), player.getEngiRefFromIndex(i) == player.getActiveEngimon(), i);

            eitem.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openEngiDetails(eitem.getEngimon(), eitem.getIndex());
                }
            });

            engiGrid.add(eitem, i % 5, i / 5);

            // Breed
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

            EngimonInventoryPicker e2 = new EngimonInventoryPicker(player.getEngiRefFromIndex(i), i);
            e2.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : parentBHBox.getChildren()) {
                        EngimonInventoryPicker engimonPicker = (EngimonInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    e2.setIsCircleChosen(true);
                    parentB = e2.getEngimon();
                }
            });

            parentBHBox.getChildren().add(e2);

            // Learn
            EngimonInventoryPicker elearn = new EngimonInventoryPicker(player.getEngiRefFromIndex(i), i);
            elearn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : EngimonLearnHBox.getChildren()) {
                        EngimonInventoryPicker engimonPicker = (EngimonInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    elearn.setIsCircleChosen(true);
                    engiToLearn = elearn.getEngimon();
                }
            });

            EngimonLearnHBox.getChildren().add(elearn);
        }

        // Default
        ((EngimonInventoryPicker) EngimonLearnHBox.getChildren().get(0)).setIsCircleChosen(true);
        engiToLearn = ((EngimonInventoryPicker) EngimonLearnHBox.getChildren().get(0)).getEngimon();

        // Default
        ((EngimonInventoryPicker) parentAHBox.getChildren().get(0)).setIsCircleChosen(true);
        parentA = ((EngimonInventoryPicker) parentAHBox.getChildren().get(0)).getEngimon();

        // Default
        ((EngimonInventoryPicker) parentBHBox.getChildren().get(0)).setIsCircleChosen(true);
        parentB = ((EngimonInventoryPicker) parentBHBox.getChildren().get(0)).getEngimon();
    }

    private void refreshSkillInventory() throws InvalidIndexInventory {
        skillsGrid.getChildren().clear();
        skillItemHBox.getChildren().clear();;

        for (int i = 0; i < player.getInventorySkill().countItemInInventory(); i++) {
            // Skill inventory
            SkillInventoryItem e = new SkillInventoryItem(player.getInventorySkill().getContainer().get(i), i, true);

            e.throwButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    List<Integer> choices = new ArrayList<>();
                    for (int j = 1; j <= e.getSkill().getItemAmount(); j++) {
                        choices.add(j);
                    }

                    ChoiceDialog<Integer> dialog = new ChoiceDialog<>(1, choices);
                    dialog.setTitle("Throw Skill Item");
                    dialog.setHeaderText("How many items do you want to throw?");

                    Optional<Integer> result = dialog.showAndWait();
                    if (result.isPresent()){
                        System.out.println("Your choice: " + result.get());
                        try {
                            player.removeNSkill(e.getIndex(), result.get());
                            refreshSkillInventory();
                        } catch (Exception e) {
                            showInfo(e.getMessage());
                            e.printStackTrace();
                        }
                    }

                }
            });

            e.skillPict.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    openSkillDetails(e);
                }
            });

            skillsGrid.add(e, i % 5, i / 5);

            // Learn
            SkillInventoryPicker s = new SkillInventoryPicker(player.getSkillRefFromIndex(i), i);
            s.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (Node n : skillItemHBox.getChildren()) {
                        SkillInventoryPicker engimonPicker = (SkillInventoryPicker) n;
                        engimonPicker.setIsCircleChosen(false);
                    }
                    s.setIsCircleChosen(true);
                    skillToLearn = s.getSkill();
                    skillToLearnIdx = s.getIndex();
                }
            });

            skillItemHBox.getChildren().add(s);
        }

        // Default
        if (player.getInventorySkill().countItemInInventory() > 0) {
            ((SkillInventoryPicker) skillItemHBox.getChildren().get(0)).setIsCircleChosen(true);
            skillToLearn = ((SkillInventoryPicker) skillItemHBox.getChildren().get(0)).getSkill();
        }
    }
    private void openSkillDetails(SkillInventoryItem e) {
        skillDetailSubScene.setSkill(e);
        skillDetailSubScene.setVisible(true);
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

        if (subSceneToHide != subScene) {
            subScene.moveSubScene();
            subSceneToHide = subScene;
        } else {
            subSceneToHide = null;
        }
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

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, k -> {
            if ( k.getCode() == KeyCode.SPACE){
                k.consume();
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
            inMenu = true;
        } else {
            gridPane1.setEffect(null);
            inMenu = false;
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

                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File("./"));
                fileChooser.setTitle("Load .engi file");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("engi file", "*.engi"));
                try {
                    File file = fileChooser.showSaveDialog(gameStage);
                    if (file != null) {
                        ResourceManager.save(data, file.getAbsolutePath());
                        System.out.println("Game saved to " + file.getAbsolutePath());
                        showInfo("Game saved!");
                    }
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
                    try {
                        if (eng.getPowerLevel(player.getActiveEngimon()) > player.getActiveEngimon().getPowerLevel(eng)) {
                            engimon.setScaleX(1.25);
                            engimon.setScaleY(1.25);
                        }
                    } catch (Exception e) {
                    }
                    
                    gridPane2.replaceMapWithImage(j, i, engimon);
                }
            }
        }
    }

    private void createPlayer() {
        playerPos = map.getPlayerPosition();
        gridPane2.replaceMapWithImage(playerPos.getX(),playerPos.getY(), new ImageView(player.getImgUrl()));
        if (player.getActiveEngimon() != null) {
            StackPane activePane = new StackPane();
            activePane.setPrefWidth(64);
            activePane.setMaxHeight(64);
            ImageView activeImg = player.getActiveEngimon().getSprite();
            activeImg.setFitWidth(64);
            activeImg.setFitHeight(64);
            activePane.getChildren().add(activeImg);

            ImageView activeLogo = new ImageView("view/resources/active.png");
            activeLogo.setFitHeight(15);
            activeLogo.setFitWidth(15);
            activePane.getChildren().add(activeLogo);
            StackPane.setAlignment(activeLogo, Pos.TOP_CENTER);

            gridPane2.replaceMapWithNode(map.getActiveEngiPosition().getX(), map.getActiveEngiPosition().getY(), activePane);
        }
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if (!inMenu) {
                        processKeypress();
                    }
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
        }

        if (removeActiveKey("D")) {
            player.setImgUrl(PLAYER_RIGHT);
            map.move("d");
            refreshMap();
        }

        if(removeActiveKey("W")) {
            player.setImgUrl(PLAYER_UP);
            map.move("w");
            refreshMap();
        }

        if (removeActiveKey("S")) {
            player.setImgUrl(PLAYER_DOWN);
            map.move("s");
            refreshMap();
        }

        if (removeActiveKey("I")) {
            if (infoSubScene.isVisible()) {
                infoSubScene.setVisible(false);
            } else {
                if (player.getActiveEngimon() != null) {
                    infoLabel.setText(player.getActiveEngimon().interact());
                } else {
                    infoLabel.setText("You don't have an active engimon right now");
                }
                infoSubScene.setVisible(true);
            }
        }

        if (removeActiveKey("B")) {
            battle();
        }

    }
    private void refreshMap() {
        gridPane2.getChildren().clear();
        
        for (int j = 0; j < map.getLength(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
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

    private void createBattleSubScene() {
        battleSubScene = new BattleMenuSubScene();
        gamePane.getChildren().add(battleSubScene);
        battleSubScene.moveSubScene();

        InfoLabel battleLabel = new InfoLabel("LET'S BATTLE!");
        battleLabel.setLayoutX(25);
        battleLabel.setLayoutX(25);
        battleSubScene.getPane().getChildren().add(battleLabel);
        battleSubScene.getPane().getChildren().add(createBattleInfo());
        battleSubScene.getPane().getChildren().add(createButtonToBattle());
        battleSubScene.getPane().getChildren().add(createButtonToRun());
    }

    private HBox createBattleInfo() {
        HBox box = new HBox();
        box.setSpacing(60);
        battleEngimon = new ArrayList<>();

        try {
            battleEngimon.add(new EngimonInfo(chosenWildEngimon, player.getActiveEngimon()));
            battleEngimon.add(new EngimonInfo(player.getActiveEngimon(), chosenWildEngimon));
        } catch (Exception e) {
            e.printStackTrace();
        }

        box.getChildren().add(battleEngimon.get(0));
        Text vs = new Text("\n\n\n\n\nVS");
        box.getChildren().add(vs);
        box.getChildren().add(battleEngimon.get(1));
        box.setLayoutX(350 - (118 * 2));
        box.setLayoutY(75);
        return box;
    }

    private void battle() throws Exception {
        if (player.getActiveEngimon() != null) {
            try {
                battleConfirmation();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            if (infoSubScene.isVisible()) {
                infoSubScene.setVisible(false);
            } else {
                infoLabel.setText("Cannot battle with no active engimon");
                infoSubScene.setVisible(true);
            }
            throw new Exception("Cannot battle with no active engimon");
        }
    }

    private void battleConfirmation() throws Exception {
        aroundTiles = map.getTilesWithEngimonAroundPlayer();
        if (aroundTiles.size() == 0) {
            if (infoSubScene.isVisible()) {
                infoSubScene.setVisible(false);
            } else {
                infoLabel.setText("There's no engimon around you");
                infoSubScene.setVisible(true);
            }
            throw new Exception("There's no engimon around you");
        }
        else if (aroundTiles.size() > 1) {
            inMenu = true;
            createWildEngimonChooserSubScene();
        } else {
            inMenu = true;
            tileWithEngimon = aroundTiles.get(0);
            chosenWildEngimon = aroundTiles.get(0).getWildEngimon();
            createBattleSubScene();
        }
    }

    private void continueBattle() {
        Engimon playerEngimon = player.getActiveEngimon();

        double playerPowerLevel = playerEngimon.getPowerLevel(chosenWildEngimon);
        double wildPowerLevel = chosenWildEngimon.getPowerLevel(playerEngimon);

        if (playerPowerLevel >= wildPowerLevel) {
            // win
            String engiName;
            showMessageSubscene(playerEngimon.getName() + " won!");
            battleStage.showAndWait();

            showMessageSubscene(player.gainActiveEngimonExp(20 * chosenWildEngimon.getLevel()));
            battleStage.showAndWait();

            showMessageSubscene("You captured a " + chosenWildEngimon.getSpecies());
            battleStage.showAndWait();

            TextInputDialog td = new TextInputDialog("");
            td.setHeaderText("Enter your new Engimon's name: ");
            td.showAndWait();

            engiName = td.getEditor().getText();
            chosenWildEngimon.setName(engiName);
            chosenWildEngimon.setLives(3);
            String newSkillName = chosenWildEngimon.getSkills().get(0).getName();
            showMessageSubscene("You get a skill item: " + newSkillName);
            battleStage.showAndWait();

            try {
                player.addToInvSkill(new SkillItem(1, newSkillName));
            } catch (InvalidSkillNameException e) {
                e.printStackTrace();
            }
            player.addToInvEngimon(chosenWildEngimon);
            tileWithEngimon.nullifyWildEngimon();
            map.decrementWildEngimon();

            if (playerEngimon.getCumExp() > 4000) {
                showMessageSubscene("Your Engimon's cumulative EXP has reached its limit!");
                battleStage.showAndWait();
                player.removeEngimonByIndex(player.getActiveEngiIndex());

                if (player.getInventoryEngimon().countItemInInventory() > 0) {
                    player.setActiveEngimonNull();
                } else {
                    showMessageSubscene("You don't have any Engimons left");
                    battleStage.showAndWait();
                    showMessageSubscene("GAME OVER! Thank you for playing with us!");
                    battleStage.showAndWait();
                    gameStage.close();
                    menuStage.show();
                }
            }
            try {
                refreshEngiInventory();
                refreshSkillInventory();
                refreshMap();
            } catch (InvalidIndexInventory e) {
                e.printStackTrace();
            }   
        } else {
            // lose
            showMessageSubscene(chosenWildEngimon.getName() + " won! Your engimon was defeated!");
            battleStage.showAndWait();

            if (playerEngimon.die()) {
                showMessageSubscene("Your Engimon has no lives left");
                battleStage.showAndWait();

                player.removeEngimonByIndex(player.getActiveEngiIndex());
                player.setActiveEngimonNull();
                if (player.getInventoryEngimon().countItemInInventory() == 0) {
                    showMessageSubscene("You don't have any Engimons left");
                    battleStage.showAndWait();
                    showMessageSubscene("GAME OVER! Thank you for playing with us!");
                    battleStage.showAndWait();
                    gameStage.close();
                    menuStage.show();
                }
                try {
                    refreshEngiInventory();
                    refreshSkillInventory();
                    refreshMap();
                } catch (InvalidIndexInventory e) {
                    e.printStackTrace();
                }
            } else {
                showMessageSubscene("Your Engimon has " + playerEngimon.getLives() + " live(s) left");
                battleStage.showAndWait();
            }
        }
        inMenu = false;
        battleSubScene.moveSubScene();
    }

    private void createWildEngimonChooserSubScene() {
        wildEngimonChooserSubScene = new BattleMenuSubScene();
        gamePane.getChildren().add(wildEngimonChooserSubScene);
        wildEngimonChooserSubScene.moveSubScene();

        InfoLabel chooseWildEngimonLabel = new InfoLabel("CHOOSE A WILD ENGIMON");
        chooseWildEngimonLabel.setLayoutX(25);
        chooseWildEngimonLabel.setLayoutX(25);
        wildEngimonChooserSubScene.getPane().getChildren().add(chooseWildEngimonLabel);
        wildEngimonChooserSubScene.getPane().getChildren().add(createWildEngimonsToChoose());
        wildEngimonChooserSubScene.getPane().getChildren().add(createButtonToSelect());
    }

    private void createMessageSubscene() {
        messageSubScene = new GameMenuSubScene();
        messageSubScene.setSize(600, 150);
        messageSubScene.setLayoutX(GAME_WIDTH/5);
        messageSubScene.setLayoutY(GAME_HEIGHT/5);
        messageLabel = new InfoLabel("");
        messageLabel.setFontSize(12);
        messageLabel.setLayoutX(25);
        messageLabel.setLayoutY(25);
        messageSubScene.getPane().getChildren().add(messageLabel);

        EngimonButton OKButton = new EngimonButton("OK");
        OKButton.setLayoutX(200);
        OKButton.setLayoutY(85);

        OKButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messageSubScene.setVisible(false);
                battleStage.hide();
            }
        });
        messageSubScene.getPane().getChildren().add(OKButton);

        gamePane.getChildren().add(messageSubScene);
        messageSubScene.setVisible(false);
    }

    private void showMessageSubscene(String message) {
        if (messageSubScene.isVisible()) {
            messageSubScene.setVisible(false);
        } else {
            messageLabel.setText(message);
            messageSubScene.setVisible(true);
        }
    }

    private HBox createWildEngimonsToChoose() {
        HBox box = new HBox();
        box.setSpacing(60);
        wildEngimonList = new ArrayList<>();

        try {
            for (int i = 0; i < aroundTiles.size(); i++) {
                wildEngimonList.add(new WildEngimonPicker(player.getActiveEngimon(), aroundTiles.get(i).getWildEngimon()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Default choice
        wildEngimonList.get(0).setIsCircleChosen(true);
        chosenWildEngimon = wildEngimonList.get(0).getEngimon();
        tileWithEngimon = aroundTiles.get(0);

        for (WildEngimonPicker engimonPicker : wildEngimonList) {
            box.getChildren().add(engimonPicker);
            engimonPicker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (WildEngimonPicker engimonPicker : wildEngimonList) {
                        engimonPicker.setIsCircleChosen(false);
                    }
                    engimonPicker.setIsCircleChosen(true);
                    chosenWildEngimon = engimonPicker.getEngimon();
                    tileWithEngimon = aroundTiles.get(wildEngimonList.indexOf(engimonPicker));
                }
            });
        }
        box.setLayoutX(300 - (118 * 2));
        box.setLayoutY(75);
        return box;
    }

    private EngimonButton createButtonToSelect() {
        EngimonButton battleButton = new EngimonButton("SELECT");
        battleButton.setLayoutX(550);
        battleButton.setLayoutY(500);

        battleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                wildEngimonChooserSubScene.moveSubScene();
                createBattleSubScene();
            }
        });
        return battleButton;
    }

    private EngimonButton createButtonToBattle() {
        EngimonButton battleButton = new EngimonButton("BATTLE");
        battleButton.setLayoutX(300);
        battleButton.setLayoutY(500);

        battleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                battleStage = new Stage();
                battleStage.setOpacity(0);
                battleStage.setX(0);
                battleStage.setY(0);
                createMessageSubscene();
                continueBattle();
            }
        });
        return battleButton;
    }

    private EngimonButton createButtonToRun() {
        EngimonButton battleButton = new EngimonButton("RUN");
        battleButton.setLayoutX(550);
        battleButton.setLayoutY(500);

        battleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                battleSubScene.moveSubScene();
                inMenu = false;
            }
        });
        return battleButton;
    }
}
