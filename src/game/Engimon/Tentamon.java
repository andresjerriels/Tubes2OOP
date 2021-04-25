package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Tentamon extends Engimon {
    public Tentamon(String name){
        super(name, Element.WATER, Element.NONE);
        species = "Tentamon";
        try {
            skills.add(new Skill("Tentamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon ubur-ubur", name));
        return String.format("[%s]: Ada engimon ubur-ubur", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/tentamon.gif");
    }
}
