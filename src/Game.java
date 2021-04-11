import java.util.*;
public class Game {
    private Peta map;
    private Player player;
    private bool gameOver;

    public void start(){
        Scanner sc = new Scanner(System.in);
        String command;

        int engiChoice;
        
        // input pilihan engimon
        engiChoice = sc.nextInt();
        while(engiChoice < 1 || engiChoice > 2){
            engiChoice = sc.nextInt();
        }

        String engiName = sc.nextLine();

        // Create player

        do{
            command = sc.nextLine();
            processCommand(command);
        } while(command.equals("exit"));
        

    }

    public void processCommand(String command){
        if(command == "w" || command == "a" || command == "s" || command == "d"){

        } else if (command == "interact"){

        } else if(command == "exit"){

        } else if(command == "battle"){

        } else if(command == "list"){

        } else if(command == "skills"){
            
        } else if(command == "engimons"){

        } else if(command == "breed"){

        } else if(command == "change"){

        } else if(command == "learn"){

        }
    }
}
