package Skill;

import javax.naming.InvalidNameException;
import java.util.ArrayList;

public class Skill {
    private String name;
    private int basepower;
    private int mastery;
    private int masteryCap = 3;
    private ArrayList<String> elements;
    SkillInfo skillInfo = new SkillInfo();

    public Skill(){
        this.name = null;
        this.basepower = 0;
        this.mastery = 0;
        this.elements = new ArrayList<String>();
    }
    public Skill(String name) throws InvalidSkillNameException {
        try {
            boolean isEngimonNameOrUniqueSkill = false;
            int k = 0;
            for (; k < skillInfo.total_engimon_species(); k++) {
                if (skillInfo.getEngimonBaseInfo()[k][0].compareTo(name) == 0) {
                    isEngimonNameOrUniqueSkill = true;
                    break;
                }
            }
            // Bukan nama Engimon, maka periksa apakah nama unique skill
            if (!isEngimonNameOrUniqueSkill) {
                k = 0;
                for (; k < skillInfo.total_engimon_species(); k++) {
                    if (skillInfo.getEngimonBaseInfo()[k][1].compareTo(name) == 0) {
                        isEngimonNameOrUniqueSkill = true;
                        break;
                    }
                }
            }
            // Apabila name merupakan nama Engimon atau nama unique skill, maka
            if (isEngimonNameOrUniqueSkill) {
                this.name = skillInfo.getEngimonBaseInfo()[k][1];
                this.basepower = skillInfo.getUSkillBP()[k];
                this.mastery = 1;
                this.masteryCap = 3;
                this.elements = new ArrayList<>();
                this.elements.add(skillInfo.getEngimonBaseInfo()[k][2]);
            } else {
                boolean isSkillName = false;
                int jName = -1;
                int countElm = 0;
                ArrayList<String> skillElements = new ArrayList<>();
                //
                // total_element_in_game digunakan karena skill diklasifikasikan berdasarkan
                // elemen.
                for (int i = 0; i < skillInfo.total_element_ingame(); i++) {
                    int totalSkillInElmtArr = skillInfo.getLearnableSkill().length;
                    for (int j = 0; j < totalSkillInElmtArr; j++) {
                        if (skillInfo.getLearnableSkill()[i][j].compareTo(name) == 0) {
                            if (!isSkillName) {
                                isSkillName = true;  // Ditemukan skill ada pada array skill
                                // setidaknya 1 kali.
                                jName = j;
                            }
                            skillElements.add(skillInfo.getElementTypes()[i]);
                            countElm++;
                            break;
                        }
                    }
                }
                if (isSkillName && jName != -1) {
                    this.name = name;
                    this.basepower = skillInfo.getLearnableSkillBP()[jName];
                    this.mastery = 1;
                    this.masteryCap = 3;
                    this.elements = skillElements;
                } else {
                    throw new InvalidSkillNameException();
                }
            }
        }
        catch (InvalidSkillNameException e){
            e.printMessage();
        }
    }
    // Getters
    public String getName() {
        return name;
    }

    public int getBasePower() {
        return basepower;
    }

    public int getMastery() {
        return mastery;
    }

    public ArrayList<String> getSkillElements() {
        return elements;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBasePower(int basepower) {
        this.basepower = basepower;
    }

    public void setMastery(int mastery) {
        this.mastery = mastery;
    }

    // Methods

    public boolean isSkillElement(String element) {
        return elements.contains(element);
    }

    public void addElTypeToElArr(String element) {
        elements.add(element);
    }

    public void printSkillInfo() {
        System.out.println("- Name        : " + name);
        System.out.println("- Basepower   : " + Integer.toString(basepower));
        System.out.println("- Mastery     : " + Integer.toString(mastery));

        for (int i = 0; i < elements.size(); i++) {
            if (i == 0) {
                System.out.println("- Element(s)  : " + elements.get(i));
            } else {
                System.out.println("                " + elements.get(i));
            }
        }
    }
}
