import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.*;
import java.util.*;

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

    public static void solveWithLog(int length) {
        long start = System.nanoTime() / 1000000;

        solveWithAspectRatio(length);

        long end = System.nanoTime() / 1000000;
        long elapsed = (end - start); 

        List<String> lines = Arrays.asList("Began execution at " + start, "Solved a grid of size " + length + ", " + length * 9 / 16 + " in " + elapsed + " ms", "Ended execution at " + end, "");
        Path path = Paths.get("log.txt");

        try {
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        solveWithLog(200 * 16 / 9);

        //WFC.solve(40, 80);
        // WFC.solveWithAspectRatio(10);
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