package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Dittimon extends Engimon {
    Dittimon(String name){
        super(name, Element.WATER, Element.GROUND);
        species = "Dittimon";
        try {
            skills.add(new Skill("Dittimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon lumpur", name));
        return String.format("[%s]: Ada engimon lumpur", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/dittimon.gif");
    }
}
