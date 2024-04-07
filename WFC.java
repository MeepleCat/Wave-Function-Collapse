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

    public static void solveWithAspectRatio(int length) {
        Grid g = new Grid(length * 9 / 16, length);
        g.collapse(-1);
        g.display();
    }
    
    public static void main(String[] args) {
        //WFC.solve(40, 80);
        WFC.solveWithAspectRatio(100);
        /* int length = 100;
        int width = 200;
        int frames = 1;
        Grid grid = new Grid(length, width); 

        for(int i = 0; i < frames; i++) {
            grid.collapse(length * width / frames);
            grid.display();
        } */
    }
}