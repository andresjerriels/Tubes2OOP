import java.util.*;
import Map.*;
import Engimon.*;
import Player.*;
public class Game {
    private Peta map;
    private Player player;
    private boolean gameOver;
    private Scanner sc;

    public void start(){
        sc = new Scanner(System.in);
        String command;

        int engiChoice;
        
        // input pilihan engimon
        engiChoice = Integer.parseInt(sc.nextLine());
        while(engiChoice < 1 || engiChoice > 2){
            engiChoice = Integer.parseInt(sc.nextLine());
        }

        String engiName = sc.nextLine();

        try{
            player = new Player(engiName, engiChoice);
            map = new Peta("../files/map.txt", player);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }


        do{
            map.PrintPeta();
            command = sc.nextLine();
            processCommand(command);
        } while(!command.equals("exit"));
        
        sc.close();
    }

    public void processCommand(String command){
        try{
            if(command.equals("w") || command.equals("a") || command.equals("s") || command.equals("d")){
                map.move(command);
            } else if (command.equals("interact")){
                player.getActiveEngimon().interact();
            } else if(command.equals("exit")){
                saveConfirmation();
            } else if(command.equals("battle")){
                battle();
            } else if(command.equals("list")){

            } else if(command.equals("skills")){
                
            } else if(command.equals("engimons")){

            } else if(command.equals("breed")){

            } else if(command.equals("change")){

            } else if(command.equals("learn")){

            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void saveConfirmation(){
        System.out.println("Do you want to save your progress?");
    }

    public void battle() throws Exception{
        Tile tileWithEngimon;
        try{
            tileWithEngimon = battleConfirmation();
        } catch(Exception e){
            throw(e);
        }

        Engimon playerEngimon = player.getActiveEngimon();
        Engimon wildEngimon = tileWithEngimon.getWildEngimon();

        double playerPowerLevel = playerEngimon.getPowerLevel(wildEngimon);
        double wildPlayerLevel = wildEngimon.getPowerLevel(wildEngimon);

        if(playerPowerLevel > wildPlayerLevel){
            // win
        } else {
            // lose
        }
    }

    public Tile battleConfirmation() throws Exception{
        int engiSelection;
        String continueSelection;

        ArrayList<Tile> tiles = new ArrayList<Tile>();

        tiles = map.getTilesWithEngimonAroundPlayer();

        if(tiles.size() == 0) throw(new Exception("There's no engimon around you"));

        if(tiles.size() > 1){
            System.out.println("Choose a wild engimon:");
            for(int i = 0; i < tiles.size(); i++){
                System.out.println((i+1) + ". " + tiles.get(i).getWildEngimon().getName());
            }

            do{
                engiSelection = sc.nextInt();
            } while(engiSelection < 1 || engiSelection > tiles.size());
        } else {
            engiSelection = 0;
        }

        System.out.println("Wild engimon info:");
        tiles.get(engiSelection).getWildEngimon().printInfo();

        continueSelection = sc.nextLine();

        if(continueSelection != "Y"){
            throw(new Exception("Cancelling battle"));
        }

        return tiles.get(engiSelection-1);
    }

    public void breedingConfirmation() throws Exception{
        int engi1, engi2;
        //print list engimon

        if(player.isInventoryFull()){
            throw(new Exception("Cannot breed! Inventory full!"));
        }
        if(player.getInventoryEngimon().getContainer().size() == 1){
            throw(new Exception("You only have 1 engimon.\nYou need at least 2 engimons to breed!"));
        }

        System.out.print("Choose your first engimon: ");
        engi1 = sc.nextInt();
        System.out.print("Choose your second engimon: ");
        engi2 = sc.nextInt();

        try{
            Engimon child = player.getEngiRefFromIndex(engi1-1).breed(player.getEngiRefFromIndex(engi2-1));
            player.addToInvEngimon(child);
        } catch (Exception e){
            throw(e);
        }
    }
}
