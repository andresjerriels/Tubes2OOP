package Engimon;

public class Tortomon extends Engimon {
    public Tortomon(String name){
        super(name, Element.WATER, Element.ICE);
        species = "Tortomon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon kura-kura", name));
    }
}
