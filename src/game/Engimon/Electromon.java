package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

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

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon listrik", name));
        return String.format("[%s]: Ada engimon listrik", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/electromon.gif");
    }
}
