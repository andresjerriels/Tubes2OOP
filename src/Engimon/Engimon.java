package Engimon;

import java.util.*;

/**
 * Engimon
 */
public abstract class Engimon {
    protected String name;
    protected String species;
    // protected String slogan;
    protected int lives;
    protected ArrayList<String> parentNames;
    protected ArrayList<String> parentSpecies;
    // protected ArrayList<Skill> skills;
    protected ArrayList<Element> elements;
    protected int level;
    protected int exp;
    protected int cum_exp;

    abstract public void interact();
        
    public Engimon() {
        this("", Element.NONE, Element.NONE);
    }

    public Engimon(String _name, Element elmt1, Element elmt2) {
        name = _name;
        elements.clear();
        if (elmt1 != Element.NONE)
            elements.add(elmt1);
        if (elmt2 != Element.NONE)
            elements.add(elmt2);
        level = 1;
        exp = 0;
        cum_exp = 0;
        lives = 3;
        parentNames = new ArrayList<String>();
        parentNames.add("None");
        parentNames.add("None");
        parentSpecies = new ArrayList<String>();
        parentSpecies.add("None");
        parentSpecies.add("None");
    }

    public Engimon breed(Engimon e) throws Exception {
        if (this != e) {
            if (level > 3 && e.getLevel() > 3) {
                double adv1 = calcTypeAdvantage(e);
                double adv2 = e.calcTypeAdvantage(this);
                String nm;
                String spc;
          
                level = level - 3;
                e.setLevel(e.getLevel() - 3);

                // jika elemen sama atau advantage lebih tinggi
                if (elements == e.getElements() || adv1 > adv2) {
                    spc = species;
                } else if (adv1 < adv2) {  // elemen berbeda dan advantage lebih kecil
                    spc = e.getSpecies();
                } else {  // elemen berbeda dan advantage sama
                    if ((elements.get(0) == Element.FIRE && e.getElements().get(0) == Element.ELECTRIC) ||
                        (elements.get(0) == Element.ELECTRIC && e.getElements().get(0) == Element.FIRE)) {
                    spc = "Torchimon";
                    } else if ((elements.get(0) == Element.WATER && e.getElements().get(0) == Element.GROUND) ||
                            (elements.get(0) == Element.GROUND && e.getElements().get(0) == Element.WATER)) {
                    spc = "Dittimon";
                    } else {
                    spc = "Tortomon";
                    }
                }

                Engimon child = EngimonFactory.createEngimon(nm, spc);

                child.setParents(this, e);

            } else {
                throw new Exception("Level parent < 4");
            }
        } else {
            throw new Exception("Cannot breed with self");
        }
    }

    public boolean isSkillLearned(Skill s) {
        for (Skill skill : skills) {
            if (skill.getName() == s.getName()) {
                return true;
            }
        }
        return false;
    }
      
    // public boolean canLearn(Skill s) {
    // for (auto i = elements.begin(); i != elements.end(); i++) {
    //     for (auto j = 0; j < s.getnSkillElmt(); j++) {
    //     if (ElementTypes[(*i)] == s.getSkillElements()[j]) {
    //         return true;
    //     }
    //     }
    // }
    // return false;
    // }

    public double calcPowerLevel(Engimon e) {
        double powerLvl = level * this.calcTypeAdvantage(e);

        for (Skill skill : skills) {
            powerLvl += skill.getBasePower() * skill.getMastery();
        }

        return powerLvl;
    }

    private double calcTypeAdvantage(Engimon e) {
        ArrayList<Element> otherElements = e.getElements();
        double maxAdv = -1;

        for (Element element : this.elements) {
            for (Element element2 : otherElements) {
                if (element.calcTypeAdvantage(element2) > maxAdv) {
                    maxAdv = element.calcTypeAdvantage(element2);
                }
            }
        }
      
        return maxAdv;
    }

    public String getSpecies() {
        return species;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCum_exp(int cum_exp) {
        this.cum_exp = cum_exp;
        level = cum_exp/100 + 1;
        exp = cum_exp % 100;
    }
}