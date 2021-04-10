// package Map;
import java.util.*;
import java.io.*;

public class Peta{
    public static void main(String args[]){
        Peta peta = new Peta("../../files/map.txt");
        peta.printPeta();
    }

    private ArrayList<ArrayList<Tile>> matriksPeta;

    public Peta(String path){
        matriksPeta = new ArrayList<ArrayList<Tile>>();

        try{
            File filePeta = new File(path);
            Scanner fileScanner = new Scanner(filePeta);
            while(fileScanner.hasNextLine()){
                ArrayList<Tile> rowPeta = new ArrayList<Tile>();

                String line = fileScanner.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    Tile tile = new Tile(line.charAt(i));
                    rowPeta.add(tile);
                }
                matriksPeta.add(rowPeta);
            }    
            
            fileScanner.close();
        } catch(FileNotFoundException e){
            System.out.println("");
            e.printStackTrace();
        }
    }

    public void printPeta(){
        for (ArrayList<Tile> row : matriksPeta) {
            for (Tile tile : row) {
                System.out.print(tile.GetSymbol() + " ");
            }
            System.out.println();
        }
    }
}