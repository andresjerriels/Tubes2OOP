// package Map;
import java.util.*;
import java.io.*;

public class Peta{
    public static void main(String args[]){
        Peta peta = new Peta("../../files/map.txt");
        peta.PrintPeta();
    }

    private int nWiildEngimon;
    private int length; //panjang ke bawah
    private int width; //panjang ke samping
    private static int maxWildEngimon = 10;
    private ArrayList<ArrayList<Tile>> matriksPeta;
    private Position playerPosition;
    private Position activeEngimonPosition;
    private int lvlCapslock;

    public Peta(String path){
        nWiildEngimon = 0;

        matriksPeta = new ArrayList<ArrayList<Tile>>();

        try{
            File filePeta = new File(path);
            Scanner fileScanner = new Scanner(filePeta);
            while(fileScanner.hasNextLine()){
                ArrayList<Tile> rowPeta = new ArrayList<Tile>();

                String line = fileScanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    Tile tile = new Tile(line.charAt(i));
                    rowPeta.add(tile);
                }
                matriksPeta.add(rowPeta);
            }    
            
            fileScanner.close();
        } catch(FileNotFoundException e){
            System.out.println("");
            e.printStackTrace();
        }

        length = matriksPeta.size();
        width = matriksPeta.get(0).size(); 

        // Random spawn point player
        Random rand = new Random();

        int x = rand.nextInt(width);
        int y = rand.nextInt(length);

        playerPosition = new Position(x,y);

        if(x > 0) activeEngimonPosition = new Position(x-1,y);
        else activeEngimonPosition = new Position(x+1, y);
    }

    public void PrintPeta(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if(playerPosition.isEqual(i, j)) System.out.print("P");
                else if(activeEngimonPosition.isEqual(i, j)) System.out.print("X");
                // else if (matriksPeta.get(i).get(j).containWildEngimon())
                else System.out.print(matriksPeta.get(i).get(j).GetSymbol() + " ");
            }
            System.out.println();
        }
    }

    // << "* Navigation:                                       *\n"
    //      << "* W/w: Water engimon                                *\n"
    //      << "* I/i: Ice engimon                                  *\n"
    //      << "* F/f: Fire engimon                                 *\n"
    //      << "* G/g: Ground engimon                               *\n"
    //      << "* E/e: Electric engimon                             *\n"
    //      << "* L/l: Fire/Electric engimon                        *\n"
    //      << "* S/s: Water/Ice engimon                            *\n"
    //      << "* N/n: Water/Ground engimon                         *\n"
    //      << "*                                                   *\n"
    //      << "* o: Sea   -: Grassland   #: Tundra   +: Mountain   *\n"
    //      << "* P: Player    X: Active Engimon                    *\n"

    public void GenerateEngimon(int minLvl, int maxLvl){
        Random rand = new Random();

        if(nWiildEngimon < maxWildEngimon && rand.nextInt(5) == 0){
            // random position
            int x = rand.nextInt(width);
            int y = rand.nextInt(length);

            int level = rand.nextInt(maxLvl - minLvl + 1) + minLvl;
            Tile tile = matriksPeta.get(y).get(x);

            if(!tile.containWildEngimon() && !playerPosition.isEqual(x, y) && !activeEngimonPosition.isEqual(x, y)){
                if(tile.getType() == TileType.Mountain) {}
                else if(tile.getType() == TileType.Tundra) {}
                else if(tile.getType() == TileType.Sea) {}
                else if(tile.getType() == TileType.Grassland) {}
                nWiildEngimon++;
            }

        }
    }

    public void move(char direction){
        playerPosition.setXY(direction);
        if(isPlayerOutOfRange() || isPlayerTileContainEngimon()){
            playerPosition.resetXY(direction);
            return;
        }
        activeEngimonPosition.setXY(playerPosition);
        activeEngimonPosition.resetXY(direction);
        
        // int level = activeEngimonLevel
        int level = 1;
        // moveWildEngimon();
        GenerateEngimon(level, level + 2);
    }

    private boolean isPlayerOutOfRange(){
        return playerPosition.getX() < 0 || playerPosition.getX() >= width || playerPosition.getY() < 0 || playerPosition.getY() >= length;
    }

    private boolean isPlayerTileContainEngimon(){
        return matriksPeta.get(playerPosition.getY()).get(playerPosition.getX()).containWildEngimon();
    }

    private boolean isPositionOccupied(int x, int y){
        return playerPosition.isEqual(x, y) || activeEngimonPosition.isEqual(x, y);
    }

    public void moveWildEngimons(){
        Random rand = new Random();

        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++){
                if(matriksPeta.get(i).get(j).containWildEngimon() && rand.nextInt(2) == 1){
                    int direction = rand.nextInt(4);
                    switch (direction)
                    {
                    case 0: // ke atas
                        if(i-1 >= 0 && i < length && !matriksPeta.get(i-1).get(j).containWildEngimon() && !playerPosition.isEqual(j,i-1) 
                           && !activeEngimonPosition.isEqual(j,i-1)
                           && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i-1).get(j))){
                            matriksPeta.get(i-1).get(j).setWildEngimon(matriksPeta.get(i).get(j).getWildEngimonPointer());
                            matriksPeta.get(i-1).get(j).moveWildEngimon();
                        }
                        break;
                    case 1: // ke kiri
                        if(j < width && j-1 >= 0 && !matriksPeta.get(i).get(j-1).containWildEngimon() && !playerPosition.isEqual(j-1,i) && !activeEngimonPosition.isEqual(j-1,i) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i).get(j-1))){
                            matriksPeta.get(i).get(j-1).setWildEngimon(matriksPeta.get(i).get(j).getWildEngimonPointer());
                            matriksPeta.get(i).get(j).moveWildEngimon();
                        }
                        break;
                    case 2: // ke bawah
                        if(i+1 < length && i >= 0 && !matriksPeta.get(i+1).get(j).containWildEngimon() && !playerPosition.isEqual(j,i+1) && !activeEngimonPosition.isEqual(j,i+1) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i+1).get(j))){
                            matriksPeta.get(i+1).get(j).setWildEngimon(matriksPeta.get(i).get(j).getWildEngimonPointer());
                            matriksPeta.get(i).get(j).moveWildEngimon();
                        }
                        break;
                    case 3: // ke kanan
                        if(j >= 0 && j+1 < width && !matriksPeta.get(i).get(j+1).containWildEngimon() && !playerPosition.isEqual(j+1,i) && !activeEngimonPosition.isEqual(j+1,i) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i).get(j+1))){
                            matriksPeta.get(i).get(j+1).setWildEngimon(matriksPeta.get(i).get(j).getWildEngimonPointer());
                            matriksPeta.get(i).get(j).moveWildEngimon();
                        }
                        break;
                    
                    default:
                        break;
                    }
                }
            }
        }
    }

    public void setLevelCapslock(int _lvlCapslock){
        lvlCapslock = _lvlCapslock;
    }

    public void decrementWildEngimon(){
        nWiildEngimon--;
    }

    public ArrayList<Tile> getTilesWithEngimonAroundPlayer(){
        ArrayList<Tile> tilesWithEngimon;
        int x = playerPosition.getX();
        int y = playerPosition.getY();

        if(y != 0 && matriksPeta.get(y-1).get(x).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y-1).get(x));
        if(y != length - 1 && matriksPeta.get(y+1).get(x).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y+1).get(x));
        if(x != 0 && matriksPeta.get(y).get(x-1).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y).get(x-1));
        if(x != width - 1 && matriksPeta.get(y).get(x+1).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y).get(x+1));
        return tilesWithEngimon;
    }
}