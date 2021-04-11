// package Map;

enum TileType {Mountain, Sea, Grassland, Tundra};

public class Tile {
    private TileType type;
    // private Engimon wildEngimon;

    public Tile(char symbol){
        if(symbol == '+') this.type = TileType.Mountain;
        else if(symbol == 'o') this.type = TileType.Sea;
        else if(symbol == '-') this.type = TileType.Grassland;
        else if(symbol == '#') this.type = TileType.Tundra;

        // wildEngimon = null;
    }

    public char GetSymbol(){
        if(type == TileType.Mountain) return '+';
        else if(type == TileType.Sea) return 'o';
        else if(type == TileType.Grassland) return '-';
        else return '#';
    }

    public boolean containWildEngimon(){
        // return wildEngimon != null;
        return false;
    }

    public TileType getType(){
        return type;
    }

    public void nullifyWildEngimon(){
        // wildEnimon = NULL;
    }

    public void insertWildEngimon(Engimon _wildEngimon){
        wildEngimon = _wildEngimon;
    }

    public Engimon getWildEngimon(){
        return wildEngimon;
    }
}
