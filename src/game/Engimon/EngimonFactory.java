package game.Engimon;

import java.util.*;

public class EngimonFactory {
    public static HashMap<String, Integer> speciesMap = new HashMap<String, Integer>();
    static {
        speciesMap.put("Charmamon", 0);
        speciesMap.put("Pikamon", 1);
        speciesMap.put("Electromon", 2);
        speciesMap.put("Molemon", 3);
        speciesMap.put("Torchimon", 4);
        speciesMap.put("Dittimon", 5);
        speciesMap.put("Mamomon", 6);
        speciesMap.put("Tentamon", 7);
        speciesMap.put("Yetimon", 8);
        speciesMap.put("Tortomon", 9);
    }

    public static Engimon createEngimon(String name, int species) throws Exception {
        switch (species) {
            case 0:
                return new Charmamon(name);
            case 1:
                return new Pikamon(name);
            case 2:
                return new Electromon(name);
            case 3:
                return new Molemon(name);
            case 4:
                return new Torchimon(name);
            case 5:
                return new Dittimon(name);
            case 6:
                return new Mamomon(name);
            case 7:
                return new Tentamon(name);
            case 8:
                return new Yetimon(name);
            case 9:
                return new Tortomon(name);
            default:
                throw new Exception("Invalid species");
        }
    }

    public static Engimon createEngimon(int species) throws Exception {
        Engimon e;
        switch (species) {
            case 0:
                e = new Charmamon("Wild Charmamon");
                e.setWild();
                return e;
            case 1:
                e = new Torchimon("Wild Torchimon");
                e.setWild();
                return e;
            case 2:
                e = new Pikamon("Wild Pikamon");
                e.setWild();
                return e;
            case 3:
                e = new Electromon("Wild Electromon");
                e.setWild();
                return e;
            case 4:
                e = new Molemon("Wild Molemon");   
                e.setWild();
                return e;
            case 5:
                e = new Dittimon("Wild Dittimon");
                e.setWild();
                return e;
            case 6:
                e = new Tentamon("Wild Tentamon");
                e.setWild();
                return e;
            case 7:
                e = new Tortomon("Wild Tortomon");
                e.setWild();
                return e;
            case 8:
                e = new Yetimon("Wild Yetimon");
                e.setWild();
                return e;
            case 9:
                e = new Mamomon("Wild Mamomon");
                e.setWild();
                return e;
            default:
                throw new Exception("Invalid species");
        }
    }

    public static Engimon createEngimon(String name, String species) throws Exception {
        if (speciesMap.containsKey(species)) {
            return createEngimon(name, speciesMap.get(species));
        } else {
            throw new Exception("Invalid species");
        }
    }
}
