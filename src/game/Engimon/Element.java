package game.Engimon;

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