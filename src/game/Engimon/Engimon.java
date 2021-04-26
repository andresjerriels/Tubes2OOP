package game.Engimon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import game.Player.InventoryItem;
import game.Skill.Skill;
import game.Skill.SkillMasteryComparator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;

/**
 * Engimon
 */
public abstract class Engimon implements InventoryItem, Comparable<Engimon>, Serializable {
    protected String name;
    protected String species;
    // protected String slogan;
    protected int lives;
    protected ArrayList<String> parentNames;
    protected ArrayList<String> parentSpecies;
    protected ArrayList<Skill> skills;
    protected ArrayList<Element> elements;
    protected int level;
    protected int exp;
    protected int cum_exp;

    abstract public String interact();

    abstract public ImageView getSprite();
        
    public Engimon() {
        this("", Element.NONE, Element.NONE);
    }

    public Engimon(String _name, Element elmt1, Element elmt2) {
        name = _name;
        elements = new ArrayList<>();
        // elements.clear(); ini bikin null exception
        if (elmt1 != Element.NONE) {
            elements.add(elmt1);
        }
        if (elmt2 != Element.NONE) {
            elements.add(elmt2);
        }
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
        skills = new ArrayList<Skill>();
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
                if (elements.equals(e.getElements()) || adv1 > adv2) {
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

                TextInputDialog td = new TextInputDialog();
                td.setHeaderText("You hatched a new " + spc + "!\n" + "Enter name");
                td.showAndWait();
                nm = td.getEditor().getText();
                Engimon child = EngimonFactory.createEngimon(nm, spc);

                child.setParents(this, e);
                
                // Ambil skills kedua parent dalam priority queue sesuai urutan mastery
                List<Skill> pAskill = new LinkedList<Skill>(this.skills);
                pAskill.sort(new SkillMasteryComparator().reversed());
                List<Skill> pBskill = new LinkedList<Skill>(e.getSkills());
                pBskill.sort(new SkillMasteryComparator().reversed());

                // Reserve unique skill anak
                Skill uniqueSkill = child.getSkills().get(0);
                Skill pAunique = pAskill.stream().filter(s -> s.getName().compareTo(uniqueSkill.getName()) == 0).findAny().orElse(null);
                Skill pBunique = pBskill.stream().filter(s -> s.getName().compareTo(uniqueSkill.getName()) == 0).findAny().orElse(null);
                if (pAunique != null && pBunique != null) {
                    if (pAunique.getMastery() != pBunique.getMastery()) {
                        child.getSkills().get(0).setMastery(Math.max(pAunique.getMastery(), pBunique.getMastery()));
                    } else {
                        child.getSkills().get(0).setMastery(Math.min(pAunique.getMastery() + 1, 3));
                    }
                    pAskill.remove(pAunique);
                    pBskill.remove(pBunique);
                } else if (pAunique != null) {
                    child.getSkills().get(0).setMastery(pAunique.getMastery());
                    pAskill.remove(pAunique);
                } else if (pBunique != null) {
                    child.getSkills().get(0).setMastery(pBunique.getMastery());
                    pBskill.remove(pBunique);
                }

                ArrayList<Skill> childSkills = child.getSkills();
                while (childSkills.size() < 4 && pAskill.size() > 0 && pBskill.size() > 0) {
                    Skill A = pAskill.get(0);
                    Skill B = pBskill.get(0);
                    // Mastery skill parent A > parent B
                    if (A.getMastery() > B.getMastery()) {
                        pAskill.remove(0);
                        if (!child.isSkillLearned(A)) {
                            childSkills.add(A);
                        }
                    } else if ((A).getMastery() < (B).getMastery()) {  // Mastery skill parent A < parent B
                        pBskill.remove(0);
                        if (!child.isSkillLearned(B)) {
                            childSkills.add(B);
                        }
                    } else {  // Mastery skill parent A = parent B
                        pAskill.remove(0);
                        if (!child.isSkillLearned(A)) {
                            if (e.isSkillLearned(A)) {
                                A.setMastery(Math.min(A.getMastery() + 1, 3));
                                childSkills.add(A);
                            } else {
                                childSkills.add(A);
                            }
                        }
                    }
                }
            
                if (child.getSkills().size() < 4) {
                    if (pAskill.size() == 0) {
                        while (child.getSkills().size() < 4 && pBskill.size() > 0) {
                            Skill B = pBskill.remove(0);
                            if (!child.isSkillLearned(B)) {
                                childSkills.add(B);
                            }
                        }
                    } else {
                        while (child.getSkills().size() < 4 && pAskill.size() > 0) {
                            Skill A = pAskill.remove(0);
                            if (!child.isSkillLearned(A)) {
                                childSkills.add(A);
                            }
                        }
                    }
                }

                return child;
            } else {
                throw new Exception("Level parent < 4");
            }
        } else {
            throw new Exception("Cannot breed with self");
        }
    }

    public boolean isSkillLearned(Skill s) {
        for (Skill skill : skills) {
            if (skill.getName().compareTo(s.getName())==0) {
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

    public boolean canLearn(Skill s) {
        for (Element engimonElmt : this.elements) {
            if (s.isSkillElement(engimonElmt.getName())) {
                return true;
            }
        }
        return false;
    }

    public String addSkill(Skill s) throws Exception {
        if (!this.isSkillLearned(s)) {
            if (canLearn(s)) {
                if (this.skills.size() < 4) {
                    this.skills.add(s);
                    return (this.name+" learned "+s.getName());
                } else {
                    int pilihan;
                    System.out.println("Engimon's skill full,");
                    System.out.println("choose a skill to be replaced");
                    printSkills();

                    TextInputDialog td = new TextInputDialog();
                    td.setHeaderText("Engimon's skill full, " + "choose a skill to be replaced\n" + skills +"\nEnter choice (1/2/3/4)");
                    td.showAndWait();
                    pilihan = Integer.parseInt(td.getEditor().getText());

                    if (1 <= pilihan && pilihan <= 4) {
                        String oldSkillName = skills.get(pilihan - 1).getName();
                        skills.set(pilihan-1, s);
                        return (name + " forgot " + oldSkillName + "and learned " + s.getName());
                    } else {
                        throw new Exception("Invalid choice");
                    }
                }
            } else {
                throw new Exception("Engimon type is not compatible");
            }
        } else {
            throw new Exception("Engimon already learned skill");
        }
    }

    public void printSkills() {
        int i = 1;
        for (Skill s : this.skills) {
            System.out.println(" Skill "+i+":");
            s.printSkillInfo();
            i++;
        }
    }

    public String printDetails() {
        String res = "";
        res += "Name:\t\t\t\t\t" + name + "\n";
        res += "Species:\t\t\t\t" + species + "\n";
        res += "Parent Names:\t\t" + parentNames.get(0) + "\n";
        res += "\t\t\t\t\t\t" + parentNames.get(1) + "\n";
        res += "Parent Species:\t" + parentSpecies.get(0) + "\n";
        res += "\t\t\t\t\t\t" + parentSpecies.get(1) + "\n";
        res += "ELement(s):\t\t" + elements.get(0).getName() + "\n";
        if(elements.size() == 2) res += ("\t\t\t\t\t\t" + elements.get(1).getName()) + "\n";
        res += "Lives:\t\t\t\t\t" + lives + "\n";
        res += "Level:\t\t\t\t" + level + "\n";
        res += "Exp:\t\t\t\t\t" + exp + "\n";
        res += "Cumulative Exp:\t" + cum_exp + "\n";
        // res += "Skills: " + "\n";
        // System.out.println("Name: " + name);
        // System.out.println("Species: " + species);
        // System.out.println("Parent Names: " + parentNames.get(0));
        // System.out.println("              " + parentNames.get(1));
        // System.out.println("Parent Species: " + parentSpecies.get(0));
        // System.out.println("                " + parentSpecies.get(1));
        // System.out.println("ELement(s): " + elements.get(0).getName());
        // if(elements.size() == 2) System.out.println(("            " + elements.get(1).getName()));
        // System.out.println("Lives: " + lives);
        // System.out.println("Level: " + level);
        // System.out.println("Exp: " + exp);
        // System.out.println("Cumulative Exp: " + cum_exp);
        // System.out.println("Skills: ");
        // printSkills();
        System.out.println(res);
        return res;
      }

    public double getPowerLevel(Engimon e) {
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
            if (element != Element.NONE) {
                for (Element element2 : otherElements) {
                    if (element2 != Element.NONE) {
                        if (element.calcTypeAdvantage(element2) > maxAdv) {
                            maxAdv = element.calcTypeAdvantage(element2);
                        }
                    }
                }
            }
        }
      
        return maxAdv;
    }

    public String gainExp(int xp) {
        cum_exp += xp;
      
        int newLvlAdd = (exp + xp) / 100;
        int newExp = (exp + xp) % 100;
        exp = newExp;
        level += newLvlAdd;
      
        if (newLvlAdd > 0) {
            return (name + " Leveled up to level " + level);
        } else {
            return (name + " gained " + xp + " exp");
        }
      }

    public String getSpecies() {
        return species;
    }

    public ArrayList<Element> getElements() {
        return elements;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public int getCumExp() {
        return cum_exp;
    }

    public int getLives() {
        return lives;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setCum_exp(int cum_exp) {
        this.cum_exp = cum_exp;
        level = cum_exp/100 + 1;
        exp = cum_exp % 100;
    }

    public void addCum_exp(int amount){
        cum_exp += amount;
        level = cum_exp/100 + 1;
        exp = cum_exp % 100;
    }

    public void setParents(Engimon parent1, Engimon parent2) {
        this.parentNames.clear();
        this.parentNames.add(parent1.getName());
        this.parentNames.add(parent2.getName());
        this.parentSpecies.clear();
        this.parentSpecies.add(parent1.getSpecies());
        this.parentSpecies.add(parent2.getSpecies());
    }

    public void setWild() {
        this.lives = 1;
    }

    public boolean die() {
        if (this.lives > 1) {
            this.lives -= 1;
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() { 
        ArrayList<Element> el = this.getElements();
        String result = this.getName() + " (" + el.get(0).getName() + (elements.size() == 2 ? (", " + el.get(1).getName() + ")") : ")") 
                        + " Lvl " + this.getLevel() + " " + this.getSpecies();
        return result;
    } 

    @Override
    public int compareTo(Engimon o) {
        if (elements.equals(o.getElements())) {
            return Integer.compare(o.getLevel(), level);
        } else {
            if (elements.size() != o.getElements().size()) {
                return Integer.compare(o.getElements().size(), elements.size());
            } else {
                if (elements.size() == 1) {
                    return Integer.compare(elements.get(0).getValue(), o.getElements().get(0).getValue());
                } else {
                    if (elements.get(0) ==  o.getElements().get(0)) {
                        return Integer.compare(elements.get(1).getValue(), o.getElements().get(1).getValue());
                    } else {
                        return Integer.compare(elements.get(0).getValue(), o.getElements().get(0).getValue());
                    }
                }
            }
        }
    }

    protected ImageView applyAura(ImageView sprite) {
        DropShadow aura1 = elements.get(0).getAura();
        if (elements.size() == 2) {
            DropShadow aura2 = elements.get(1).getAura();
            aura1.setInput(aura2);
        }
        sprite.setEffect(aura1);
        return sprite;
    }
}