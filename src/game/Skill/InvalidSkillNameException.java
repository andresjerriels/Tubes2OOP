package game.Skill;

public class InvalidSkillNameException extends Exception{
    private String message;
    public InvalidSkillNameException(){
        message = "Nama skill/engimon tidak valid.";
    }
    public void printMessage(){
        System.out.println(message);
    }
}
