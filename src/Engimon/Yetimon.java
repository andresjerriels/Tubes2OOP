package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Yetimon extends Engimon {
    public Yetimon(String name){
        super(name, Element.ICE, Element.NONE, "view/resources/engimons/yetimon.gif");
        species = "Yetimon";
        try {
            skills.add(new Skill("Yetimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada raksasa salju", name));
    }
}
