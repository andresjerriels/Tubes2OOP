package Engimon;

public class Molemon extends Engimon {
    Molemon(String name){
        super(name, Element.GROUND, Element.NONE);
        species = "Molemon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Engimon ini suka menggali", name));
    }
}
