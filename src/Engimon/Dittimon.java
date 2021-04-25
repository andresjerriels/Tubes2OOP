package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Dittimon extends Engimon {
    Dittimon(String name){
        super(name, Element.WATER, Element.GROUND,"view/resources/engimons/dittimon.gif");
        species = "Dittimon";
        try {
            skills.add(new Skill("Dittimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon lumpur", name));
    }
}
