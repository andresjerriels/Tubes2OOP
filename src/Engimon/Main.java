package Engimon;

import Skill.Skill;

public class Main {
    public static void main(String[] args) throws Exception {
        Engimon test = EngimonFactory.createEngimon("P1", "Charmamon");
        Engimon test2 = EngimonFactory.createEngimon("P2", "Electromon");

        test.printInfo();

        test.setLevel(10);
        test2.setLevel(10);

        test.addSkill(new Skill("Fire Bolt"));
        test.addSkill(new Skill("Fire Breath"));

        test.printSkills();

        test2.getSkills().get(0).setMastery(2);

        Engimon child = test.breed(test2);
        test.printInfo();
    }
}
