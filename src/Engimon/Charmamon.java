package Engimon;

public class Charmamon extends Engimon {
    Charmamon(String name){
        super(name, Element.FIRE, Element.NONE);
        species = "Charmamon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon api", name));
    }
}
