package Player;

import Engimon.*;
import Skill.*;

import java.util.*;

public class Player {
     //atribut
     protected Inventory<Engimon> inventoryEngimon;
     protected Inventory<SkillItem> inventorySkill;
     protected Engimon activeEngimon;
     protected final int MaxCapacity = 30;

     //constructor
     public Player(String starter_name, int species) {
          Engimon starterEngimon = EngimonFactory.createEngimon(starter_name, species);
          inventoryEngimon.addToInventory(starterEngimon);
          this.activeEngimon = inventoryEngimon.getContainer().get(0);
     }

     public Inventory<Engimon> getInventoryEngimon() {
          return this.inventoryEngimon;
     }

     public Inventory<SkillItem> getInventorySkill() {
          return this.inventorySkill;
     }

     public Engimon getActiveEngimon() {
          return this.activeEngimon;
     }

     public int getMaxCapacity() {
          return this.MaxCapacity;
     }

     public void setActiveEngimon(int i) {
          this.activeEngimon = inventoryEngimon.getContainer().get(i);
     }

     public boolean isInventoryFull() { //true kalau jumlah item == maxCapacity
          int count = 0;
          count += inventoryEngimon.countItemInInventory();
          
          for (SkillItem skill: inventorySkill.getContainer()) {
               count += skill.getItemAmount();
          }

          if (count >= MaxCapacity) {return true;}
          else {return false;}
     }

     public void addToInvEngimon(Engimon engi) {
          if (!isInventoryFull()) {
               int index = 0;
               while (engi.getElements().get(0) != inventoryEngimon.getContainer().get(index).getElements().get(0) || index == inventoryEngimon.getContainer().size()) {
                    index++;
               }
               //index sudah berada di tempat yg elemennya sama / akhir container
               if (index != inventoryEngimon.getContainer().size()) {
                    while (engi.getLevel() >= inventoryEngimon.getContainer().get(index).getLevel() || index == inventoryEngimon.getContainer().size()) {
                         index++;
                    }
               }
               //else element terakhir tidak perlu ditambah lagi indeksnya

               //masukkan di indeks index
               inventoryEngimon.getContainer().add(index, engi);
          }
          else {
               //{Util.printFormatKiri("Inventory Full!");} <==== , masih belum tau gimaana printnya
          }
     }

     public void addToInvSkill(String _skill) {
          if (!isInventoryFull()) {
               //nunggu skillItem dulu
               //reminder: sort by base power
          }
     }

     public void gainActiveEngimonExp(int exp) {
          activeEngimon.gainExp(exp);
     }

     public int getActiveEngiIndex() {
          int index = 0;
          while (activeEngimon != inventoryEngimon.getContainer().get(index)) {
               index++;
          }
          return index;
     }

     public void removeEngimonByIndex(int idx) {
          inventoryEngimon.removeByIndex(idx);
     }

     public void removeSkillByIndex(int idx) {
          inventorySkill.removeByIndex(idx);
     }

     public void openEngimonInventory() {
          String cmd;
          do {
               System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
               //Util::printFormatKiri("Your Engimon(s):");
               inventoryEngimon.printInventory();
               //Util::printFormatKiri("- To see details, select a number");
               //Util::printFormatKiri("- To close inventory, select 'c'");
               //Util::printFormatKiri("What do you want to do?");
               System.out.println("* ");
               System.in.read();

               if (cmd != "c") {
                    int i = Integer.parseInt(cmd);
                    if(1 <= i && i <= inventoryEngimon.getContainer().size()) inventoryEngimon.getContainer().get(i-1).printInfo();
                    else ;//Util::printFormatKiri("Number invalid");
               }
          } while (!cmd.equals("c"));
     }

     public void openSkillInventory() {
          String cmd;
          do {
               System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * *");
               //Util::printFormatKiri("Your Skill(s):");
               inventorySkill.printInventory();
               //Util::printFormatKiri("- To see details, select a number");
               //Util::printFormatKiri("- To close inventory, select 'c'");
               //Util::printFormatKiri("What do you want to do?");
               System.out.println("* ");
               System.in.read();

               if (cmd != "c") {
                    int i = Integer.parseInt(cmd);
                    if(1 <= i && i <= inventorySkill.getContainer().size()) inventorySkill.getContainer().get(i-1).printInfo();
                    else ;//Util::printFormatKiri("Number invalid");
               }
          } while (!cmd.equals("c"));
     }

     public Engimon getEngiRefFromIndex(int i) {
          if ( 0 <= i && i < inventoryEngimon.countItemInInventory()) {
               return inventoryEngimon.getContainer().get(i);
          } else {
               throw new Exception("Index out of range");
          }
     }

     public Skill getSkillRefFromIndex(int i) {
          if ( 0 <= i && i < inventorySkill.countItemInInventory()) {
               return inventorySkill.getContainer().get(i);
          } else {
               throw new Exception("Index out of range");
          }
     }
}
