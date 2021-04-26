package game.Engimon;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public enum Element { 
    NONE(-1), FIRE(0), WATER(1), ELECTRIC(2), GROUND(3), ICE(4);

    private int value;
    final double typeAdvTable[][] = {{1, 0, 1, 0.5, 2}, 
                                    {2, 1, 0, 1, 1},
                                    {1, 2, 1, 0, 1.5},
                                    {1.5, 1, 2, 1, 0},
                                    {0, 1, 0.5, 2, 1}};

    Element(int v) {
        value = v;
    }

    public int getValue() {
        return value;
    }

    public double calcTypeAdvantage(Element e) {
        return typeAdvTable[this.value][e.getValue()];
    }

    public String getName() {
        String elmtName = "";
        switch (this.value) {
            case 0:
                elmtName = "Fire";
                break;
            case 1:
                elmtName = "Water";
                break;
            case 2:
                elmtName = "Electric";
                break;
            case 3:
                elmtName = "Ground";
                break;
            case 4:
                elmtName = "Ice";
                break;
            default:
                elmtName = "None";
                break;
        }
        return elmtName;
    }

    public DropShadow getAura() {
        DropShadow aura = new DropShadow();
        aura.setWidth(30);
        aura.setHeight(30);
        aura.setSpread(0.3);
        switch (this.value) {
            case 0:
                aura.setColor(Color.RED);
                break;
            case 1:
                aura.setColor(Color.BLUE);
                break;
            case 2:
                aura.setColor(Color.YELLOW);
                break;
            case 3:
                aura.setColor(Color.SADDLEBROWN);
                break;
            case 4:
                aura.setColor(Color.AQUAMARINE);
                break;
            default:
                break;
        }
        return aura;
    }

    public static Element fromName(String name) {
        Element elmt = NONE;
        switch (name) {
            case "Fire":
                elmt = FIRE;
                break;
            case "Water":
                elmt = WATER;
                break;
            case "Electric":
                elmt = ELECTRIC;
                break;
            case "Ground":
                elmt = GROUND;
                break;
            case "Ice":
                elmt = ICE;
                break;
            default:
                elmt = NONE;
                break;
        }
        return elmt;
    }

    public String getImageUrl() {
        String url = "";
        switch (this.value) {
            case 0:
                url = "view/resources/elements/fire.png";
                break;
            case 1:
                url = "view/resources/elements/water.png";
                break;
            case 2:
                url = "view/resources/elements/electric.png";
                break;
            case 3:
                url = "view/resources/elements/ground.png";
                break;
            case 4:
                url = "view/resources/elements/ice.png";
                break;
            default:
                url = "";
                break;
        }
        return url;
    }
};