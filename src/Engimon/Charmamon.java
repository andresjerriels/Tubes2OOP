package Engimon;
import Skill.InvalidSkillNameException;
import Skill.Skill;

public class Charmamon extends Engimon {
    Charmamon(String name){
        super(name, Element.FIRE, Element.NONE, "view/resources/engimons/charmamon.gif");
        species = "Charmamon";
        try {
            skills.add(new Skill("Charmamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public void interact() {
        System.out.println(String.format("[%s]: Ada engimon api", name));
    }
}
