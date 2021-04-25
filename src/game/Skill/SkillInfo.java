package game.Skill;

import java.io.Serializable;

public class SkillInfo implements Serializable {
    private final String[] ElementTypes = {"Fire","Water","Electric","Ground","Ice"};
    private final String[][] EngimonBaseInfo =   {  // Enginame, UniqueSkill, EngimonElement
            {"Charmamon","Flame Whip","Fire"},
            {"Torchimon", "Burn", "Fire"},
            {"Tentamon", "Ink Stroke", "Water"},
            {"Tortomon", "Water Cannon", "Water"},
            {"Pikamon", "Volt Tackle" , "Electric"},
            {"Electromon", "Thunderwhip", "Electric"},
            {"Dittimon", "Dig Strike", "Ground"},
            {"Molemon", "Dirt Claw", "Ground"},
            {"Yetimon", "Cold Stomp", "Ice"},
            {"Mamomon", "Ice Skewer", "Ice"}
        };

    private final int[] USkillBP = {40,45,35,45,40,45,35,45,35,45};
    private final String[][] LearnableSkill ={
            {"Flame Claw", "Fire Breath", "Fire Bolt", "Flame Punch", "Fiery Explosion", "Magma Spill", "Cold Burn"},
            {"Water Spill", "Water Slice", "Mud Storm", "Water Burst", "Surf Wave", "Hydro Cannon", "Tsunami"},
            {"Shock", "Lightning Bolt", "Thunderbolt", "Electric Surge", "Electric Discharge", "Zap Cannon" ,"Storm Hammer"},
            {"Rock Throw", "Tremor", "Mud Storm", "Stone Cutter", "Earth Spike", "Bury", "Earthquake"},
            {"Frostbite", "Frost Breath", "Ice Spike", "Chilling Touch", "Winter Curse" ,"Ice Blast", "Cold Burn"}
        };
    private final int[] LearnableSkillBP = {40,45,55,65,75,75,80};

    public SkillInfo(){}

    public String[] getElementTypes(){
        return ElementTypes;
    }
    public String[][] getEngimonBaseInfo(){
        return EngimonBaseInfo;
    }
    public int[] getUSkillBP(){
        return USkillBP;
    }
    public String[][] getLearnableSkill(){
        return LearnableSkill;
    }
    public int[] getLearnableSkillBP(){
        return LearnableSkillBP;
    }
    public int total_engimon_species(){
        return EngimonBaseInfo.length;
    }
    public int total_element_ingame(){
        return ElementTypes.length;
    }





}
