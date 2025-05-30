public class RestrictPossibilities {
    public static void collapse(Cell cell, String possibleNums) {
// System.out.println("//////// START COLLAPSE ////////");
// System.out.println("cell: " + cell);
// System.out.println("Entropy: " + cell.findEntropy());

        int nums = possibleNums.length() - possibleNums.replaceAll(" ", "").length() + 1;
// System.out.println("possibleNums:" + possibleNums + " nums: " + nums);
        //int nums = possibleNums.length() - possibleNums.replaceAll("[1-9]", "").length() + 1;
        int[] possible = new int[nums];

        for(int i = 0; i < nums; i++) {
          //System.out.println("\npossibleNums: \"" + possibleNums + "\"");
          //System.out.println(possibleNums.substring(0, possibleNums.indexOf(" ") + 2));

            int end = possibleNums.length(); 
            if(possibleNums.indexOf(" ") != -1) end = possibleNums.indexOf(" ");

            possible[i] = Integer.parseInt(possibleNums.substring(0, end));
            if(i != nums -1) possibleNums = possibleNums.substring(possibleNums.indexOf(" ") + 1).trim();
        }

        if(cell.findEntropy() <= 1) { 
            //System.out.println(cell.toString() + "has " + cell.findEntropy() + " values remaining\n");\
            //System.out.println("cell already solved");
// System.out.println("///////// END COLLAPSE /////////\n\n");
            return;
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
                values[i] = -1;
                //cell.setValues(values);
                //System.out.println("there are now " + cell.findEntropy() + " values remaining");
                //cell.print();
            } 
        } 
        
        // for(int i : values) System.out.print(i + " ");
        //System.out.println();
        
        cell.setValues(values);

// System.out.println("///////// END COLLAPSE /////////\n\n");
    }
}