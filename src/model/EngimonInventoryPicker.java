package model;

import game.Engimon.Engimon;

public class EngimonInventoryPicker extends EngimonStarterPicker {
    EngimonInventoryItem engiItem;

    public EngimonInventoryPicker(Engimon engimon, int idx) {
        super(engimon);
        engiItem = new EngimonInventoryItem(engimon, false, idx);
        
        this.getChildren().clear();
        this.setSpacing(5);
        circleImage.setFitWidth(20);
        circleImage.setFitHeight(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(engiItem);
    }
    
}
