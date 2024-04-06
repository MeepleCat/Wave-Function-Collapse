public class RestrictPossibilities {
    public static Cell collapse(Cell cell, int[] possible) {
        if(cell.findEntropy() <= 1) { 
            //System.out.println(cell.toString() + "has " + cell.findEntropy() + " values remaining\n");
            return cell;
        } 
        
        int[] values = cell.getValues();
        // for(int i : values) System.out.print(i + " ");
        // System.out.println();
        
        for(int i = 0; i < values.length; i++) { 
            boolean passed = false;
            for(int o : possible) {
                if(values[i] == o) {
                    passed = true;

                }
            }
            
            if(!passed) { 
                //System.out.println("removed " + values[i]);
                values[i] = 0;
                cell.setValues(values);
                //System.out.println("there are now " + cell.findEntropy() + " values remaining");
                //cell.print();
            } 
        } 
        
        // for(int i : values) System.out.print(i + " ");
        //System.out.println();
        
        cell.setValues(values);
        
        return cell;
    }
}