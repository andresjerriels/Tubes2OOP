package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Mamomon extends Engimon {
    Mamomon(String name){
        super(name, Element.ICE, Element.NONE);
        species = "Mamomon";
        try {
            skills.add(new Skill("Mamomon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon mamut", name));
    }
}
