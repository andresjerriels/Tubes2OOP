package Engimon;

import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Pikamon extends Engimon {
    public Pikamon(String name){
        super(name, Element.ELECTRIC, Element.NONE);
        species = "Pikamon";
        try {
            skills.add(new Skill("Pikamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Bzzt! Engimon ini menyengat!", name));
    }
}
