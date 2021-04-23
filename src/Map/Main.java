package Map;
import java.util.*;
import Player.*;
public class Main {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Player player = new Player("Eve", 1);
        Peta peta = new Peta("../files/map.txt", player);

        String command;

        do{
            peta.PrintPeta();
            command = sc.nextLine();
            try{
                peta.move(command);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while(!command.equals("exit"));

        sc.close();
    }
}
