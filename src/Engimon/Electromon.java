package Engimon;

public class Electromon extends Engimon {
    Electromon(String name){
        super(name, Element.ELECTRIC, Element.NONE);
        species = "Electromon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon listrik", name));
    }
}
