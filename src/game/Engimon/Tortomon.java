package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Tortomon extends Engimon {
    public Tortomon(String name){
        super(name, Element.WATER, Element.ICE);
        species = "Tortomon";
        try {
            skills.add(new Skill("Tortomon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon kura-kura", name));
        return String.format("[%s]: Ada engimon kura-kura", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/tortomon.gif");
    }
}
