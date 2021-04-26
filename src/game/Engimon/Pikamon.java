package game.Engimon;

import game.Skill.InvalidSkillNameException;
import game.Skill.Skill;
import javafx.scene.image.ImageView;

public class Pikamon extends Engimon {
    public Pikamon(String name){
        super(name, Element.ELECTRIC, Element.NONE);
        species = "Pikamon";
        try {
            skills.add(new Skill("Pikamon"));
        } catch (InvalidSkillNameException e) {
            e.printMessage();
            e.printStackTrace();
        }
    }

    public String interact() {
        System.out.println(String.format("[%s]: Bzzt! Engimon ini menyengat!", name));
        return String.format("[%s]: Bzzt! Engimon ini menyengat!", name);
    }

    @Override
    public ImageView getSprite() {
        return applyAura(new ImageView("view/resources/engimons/pikamon.gif"));
    }
}
