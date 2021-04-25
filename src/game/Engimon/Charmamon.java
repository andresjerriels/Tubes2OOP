package game.Engimon;
import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Charmamon extends Engimon {
    Charmamon(String name){
        super(name, Element.FIRE, Element.NONE);
        species = "Charmamon";
        try {
            skills.add(new Skill("Charmamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Ada engimon api", name));
        return String.format("[%s]: Ada engimon api", name);
    }

    @Override
    public ImageView getSprite() {
        return new ImageView("view/resources/engimons/charmamon.gif");
    }
}
