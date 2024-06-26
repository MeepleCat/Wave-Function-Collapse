import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;

public class Grid {
    private int length;
    private int width;
    private Cell[][] cells;
    
    public Grid(int len, int wid) { 
        length = len;
        width = wid;
        
        cells = new Cell[len][wid];
        
        for(int r = 0; r < len; r++) { 
            for(int c = 0; c < wid; c++) {
                cells[r][c] = new Cell(r, c);
            } 
        }
        
        // System.out.println("NO ERRORS LETS GOOOOOO");
    }
    
    public Grid(int len) { 
        length = len;
        width = len;
        
        cells = new Cell[len][len];
        
        for(int r = 0; r < len; r++) { 
            for(int c = 0; c < len; c++) {
                cells[r][c] = new Cell(r, c);
            } 
        }
        
        // System.out.println("NO ERRORS LETS GOOOOOO");
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
            switch(value) {
                case 1:
                    cells = Value1.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;
                case 2:
                    cells = Value2.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 3:
                    cells = Value3.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 4:
                    cells = Value4.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 5:
                    cells = Value5.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 6:
                    cells = Value6.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 7:
                    cells = Value7.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 8:
                    cells = Value8.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                case 9:
                    cells = Value9.collapse(cells, cell.getRow(), cell.getCol(), length, width);
                    break;                
                default: 
                    //throw new RuntimeException("ERROR-- VALUE " + value + " IS NOT WITHIN RANGE");
            }

            if((cycles - (counted * t / 100)) >= t / 100) {
                counted++; 
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
        dump(); 
        // SwingUtilities.invokeLater(() -> new Window(cells, length, width));
    }

    public void dump() {
        // List<String> lines = Arrays.asList();
        //String[][] lines = new String[10][10];
        

        Path path = Paths.get("data.txt");

        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
}