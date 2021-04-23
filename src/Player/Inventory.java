package Player;
import java.util.*;

public class Inventory<T> {
     //atribut
     protected ArrayList<T> container;

     public Inventory() {
          container = new ArrayList<T>();
     }

     public ArrayList<T> getContainer() {
          return container;
     }

     public void addToInventory(T item) {
          container.add(item);
     }

     public int countItemInInventory() {
          return container.size();
     }

     public int searchIndexItemInInventory(T item) {
          int i = 0;
          boolean found = false;

          while (!found && i != container.size()) {
               if (item == container.get(i)) {
                    found = true;
               }
               else {
                    i++;
               }
          }

          if (found) {
               return i;
          } else {
               return -1;
          }
     }

     public void removeFromInventory(T item) {
          container.remove(item);
     }

     public void removeByIndex(int idx) {
          container.remove(idx);
     }

     public void printInventory() {
          if (container.isEmpty()) 
          {
               System.out.println("Empty");
          }
          else {
               for (int i = 0; i < container.size(); i++) {
                    System.out.print("* ");
                    System.out.println((i+1) + ". "+ container.get(i));
               }
          }
     }
}
