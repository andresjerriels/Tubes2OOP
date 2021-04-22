import java.util.*;
import Map.*;
import Engimon.*;
import Player.*;
import Skill.*;

public class Game {
    private Peta map;
    private Player player;
    private boolean gameOver;
    private Scanner sc;

    public void start(){
        gameOver = false;
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
            player = new Player(engiName, engiChoice-1);
            map = new Peta("../files/map.txt", player);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }


        do{
            map.PrintPeta();
            command = sc.nextLine();
            processCommand(command);
        } while(!command.equals("exit") && !gameOver);
        
        sc.close();
    }

    public void processCommand(String command){
        try{
            if(command.equals("w") || command.equals("a") || command.equals("s") || command.equals("d")){
                map.move(command);
            } else if (command.equals("interact")){
                if (player.getActiveEngimon() != null) {
                    player.getActiveEngimon().interact();
                } else {
                    System.out.println("You don't have an active engimon right now");
                }
            } else if(command.equals("exit")){
                saveConfirmation();
            } else if(command.equals("battle")){
                battle();
            } else if(command.equals("help")){

            } else if(command.equals("skills")){
                
            } else if(command.equals("engimons")){

            } else if(command.equals("breed")){
                breedingConfirmation();
            } else if(command.equals("change")){
                changeActiveEngimonConfirmation();
            } else if(command.equals("learn")){
                learnSkillConfirmation();
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void learnSkillConfirmation() throws Exception {
        if (player.getInventorySkill().countItemInInventory() > 0) {
            int engiChoice, skillChoice;
            System.out.println("Your Engimon(s):");
            player.getInventoryEngimon().printInventory();
        
            System.out.print("Choose your engimon: ");
            engiChoice = Integer.parseInt(sc.nextLine());;
        
            Engimon engi = player.getEngiRefFromIndex(engiChoice-1);
        
            System.out.println("Your Skill Item(s):");
            player.getInventorySkill().printInventory();
            System.out.print("Choose a skill item: ");
            skillChoice = Integer.parseInt(sc.nextLine());;;
        
            SkillItem skill = player.getSkillRefFromIndex(skillChoice-1);
              
            if (skill.learn(engi) == 0) {
              player.removeSkillByIndex(skillChoice-1);
            }
          } else {
            throw new Exception("*         You don't have any skill items            *");
          }
    }

    public void saveConfirmation(){
        System.out.println("Do you want to save your progress?");
    }

    public void battle() throws Exception{
        if (player.getActiveEngimon() != null) {
            Tile tileWithEngimon;
            try{
                tileWithEngimon = battleConfirmation();
            } catch(Exception e){
                throw(e);
            }

            Engimon playerEngimon = player.getActiveEngimon();
            Engimon wildEngimon = tileWithEngimon.getWildEngimon();

            double playerPowerLevel = playerEngimon.getPowerLevel(wildEngimon);
            double wildPowerLevel = wildEngimon.getPowerLevel(wildEngimon);

            System.out.println(playerEngimon.getName());
            System.out.println("Power level: " + playerPowerLevel);
            System.out.println("                        vs                         ");
            System.out.println(wildEngimon.getName());
            System.out.println("Power level: " + wildPowerLevel);

            if(playerPowerLevel >= wildPowerLevel){
                // win
                String engiName;
                System.out.println(playerEngimon.getName() + " won!!");
                player.gainActiveEngimonExp(20*wildEngimon.getLevel());
                System.out.println("You captured a " + wildEngimon.getSpecies());
                System.out.println("Enter your new engimon's name: ");
                engiName = sc.nextLine();
                wildEngimon.setName(engiName);
                wildEngimon.setLives(3);
                String newSkillName = wildEngimon.getSkills().get(0).getName();
                System.out.println("You get a skill item: " + newSkillName);
                player.addToInvSkill(new SkillItem(1, newSkillName));
                player.addToInvEngimon(wildEngimon);
                tileWithEngimon.nullifyWildEngimon();
                map.decrementWildEngimon();

                if (playerEngimon.getCumExp() > 4000) {
                    System.out.println("Engimon's cumulative EXP has reached its limit");
                    player.removeEngimonByIndex(player.getActiveEngiIndex());
                    if (player.getInventoryEngimon().countItemInInventory() > 0) {
                        // System.out.println("Please select another active engimon");
                        // int i;
                        // System.out.println("Your Engimon(s):");
                        // player.getInventoryEngimon().printInventory();
                
                        // System.out.println("Choose an engimon: ");
                
                        // System.out.print("Choice: ");
                        // i = Integer.parseInt(sc.nextLine());
                        // while (i < 1 || i > player.getInventoryEngimon().countItemInInventory()) {
                        //     System.out.print("Choice: ");
                        //     i = Integer.parseInt(sc.nextLine());
                        // }
                
                        // player.setActiveEngimon(i-1);
                        player.setActiveEngimonNull();
                    } else {
                        System.out.println("You don't have any engimons left");
                        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
                        System.out.println("*                     GAME OVER                     *");
                        System.out.println("*           Thank you for playing with us!          *");
                        System.out.println("*                    See you soon!                  *");
                        gameOver = true;
                    }
                }
            } else {
                // lose
                System.out.println(wildEngimon.getName() + " won!!");
                System.out.println("Your engimon was defeated in battle");

                if (playerEngimon.die()) {
                    System.out.println("Your engimon has no lives left");
                    player.removeEngimonByIndex(player.getActiveEngiIndex());
                    player.setActiveEngimonNull();
                    if (player.getInventoryEngimon().countItemInInventory() == 0) {
                        System.out.println("You don't have any engimons left");
                        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
                        System.out.println("*                     GAME OVER                     *");
                        System.out.println("*           Thank you for playing with us!          *");
                        System.out.println("*                    See you soon!                  *");
                        gameOver = true;
                    }
                } else {
                    System.out.println("Your engimon has " + playerEngimon.getLives() + " live(s) left");
                }
            }
        } else {
            throw new Exception("Cannot battle with no active engimon");
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
                engiSelection = Integer.parseInt(sc.nextLine());
            } while(engiSelection < 1 || engiSelection > tiles.size());
        } else {
            engiSelection = 1;
        }

        System.out.println("Wild engimon info:");
        tiles.get(engiSelection-1).getWildEngimon().printInfo();

        System.out.print("Continue battle (y/n)? ");

        continueSelection = sc.nextLine();

        if(!continueSelection.toUpperCase().equals("Y")){
            throw(new Exception("Cancelling battle"));
        }

        return tiles.get(engiSelection-1);
    }

    public void breedingConfirmation() throws Exception{
        if(!player.isInventoryFull()){
            if(player.getInventoryEngimon().countItemInInventory() > 1){
                int engi1, engi2;
                //print list engimon
                System.out.println("Your Engimon(s):");
                player.getInventoryEngimon().printInventory();

                System.out.print("Choose your first engimon: ");
                engi1 = Integer.parseInt(sc.nextLine());
                System.out.print("Choose your second engimon: ");
                engi2 = Integer.parseInt(sc.nextLine());
    
                try{
                    Engimon child = player.getEngiRefFromIndex(engi1-1).breed(player.getEngiRefFromIndex(engi2-1));
                    player.addToInvEngimon(child);
                } catch (Exception e){
                    throw(e);
                } 
            } else {
                throw(new Exception("You only have 1 engimon.\nYou need at least 2 engimons to breed!"));
            }
        } else {
            throw(new Exception("Cannot breed! Inventory full!"));
        }
    }

    private void changeActiveEngimonConfirmation() throws Exception {
        System.out.println("Current active engimon:");
        System.out.println(player.getActiveEngimon() != null ? player.getActiveEngimon() : "None");
        int i;
        System.out.println("Your Engimon(s):");
        player.getInventoryEngimon().printInventory();
        
        System.out.print("Choose an engimon: ");
        i = Integer.parseInt(sc.nextLine());
        if(i <= player.getInventoryEngimon().countItemInInventory()){
          player.setActiveEngimon(i-1);
        } else {
          throw new Exception("Index out of range");
        }
    }
}
