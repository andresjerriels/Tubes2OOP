package game.Map;
import java.util.*;
import java.io.*;

import game.Engimon.*;
import game.Player.*;
public class Peta implements Serializable {

    private int nWiildEngimon;
    private int length; //panjang ke bawah
    private int width; //panjang ke samping
    private static int maxWildEngimon = 15;
    private ArrayList<ArrayList<Tile>> matriksPeta;
    private Position playerPosition;
    private Position activeEngimonPosition;
    private int lvlCapslock;
    private static Random rand = new Random();
    private Map<ArrayList<Element>, Character> engimonSymbol;
    private Player player;
    private int nTurn;

    public Peta(String path, Player _player){
        nWiildEngimon = 0;

        player = _player;

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

        int x = rand.nextInt(width);
        int y = rand.nextInt(length);

        playerPosition = new Position(x,y);

        if(x > 0) activeEngimonPosition = new Position(x-1,y);
        else activeEngimonPosition = new Position(x+1, y);

        initializeEngimonSymbol();

        while(nWiildEngimon < 5){
            GenerateEngimon(1, 3);
        }

        nTurn = 0;
    }

    public void PrintPeta(){
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(playerPosition.isEqual(j, i)) System.out.print("P ");
                else if(player.getActiveEngimon() != null && activeEngimonPosition.isEqual(j, i)) System.out.print("X ");
                else if (matriksPeta.get(i).get(j).containWildEngimon()){
                    Engimon wildEngimon = matriksPeta.get(i).get(j).getWildEngimon();
                    if(wildEngimon.getLevel() > lvlCapslock) System.out.print(Character.toUpperCase(engimonSymbol.get(wildEngimon.getElements())) + " ");
                    else System.out.print(engimonSymbol.get(wildEngimon.getElements()) + " ");
                }
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
        // random position
        int x = rand.nextInt(width);
        int y = rand.nextInt(length);

        int level = rand.nextInt(maxLvl - minLvl + 1) + minLvl;
        Tile tile = matriksPeta.get(y).get(x);

        boolean valid = false;

        while(!valid){
            if(!tile.containWildEngimon() && !playerPosition.isEqual(x, y) && !activeEngimonPosition.isEqual(x, y)){
                try {
                    if(tile.getType() == TileType.Mountain) {
                        tile.setWildEngimon(EngimonFactory.createEngimon(rand.nextInt(2)));
                        tile.getWildEngimon().setCum_exp(100*(level-1));
                    }
                    else if(tile.getType() == TileType.Tundra) {
                        tile.setWildEngimon(EngimonFactory.createEngimon(rand.nextInt(3) + 7));
                        tile.getWildEngimon().setCum_exp(100*(level-1));
                    }
                    else if(tile.getType() == TileType.Sea) {
                        tile.setWildEngimon(EngimonFactory.createEngimon(rand.nextInt(3) + 5));
                        tile.getWildEngimon().setCum_exp(100*(level-1));
                    }
                    else if(tile.getType() == TileType.Grassland) {
                        tile.setWildEngimon(EngimonFactory.createEngimon(rand.nextInt(5) + 1));
                        tile.getWildEngimon().setCum_exp(100*(level-1));
                    }
                    valid = true;
                    nWiildEngimon++;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                x = rand.nextInt(width);
                y = rand.nextInt(length);
                tile = matriksPeta.get(y).get(x);
            }    
        }

    }

    public void move(String direction) throws Exception{
        playerPosition.setXY(direction);
        if(isPlayerOutOfRange()){
            playerPosition.resetXY(direction);
            throw new Exception("oops, you hit an invisible wall!");
        } else if (isPlayerTileContainEngimon()){
            playerPosition.resetXY(direction);
            throw new Exception("ouch, you got bitten by a wild engimon!");
        }
        activeEngimonPosition.setXY(playerPosition);
        activeEngimonPosition.resetXY(direction);
        int level = player.getMaxEngiLevel();

        nTurn++;

        if(nTurn % 4 == 0){
            if (nWiildEngimon < maxWildEngimon) {
                GenerateEngimon(level, level + 2);
            }
            moveWildEngimons();
        }
        
        incrementEngimonsAge();
    }

    private boolean isPlayerOutOfRange(){
        return playerPosition.getX() < 0 || playerPosition.getX() >= width || playerPosition.getY() < 0 || playerPosition.getY() >= length;
    }

    private boolean isPlayerTileContainEngimon(){
        return matriksPeta.get(playerPosition.getY()).get(playerPosition.getX()).containWildEngimon();
    }

    private boolean isPositionOccupied(int x, int y){
        return playerPosition.isEqual(x, y) || activeEngimonPosition.isEqual(x, y) || matriksPeta.get(y).get(x).containWildEngimon();
    }

    public void moveWildEngimons() throws Exception {

        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++){
                if(matriksPeta.get(i).get(j).containWildEngimon()){
                    int direction = rand.nextInt(4);
                    switch (direction)
                    {
                    case 0: // ke atas
                        if(i-1 >= 0 && i < length && !isPositionOccupied(j,i-1) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i-1).get(j))){
                            matriksPeta.get(i-1).get(j).setWildEngimon(matriksPeta.get(i).get(j));
                        } else if (playerPosition.isEqual(j, i-1)){
                            throw new Exception("Engimon liar mencoba menempati tempatmu!");
                        } else if (activeEngimonPosition.isEqual(j, i-1)){
                            throw new Exception("Engimon liar mencoba menyerang engimonmu!");
                        }
                        break;
                    case 1: // ke kiri
                        if(j < width && j-1 >= 0 && !isPositionOccupied(j-1,i) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i).get(j-1))){
                            matriksPeta.get(i).get(j-1).setWildEngimon(matriksPeta.get(i).get(j));
                        } else if (playerPosition.isEqual(j-1, i)){
                            throw new Exception("Engimon liar mencoba menempati tempatmu!");
                        } else if (activeEngimonPosition.isEqual(j-1, i)){
                            throw new Exception("Engimon liar mencoba menyerang engimonmu!");
                        }
                        break;
                    case 2: // ke bawah
                        if(i+1 < length && i >= 0 && !isPositionOccupied(j,i+1) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i+1).get(j))){
                            matriksPeta.get(i+1).get(j).setWildEngimon(matriksPeta.get(i).get(j));
                        } else if (playerPosition.isEqual(j, i+1)){
                            throw new Exception("Engimon liar mencoba menempati tempatmu!");
                        } else if (activeEngimonPosition.isEqual(j, i+1)){
                            throw new Exception("Engimon liar mencoba menyerang engimonmu!");
                        }
                        break;
                    case 3: // ke kanan
                        if(j >= 0 && j+1 < width && !isPositionOccupied(j+1,i) && isSpeciesAndGroundTypeValid(matriksPeta.get(i).get(j).getWildEngimon(), matriksPeta.get(i).get(j+1))){
                            matriksPeta.get(i).get(j+1).setWildEngimon(matriksPeta.get(i).get(j));
                        } else if (playerPosition.isEqual(j+1, i)){
                            throw new Exception("Engimon liar mencoba menempati tempatmu!");
                        } else if (activeEngimonPosition.isEqual(j+1, i)){
                            throw new Exception("Engimon liar mencoba menyerang engimonmu!");
                        }
                        break;
                    
                    default:
                        break;
                    }
                }
            }
        }
    }

    public boolean isSpeciesAndGroundTypeValid(Engimon engimon, Tile tile){
        if((tile.getType() == TileType.Mountain && engimon.getElements().contains(Element.FIRE)) ||
            (tile.getType() == TileType.Sea && engimon.getElements().contains(Element.WATER)) || 
            (tile.getType() == TileType.Grassland && (engimon.getElements().contains(Element.GROUND) || engimon.getElements().contains(Element.ELECTRIC))) ||
            (tile.getType() == TileType.Tundra && engimon.getElements().contains(Element.ICE))){
                return true;
        }

        else return false;
    }

    public void setLevelCapslock(int _lvlCapslock){
        lvlCapslock = _lvlCapslock;
    }

    public void decrementWildEngimon(){
        nWiildEngimon--;
    }

    public ArrayList<Tile> getTilesWithEngimonAroundPlayer(){
        ArrayList<Tile> tilesWithEngimon = new ArrayList<Tile>();
        int x = playerPosition.getX();
        int y = playerPosition.getY();

        if(y != 0 && matriksPeta.get(y-1).get(x).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y-1).get(x));
        if(y != length - 1 && matriksPeta.get(y+1).get(x).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y+1).get(x));
        if(x != 0 && matriksPeta.get(y).get(x-1).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y).get(x-1));
        if(x != width - 1 && matriksPeta.get(y).get(x+1).containWildEngimon()) tilesWithEngimon.add(matriksPeta.get(y).get(x+1));
        
        return tilesWithEngimon;
    }

    public void incrementEngimonsAge(){
        for (ArrayList<Tile> arrayList : matriksPeta) {
            for (Tile tile : arrayList) {
                if(tile.containWildEngimon()){
                    tile.incrementWildEngimonAge();
                    if(tile.getWildEngimonAge() % 5 == 0){
                        tile.getWildEngimon().addCum_exp(100);
                        if (tile.getWildEngimon().getCumExp() > 4000) {
                            tile.nullifyWildEngimon();
                            nWiildEngimon--;
                        }
                    }
                }
            }
        }
    }

    public void initializeEngimonSymbol(){
        engimonSymbol = new HashMap<ArrayList<Element>, Character>();

        ArrayList<Element> temp = new ArrayList<Element>();
        temp.add(Element.WATER);
        // temp.add(Element.NONE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'w');

        temp.clear();
        temp.add(Element.ICE);
        // temp.add(Element.NONE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'i');
        
        temp.clear();
        temp.add(Element.FIRE);
        // temp.add(Element.NONE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'f');
        
        temp.clear();
        temp.add(Element.GROUND);
        // temp.add(Element.NONE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'g');

        temp.clear();
        temp.add(Element.ELECTRIC);
        // temp.add(Element.NONE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'e');
        
        temp.clear();
        temp.add(Element.FIRE);
        temp.add(Element.ELECTRIC);
        engimonSymbol.put(new ArrayList<Element>(temp), 'l');

        
        temp.clear();
        temp.add(Element.ICE);
        temp.add(Element.WATER);
        engimonSymbol.put(new ArrayList<Element>(temp), 's');

        
        temp.clear();
        temp.add(Element.WATER);
        temp.add(Element.GROUND);
        engimonSymbol.put(new ArrayList<Element>(temp), 'n');

        temp.clear();
        temp.add(Element.ELECTRIC);
        temp.add(Element.FIRE);
        engimonSymbol.put(new ArrayList<Element>(temp), 'l');
        
        temp.clear();
        temp.add(Element.WATER);
        temp.add(Element.ICE);
        engimonSymbol.put(new ArrayList<Element>(temp), 's');

        temp.clear();
        temp.add(Element.GROUND);
        temp.add(Element.WATER);
        engimonSymbol.put(new ArrayList<Element>(temp), 'n');
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public Tile getTile(int i, int j) {
        return matriksPeta.get(i).get(j);
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Position getActiveEngiPosition() {
        return activeEngimonPosition;
    }
}