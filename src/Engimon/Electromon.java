package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Electromon extends Engimon {
    Electromon(String name){
        super(name, Element.ELECTRIC, Element.NONE);
        species = "Electromon";
        try {
            skills.add(new Skill("Electromon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon listrik", name));
    }
}
