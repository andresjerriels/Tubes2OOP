package Engimon;

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
};