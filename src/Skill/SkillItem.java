package Skill;

import Engimon.Engimon;

public class SkillItem {
    private Skill skill;
    private int amount;

    public SkillItem(int amount, String skillName) throws InvalidSkillNameException {
        this.skill = new Skill(skillName);
        this.amount = amount;
    }
    public SkillItem(SkillItem s){
        this.skill = s.getSkill();
        this.amount = s.getItemAmount();
    }
    public Skill getSkill(){
        return skill;
    }
    public int getItemAmount(){
        return amount;
    }
    public void setAmount(int amount){
        this.amount = amount;
    }
    public void incrementItemAmount(){
        if (amount > 0) {
            amount++;
        }
    }
    public void decrementItemAmount(){
        if (amount > 0) {
            amount--;
        }
    }
    // Untuk digunakan dengan System.out.println()
    public String toString(){
        return Integer.toString(amount)+" "+skill.getName();
    }

    // Tunggu lebi lanjut mau kyk gmn
    public void learn(Engimon e){
        this.decrementItemAmount();
    }

}
