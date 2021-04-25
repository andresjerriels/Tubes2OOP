package Player;

public class InvalidIndexInventory extends Exception {
     private String errorMessage;
     public InvalidIndexInventory() {
          super();
          errorMessage = "Index out of range";
     }
     public String getErrorMessage() {
          return errorMessage;
     }
}
