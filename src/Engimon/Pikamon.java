package Engimon;

public class Pikamon extends Engimon {
    public Pikamon(String name){
        super(name, Element.ELECTRIC, Element.NONE);
        species = "Pikamon";
    }

    public void interact() {
        System.out.println(String.format("[%s]: Bzzt! Engimon ini menyengat!", name));
    }
}
