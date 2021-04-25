package game.Save;

import game.Map.*;
import game.Player.*;

public class SaveData implements java.io.Serializable {
     private static final long serialVersionUID = 1L;

     public Peta map;
     public Player player;
}
