import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Cell[][] dataArray;

    public Window(Cell[][] dataArray, int length, int width) {
        this.dataArray = dataArray;
        setTitle("Color Grid");
        setSize(width, length);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel gridPanel = new JPanel(new GridLayout(dataArray.length, dataArray[0].length));

        // Create colored labels for each cell in the array
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setPreferredSize(new Dimension(40, 40)); // Set preferred size for each cell
                label.setBackground(getColorForValue(dataArray[i][j].getValue()));
                gridPanel.add(label);
            }
        }

        add(gridPanel);
        setVisible(true);
    }

    // Method to map integer values to colors
    private Color getColorForValue(int value) {
        switch (value) {
            case 1:
                return new Color(255, 0, 0);
            case 2:
                return new Color(255, 140, 0); // Red Orange
            case 3:
                return Color.ORANGE;
            case 4:
                return Color.YELLOW;
            case 5:
                return new Color(0, 255, 0);
            case 6:
                return new Color(0, 0, 255);
            case 7:
                return new Color(0, 128, 128); // Teal
            case 8:
                return new Color(255, 0, 255); // Light Purple
            case 9:
                return new Color(120, 0, 255); // Dark Purple
            default:
                return Color.WHITE;
        }
    }

    public static void createWindow(Cell[][] arr, int length, int width) {
        // Example 2D array
        SwingUtilities.invokeLater(() -> new Window(arr, length, width));
    }
}
