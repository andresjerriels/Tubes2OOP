package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

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

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon mamut", name));
        return String.format("[%s]: Ada engimon mamut", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/mamomon.gif");
    }
}
