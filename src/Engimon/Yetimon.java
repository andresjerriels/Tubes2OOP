package Engimon;

public class Yetimon extends Engimon {
    public Yetimon(String name){
        super(name, Element.ICE, Element.NONE);
        species = "Yetimon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada raksasa salju", name));
    }
}
