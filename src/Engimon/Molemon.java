package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Molemon extends Engimon {
    Molemon(String name){
        super(name, Element.GROUND, Element.NONE);
        species = "Molemon";
        try {
            skills.add(new Skill("Molemon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Engimon ini suka menggali", name));
    }
}
