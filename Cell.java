import java.util.Arrays;

public class Cell {
    private int row;
    private int col;
    private boolean collapsed;
    
    private int[] values;
    
    public Cell(int r, int c, int[] v) { 
        row = r;
        col = c;
        values = v;
        collapsed = false; 

        // System.out.println(Arrays.toString(v));
    } 
    
    public int findEntropy() { 
        int count = 0; 
        for(int i : values) { 
            if(i != -1) { 
                count++;
            }
        }
        
        return count;
    }
    
    public int getValue() {
        int value = 9;

        for(int i : values) { 
            if(i != -1) {
                if(value != 9) { 
                    return 9;
                }
                value = i;
            }
        }
        
        // System.out.println(Arrays.toString(values) + "  " + value);
        return value;
    }
    
    public int collapse() { 
        int states = 0;
        
        for(int i : values) { 
            if(i != -1) { 
                states++;
            }
        } 
        
        int random = (int)(Math.random() * states);
        int pos = 0;
        int value = 0;
        
        for(int i = 0; i < values.length; i++) { 
            if(values[i] != -1) { 
                if(pos == random) { 
                    value = values[i];
                }
                else {
                    values[i] = -1;
                }
                pos++;
            }
        } 
        
        collapsed = true; 
        
        // System.out.println(Arrays.toString(values) + "  " + value);

        return value; 
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col; 
    }
    
    public boolean isCollapsed() {
        return collapsed; 
    }
    
    public int[] getValues() { 
        return values;
    }
    
    public void setValues(int[] newValues) {
        values = newValues; 
    }
    
    public void print() {
        for(int i : values) {
            System.out.print(i + " "); 
        } 
        System.out.println();
    }
    
    public String toString() { 
        String s = "";
        for(int i : values) { 
            s += i + " ";
        }
        return s; 
    } 
    
}