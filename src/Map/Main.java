package Map;
import java.util.*;

public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Peta peta = new Peta("../files/map.txt");

        String command;

        do{
            peta.PrintPeta();
            command = sc.nextLine();
            peta.move(command);
        } while(!command.equals("exit"));

        sc.close();
    }
}
