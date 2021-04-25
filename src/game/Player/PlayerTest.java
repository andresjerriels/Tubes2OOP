package game.Player;
import game.Engimon.*;

public class PlayerTest {
     public static void main(String[] args) {
          Player playa = new Player("Redd",1);
          try {
               Engimon a = EngimonFactory.createEngimon("aa", 0);
               Engimon b = EngimonFactory.createEngimon("bb", 2);
               Engimon c = EngimonFactory.createEngimon("cc", 4);
               Engimon e = EngimonFactory.createEngimon("ee", 4);
               Engimon d = EngimonFactory.createEngimon("dd", 5);
               playa.addToInvEngimon(a);
               playa.addToInvEngimon(b);
               playa.addToInvEngimon(c);
               playa.addToInvEngimon(d);
               playa.addToInvEngimon(e);
          } catch (Exception e) {
               //TODO: handle exception
               e.fillInStackTrace();
          }
          
          playa.openEngimonInventory();
     }
}
