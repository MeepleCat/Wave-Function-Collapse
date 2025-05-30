import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;

public class Grid {
    private int length;
    private int width;
    private Cell[][] cells;
    private XMLObject rules;
    private HashMap<Integer, Color> colors = new HashMap<>();
    
    public Grid(int len, int wid, String path) { 
        length = len;
        width = wid;

        rules = new XMLObject(path);
        //System.out.println(rules);
        Element[] items = rules.searchByName("items").getChildren();
        
        int[] v = new int[items.length];
        for(int i = 0; i < items.length; i++) {
            Element e = items[i];
            HashMap<String, String> tags = e.tags();
            String id = tags.get("id");
            v[i] = Integer.parseInt(id);
        }

        System.out.println("the ids of the values being used are: " + Arrays.toString(v));

        cells = new Cell[len][wid];

        for(int i : v) {
            int red = Integer.parseInt(rules.searchByTag("id", Integer.toString(i)).searchByName("red").getValue());
            int green = Integer.parseInt(rules.searchByTag("id", Integer.toString(i)).searchByName("green").getValue());
            int blue = Integer.parseInt(rules.searchByTag("id", Integer.toString(i)).searchByName("blue").getValue());
            colors.put(i, new Color(red, green, blue));
        }
        
        for(int r = 0; r < len; r++) { 
            for(int c = 0; c < wid; c++) {
                cells[r][c] = new Cell(r, c, v.clone());
            } 
        }
    }
    
    public Grid(int len, String path) { 
        this(len, len, path);
    }
    
    public void collapse(int t) {
        int cycles = 0; 
        int counted = 0;
        boolean done = false;

        if(t == -1) {
            t = length * width;
        }

        while(cycles < t && !done) {
            Cell cell = findLowestEntropy();
            int value = cell.collapse();
            cycles++;
            
            if(cycles == length * width) { 
                done = true;
            }

            int row = cell.getRow();
            int col = cell.getCol();

            if(value != 10013) {

            //System.out.println("row: " + row + " col: " + col + " value: " + value);
            //System.out.println(rules.value(rules.searchByTag("id", String.valueOf(value)).searchByName("top")));
            
                if(row != 0) {
                    //System.out.println(cells[row - 1][col]);
                    //System.out.println(cells[row - 1][col].getValues());
                    //System.out.println("Top:");
                    RestrictPossibilities.collapse(cells[row - 1][col], rules.value(rules.searchByTag("id", String.valueOf(value)).searchByName("top"))); 
                }
                
                if(row != length - 1) {
                    //System.out.println(cells[row + 1][col].getValues());
                    //System.out.println("Bottom:");
                    RestrictPossibilities.collapse(cells[row + 1][col], rules.value(rules.searchByTag("id", String.valueOf(value)).searchByName("bottom")));
                }
                
                if(col != 0) {
                    //System.out.println(cells[row][col - 1].getValues());
                    //System.out.println("Left:");
                    RestrictPossibilities.collapse(cells[row][col - 1], rules.value(rules.searchByTag("id", String.valueOf(value)).searchByName("left")));
                }
                
                if(col != width - 1) {
                    //System.out.println(cells[row][col + 1].getValues());
                    //System.out.println("Right:");
                    RestrictPossibilities.collapse(cells[row][col + 1], rules.value(rules.searchByTag("id", String.valueOf(value)).searchByName("right")));
                }                
            }

            if((cycles - (counted * t / 100)) >= t / 100 + 9) {
                counted+=10; 
                System.out.println(counted + "% Done Processing...");
            } 
        }
    }
    
    public Cell findLowestEntropy() { 
        List<Integer> lowestRows = new ArrayList<Integer>(); 
        List<Integer> lowestCols = new ArrayList<Integer>(); 
        int lowest = 10; 

        for(int r = 0; r < length; r++) { 
            for(int c = 0; c < width; c++) { 
                if(cells[r][c].findEntropy() < lowest && !cells[r][c].isCollapsed()) { 
                    lowestRows.clear();
                    lowestCols.clear();
                    lowest = cells[r][c].findEntropy();
                }
                if(cells[r][c].findEntropy() == lowest && !cells[r][c].isCollapsed()) {
                    lowestRows.add(r);
                    lowestCols.add(c);                    
                }
            }
        }
        
        int random = (int)(Math.random() * lowestRows.size());
        Cell cell = cells[lowestRows.get(random)][lowestCols.get(random)]; 
        //System.out.println(cell.getRow() + ", " + cell.getCol() + " had the lowest entropy");
        
        return cell;
    } 
    
    public void display() {
        // new Window(cells, length, width);
        //dump(); 
        SwingUtilities.invokeLater(() -> new Window(cells, length, width, colors));
    }

    public void printUnicode() {
        for(Cell[] arr : cells) {
            for(Cell c : arr) {
                String s = "";
                switch(c.getValue()) {
                    case 1: s = "\u2648"; break;
                    case 2: s = "\u2649"; break;
                    case 3: s = "\u264A"; break;
                    case 4: s = "\u264B"; break;
                    case 5: s = "\u264C"; break;
                    case 6: s = "\u264E"; break;
                    case 7: s = "\u264F"; break;
                    case 8: s = "\u2650"; break;
                    case 9: s = "\u2652"; break;
                    case 10: s = "\u26AB"; break;
                }
                System.out.print(s);
            }
            System.out.println();
        }
    }

    /* public void dump() {
        // List<String> lines = Arrays.asList();
        //String[][] lines = new String[10][10];
        

        Path path = Paths.get("data.txt");

        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    } */
}