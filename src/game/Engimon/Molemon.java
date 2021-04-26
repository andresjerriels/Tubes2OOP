package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Molemon extends Engimon {
    Molemon(String name){
        super(name, Element.GROUND, Element.NONE);
        species = "Molemon";
        try {
            skills.add(new Skill("Molemon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Engimon ini suka menggali", name));
        return String.format("[%s]: Engimon ini suka menggali", name);
    }

    @Override
    public ImageView getSprite() {
        return applyAura(new ImageView("view/resources/engimons/molemon.gif"));
    }
}
