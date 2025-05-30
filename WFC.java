public class WFC {
    public static void solve(int length, int width, String path) {
        Grid g = new Grid(length, width, path);
        g.collapse(-1);
        g.display();
        //g.printUnicode();
    }

    public static void solveInIntervals(int length, int width, int intervals, String path) {
        Grid g = new Grid(length, width, path);
        
        for(int i = 0; i < intervals; i++) {
            g.collapse(length * width / intervals);
            g.display();
            //g.printUnicode();
            //System.out.println("-------------------------------------------------------------------------------------------------------------");
        }
    }

    public static void solveWithAspectRatio(int length, String path) {
        Grid g = new Grid(length, length * 16 / 9, path);
        g.collapse(-1);
        //g.printUnicode();
        g.display();
    }
    
    public static void main(String[] args) {
        //solveInIntervals(20, 20, 1);
        //solveInIntervals(100, 100, 5);
        //solve(150, 250);
        //solve(500, 888, "rules3.xml");
        solve(200, 355, "rules3.xml");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}