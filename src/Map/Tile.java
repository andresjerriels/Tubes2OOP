package Map;
import java.io.Serializable;

import Engimon.*;

enum TileType {Mountain, Sea, Grassland, Tundra};

public class Tile implements Serializable {
    private TileType type;
    private Engimon wildEngimon;
    private int wildEngimonAge;

    public Tile(char symbol){
        if(symbol == '+') this.type = TileType.Mountain;
        else if(symbol == 'o') this.type = TileType.Sea;
        else if(symbol == '-') this.type = TileType.Grassland;
        else if(symbol == '#') this.type = TileType.Tundra;

        wildEngimon = null;
        wildEngimonAge = 0;
    }

    public char GetSymbol(){
        if(type == TileType.Mountain) return '+';
        else if(type == TileType.Sea) return 'o';
        else if(type == TileType.Grassland) return '-';
        else return '#';
    }

    public boolean containWildEngimon(){
        return wildEngimon != null;
    }

    public TileType getType(){
        return type;
    }

    public void nullifyWildEngimon(){
        wildEngimon = null;
        wildEngimonAge = 0;
    }

    public void insertWildEngimon(Engimon _wildEngimon){
        wildEngimon = _wildEngimon;
    }

    public Engimon getWildEngimon(){
        return wildEngimon;
    }

    public int getWildEngimonAge() {
        return wildEngimonAge;
    }

    public void incrementWildEngimonAge(){
        wildEngimonAge++;
    }

    public void setWildEngimon(Tile other) {
        this.wildEngimon = other.getWildEngimon();
        this.wildEngimonAge = other.getWildEngimonAge();
        other.moveWildEngimon();
    }

    public void setWildEngimon(Engimon engimon) {
        this.wildEngimon = engimon;
        this.wildEngimonAge = 0;
    }

    public void moveWildEngimon(){
        wildEngimon = null;
        wildEngimonAge = 0;
    }
}
