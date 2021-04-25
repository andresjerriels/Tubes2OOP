package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Torchimon extends Engimon {
    public Torchimon(String name){
        super(name, Element.FIRE, Element.ELECTRIC);
        species = "Torchimon";
        try {
            skills.add(new Skill("Torchimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Engimon ini overloaded", name));
        return String.format("[%s]: Engimon ini overloaded", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/torchimon.gif");
    }
}
