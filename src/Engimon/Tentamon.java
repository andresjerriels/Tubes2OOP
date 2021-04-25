package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Tentamon extends Engimon {
    public Tentamon(String name){
        super(name, Element.WATER, Element.NONE,"view/resources/engimons/tentamon.gif");
        species = "Tentamon";
        try {
            skills.add(new Skill("Tentamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon ubur-ubur", name));
    }
}
