import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Cell[][] dataArray;
    private Color red = new Color(255, 0, 0);
    private Color redOrange = new Color(255, 140, 0);
    private Color orange = Color.ORANGE;
    private Color yellow = Color.YELLOW;
    private Color green = new Color(0, 255, 0);
    private Color blue = new Color(0, 0, 255);
    private Color teal = new Color(0, 128, 128);
    private Color lightPurple = new Color(153, 102, 255);
    private Color purple = new Color(120, 0, 255);
    private Color white = Color.WHITE;

    public Window(Cell[][] arr, int length, int width) {
        //System.out.println("TEST");
        dataArray = arr;
        setTitle("Color Grid");
        setSize(width, length);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel gridPanel = new JPanel(new GridLayout(dataArray.length, dataArray[0].length));
       
        int cycles = 0; 
        int counted = 0;

        // Create colored labels for each cell in the array
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                // label.setPreferredSize(new Dimension(40, 40)); // Set preferred size for each cell
                label.setBackground(getColorForValue(dataArray[i][j].getValue()));
                gridPanel.add(label);
                cycles++;
                if((cycles - (counted * length * width / 100)) >= length * width / 100) {
                    counted++; 
                    System.out.println(counted + "% Done Assembling Image...");
                } 
            } 
        }

        add(gridPanel);
        setVisible(true);
    }

    // Method to map integer values to colors
    private Color getColorForValue(int value) {
        //System.out.println("test2");
        switch (value) {
            case 1:
                return red;
            case 2:
                return redOrange;
            case 3:
                return orange;
            case 4:
                return yellow;
            case 5:
                return green;
            case 6:
                return blue; 
            case 7:
                return teal;
            case 8:
                return lightPurple;
            case 9:
                return purple;
            default:
                return white;
        }
    }

    public static void createWindow(Cell[][] arr, int length, int width) {
        // Example 2D array
        //System.out.println("test3");
        SwingUtilities.invokeLater(() -> new Window(arr, length, width));
    }
}
