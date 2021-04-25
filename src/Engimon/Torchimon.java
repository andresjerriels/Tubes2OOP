package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Torchimon extends Engimon {
    public Torchimon(String name){
        super(name, Element.FIRE, Element.ELECTRIC, "view/resources/engimons/torchimon.gif");
        species = "Torchimon";
        try {
            skills.add(new Skill("Torchimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Engimon ini overloaded", name));
    }
}
