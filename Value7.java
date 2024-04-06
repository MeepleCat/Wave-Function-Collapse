public class Value7 {
    public static int[] possibleTop = {6, 7, 8};
    public static int[] possibleBottom = {6, 7, 8};
    public static int[] possibleLeft = {6, 7, 8};
    public static int[] possibleRight = {6, 7, 8};
    
    public static Cell[][] collapse(Cell[][] cells, int row, int col, int length, int width) {
        if(row != 0) {
            Cell topCell = RestrictPossibilities.collapse(cells[row - 1][col], possibleTop); 
        }
        
        if(row != length - 1) {
            Cell bottomCell = RestrictPossibilities.collapse(cells[row + 1][col], possibleBottom);
        }
        
        if(col != 0) {
            Cell leftCell = RestrictPossibilities.collapse(cells[row][col - 1], possibleLeft);
        }
        
        if(col != width - 1) {
            Cell rightCell = RestrictPossibilities.collapse(cells[row][col + 1], possibleRight);
        }
        
        return cells;
    }
}