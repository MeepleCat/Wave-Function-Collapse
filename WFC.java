public class WFC {
    public static void solve(int length, int width) {
        Grid g = new Grid(length, width);
        g.collapse(-1);
        g.display();
    }
    
    public static void solve(int length) {
        Grid g = new Grid(length);
        g.collapse(-1);
        g.display();
    }
    
    public static void main(String[] args) {
        //WFC.solve(40, 80);
        Grid grid = new Grid(100, 200); 
        grid.collapse(-1);
        grid.display();
    }
}