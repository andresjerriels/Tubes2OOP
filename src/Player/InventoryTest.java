package Player;

public class InventoryTest {
     public static void main(String[] args) {
          Inventory<Integer> inv = new Inventory<Integer>();

          inv.printInventory();
          inv.addToInventory(1);
          inv.addToInventory(3);
          inv.addToInventory(5);
          inv.addToInventory(7);
          inv.addToInventory(9);
          inv.printInventory();
          int counter = inv.countItemInInventory();
          System.out.println("counter: "+counter);
          int var = inv.searchIndexItemInInventory(9);
          System.out.println(var);
          inv.removeFromInventory(7);
          inv.printInventory();
     }
}
