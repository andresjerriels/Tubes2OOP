package Player;

import Engimon.*;
import Skill.*;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {
     //atribut
     protected Inventory<Engimon> inventoryEngimon;
     protected Inventory<SkillItem> inventorySkill;
     protected Engimon activeEngimon;
     protected final int MaxCapacity = 30;

     //constructor
     public Player(String starter_name, int species) {
          Engimon starterEngimon;
          try {
               inventoryEngimon = new Inventory<Engimon>();
               inventorySkill = new Inventory<SkillItem>();
               starterEngimon = EngimonFactory.createEngimon(starter_name, species);
               inventoryEngimon.addToInventory(starterEngimon);
               this.activeEngimon = null;
          } catch (Exception e) {
               e.printStackTrace();
          }
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

     public void setActiveEngimon(int index) {
          this.activeEngimon = inventoryEngimon.getContainer().get(index);
     }

     public void setActiveEngimonNull() {
          this.activeEngimon = null;
     }

     public int getMaxCapacity() {
          return this.MaxCapacity;
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
               // int index = 0;
               // while (index < inventoryEngimon.getContainer().size() && 
               //      (engi.getElements().get(0) != inventoryEngimon.getContainer().get(index).getElements().get(0) ||
               //      engi.getElements().get(1) != inventoryEngimon.getContainer().get(index).getElements().get(1))) {
               //      index++;
               // }
               // //index sudah berada di tempat yg elemennya sama / akhir container
               // if (index != inventoryEngimon.getContainer().size()) {
               //      while (index < inventoryEngimon.getContainer().size() && engi.getLevel() <= inventoryEngimon.getContainer().get(index).getLevel() && 
               //      (engi.getElements().get(0) == inventoryEngimon.getContainer().get(index).getElements().get(0) &&
               //      engi.getElements().get(1) == inventoryEngimon.getContainer().get(index).getElements().get(1))) {
               //           index++;
               //      }
               //      if (index < inventoryEngimon.getContainer().size()) {
               //           //masukkan di indeks index
               //           inventoryEngimon.getContainer().add(index, engi);
               //      }
               //      else {
               //           inventoryEngimon.getContainer().add(engi);
               //      }
               // }
               // //else element terakhir tidak perlu ditambah lagi indeksnya
               // else {
               //      inventoryEngimon.getContainer().add(engi);
               // }
               inventoryEngimon.addToInventory(engi);
               Collections.sort(inventoryEngimon.getContainer());
          }
          else {
               System.out.println("Inventory Full!"); //<==== , masih belum tau gimaana printnya
          }
     }

     public void addToInvSkill(SkillItem _skill) {
          int indexFound = searchSkillItemInInventory(_skill.getSkill().getName());
          if (indexFound == -1) { //kalo ga ketemu
               if (!isInventoryFull()) {
                    int index = 0;
                    while (index < inventorySkill.getContainer().size() && _skill.getSkill().getBasePower() <= inventorySkill.getContainer().get(index).getSkill().getBasePower()) {
                         index++;
                    }
                    if (index < inventorySkill.getContainer().size()) {
                         inventorySkill.getContainer().add(index, _skill);
                    } else {
                         inventorySkill.getContainer().add(_skill);
                    }
               }
               else {
                    //{Util.printFormatKiri("Inventory Full!");} <==== , masih belum tau gimaana printnya
               }
          }
          else { //kalo ketemu
               inventorySkill.getContainer().get(indexFound).incrementItemAmount();
          }
     }

     public int searchSkillItemInInventory(String skillName) {
          int index = 0;
          boolean found = false;
          while (!found && index < inventorySkill.getContainer().size()) {
               if (skillName.equals(inventorySkill.getContainer().get(index).getSkill().getName())) {
                    found = true;
               }
               else {
                    index++;
               }
          }
          if (!found) {
               return -1; //kalo ga ketemu
          }
          return index;
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

     public void renameEngimon(int idx, String name) {
          inventoryEngimon.getContainer().get(idx).setName(name);
     }

     public void removeEngimonByIndex(int idx) {
          inventoryEngimon.removeByIndex(idx);
     }

     public void removeSkillByIndex(int idx) {
          inventorySkill.removeByIndex(idx);
     }

     public void removeNSkill(int idx, int jumlah) throws Exception{
          if (jumlah <= inventorySkill.getContainer().get(idx).getItemAmount()) {
               if (jumlah < inventorySkill.getContainer().get(idx).getItemAmount()) {
                    inventorySkill.getContainer().get(idx).setAmount(inventorySkill.getContainer().get(idx).getItemAmount() - jumlah);
               }
               else {
                    removeSkillByIndex(idx);
               }
          }
          else {
               throw new Exception("Jumlah item tidak cukup");
          }
     }

     public void openEngimonInventory() {
          System.out.println("Your Engimon(s):");
          openInventory(inventoryEngimon);
     }

     public void openSkillInventory() {
          System.out.println("Your Skill(s):");
          openInventory(inventorySkill);
     }

     public int getMaxEngiLevel(){
          int max = 0;

          for (Engimon engi : inventoryEngimon.getContainer()) {
               if(engi.getLevel() > max) max = engi.getLevel();
          }

          return max;
     }

     private void openInventory(Inventory<? extends InventoryItem> inv) {
          Scanner sc = new Scanner(System.in);
          String cmd;
          do {
               inv.printInventory();
               System.out.println("- To see details, select a number");
               System.out.println("- To close inventory, enter 'close'");
               System.out.println("What do you want to do?");
               System.out.print("* ");
               cmd = sc.nextLine();

               if (!cmd.equals("close")) {
                    int i = Integer.parseInt(cmd);
                    if(1 <= i && i <= inv.getContainer().size()) inv.getContainer().get(i-1).printDetails();
                    else System.out.println("Number invalid");
               }
          } while (!cmd.equals("close"));
     }


     public Engimon getEngiRefFromIndex(int i) throws InvalidIndexInventory {
          if ( 0 <= i && i < inventoryEngimon.countItemInInventory()) {
               return inventoryEngimon.getContainer().get(i);
          } else {
               throw new InvalidIndexInventory();
          }
     }

     public SkillItem getSkillRefFromIndex(int i) throws InvalidIndexInventory {
          if ( 0 <= i && i < inventorySkill.countItemInInventory()) {
               return inventorySkill.getContainer().get(i);
          } else {
               throw new InvalidIndexInventory();
          }
     }
}
