package Engimon;

public class Dittimon extends Engimon {
    Dittimon(String name){
        super(name, Element.WATER, Element.GROUND);
        species = "Dittimon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon lumpur", name));
    }
}
