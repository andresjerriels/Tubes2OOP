package Engimon;

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

    public static Engimon createEngimon(String name, String species) throws Exception {
        if (speciesMap.containsKey(species)) {
            return createEngimon(name, speciesMap.get(species));
        } else {
            throw new Exception("Invalid species");
        }
    }
}
