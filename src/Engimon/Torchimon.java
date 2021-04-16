package Engimon;

public class Torchimon extends Engimon {
    public Torchimon(String name){
        super(name, Element.FIRE, Element.ELECTRIC);
        species = "Torchimon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Engimon ini overloaded", name));
    }
}
