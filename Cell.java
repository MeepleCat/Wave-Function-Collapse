public class Cell {
    private int row;
    private int col;
    private boolean collapsed;
    
    private int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    public Cell(int r, int c) { 
        row = r;
        col = c;
        collapsed = false; 
    } 
    
    public int findEntropy() { 
        int count = 0; 
        for(int i : values) { 
            if(i != 0) { 
                count++;
            }
        }
        
        return count;
    }
    
    public int getValue() {
        int value = 0;

        for(int i : values) { 
            if(i != 0) {
                if(value != 0) { 
                    return 0;
                }
                value = i;
            }
        }
        
        return value;
    }
    
    public int collapse() { 
        int states = 0;
        
        for(int i : values) { 
            if(i != 0) { 
                states++;
            }
        } 
        
        int random = (int)(Math.random() * states);
        int pos = 0;
        int value = 0;
        
        for(int i = 0; i < values.length; i++) { 
            if(values[i] != 0) { 
                if(pos == random) { 
                    value = values[i];
                }
                else {
                    values[i] = 0;
                }
                pos++;
            }
        } 
        
        collapsed = true; 
        
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