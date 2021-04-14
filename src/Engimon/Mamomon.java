package Engimon;

public class Mamomon extends Engimon {
    Mamomon(String name){
        super(name, Element.ICE, Element.NONE);
        species = "Mamomon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon mamut", name));
    }
}
