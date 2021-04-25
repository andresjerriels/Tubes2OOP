package Skill;

import java.util.Comparator;

public class SkillMasteryComparator implements Comparator<Skill> {

    @Override
    public int compare(Skill s1, Skill s2) {
        return Integer.compare(s1.getMastery(), s2.getMastery());
    }

}