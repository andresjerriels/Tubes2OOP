package Engimon;

public class Tentamon extends Engimon {
    public Tentamon(String name){
        super(name, Element.WATER, Element.NONE);
        species = "Tentamon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon ubur-ubur", name));
    }
}
