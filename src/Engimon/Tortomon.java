package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Tortomon extends Engimon {
    public Tortomon(String name){
        super(name, Element.WATER, Element.ICE, "view/resources/engimons/tortomon.gif");
        species = "Tortomon";
        try {
            skills.add(new Skill("Tortomon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon kura-kura", name));
    }
}
