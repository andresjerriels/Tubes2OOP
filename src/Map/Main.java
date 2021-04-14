import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Peta peta = new Peta("../../files/map.txt");
        peta.PrintPeta();

        String command;

        do{
            command = sc.nextLine();
            peta.move(command);
        } while(!command.equals("exit"));
    }
}
