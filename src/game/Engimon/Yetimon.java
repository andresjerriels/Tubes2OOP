package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Yetimon extends Engimon {
    public Yetimon(String name){
        super(name, Element.ICE, Element.NONE);
        species = "Yetimon";
        try {
            skills.add(new Skill("Yetimon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Ada raksasa salju", name));
        return String.format("[%s]: Ada raksasa salju", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/yetimon.gif");
    }
}
