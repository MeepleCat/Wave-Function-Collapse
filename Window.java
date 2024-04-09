import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private Cell[][] dataArray;
    
    private Color[] colors = {
        new Color(255, 0, 0), // red
        new Color(255, 140, 0), // redOrange
        Color.ORANGE, // orange
        new Color(255, 255, 0), // yellow
        new Color(0, 255, 0), // green
        new Color(0, 192, 192), // teal
        new Color(0, 128, 255), // blue
        new Color(153, 102, 255), // lightPurple
        new Color(120, 0, 255), // purple
        new Color(255, 255, 255) // white
    };

    public Window(Cell[][] arr, int length, int width) {
        dataArray = arr;
        setTitle("Color Grid");
        setSize(width * 4, length * 4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        JPanel gridPanel = new JPanel(new GridLayout(dataArray.length, dataArray[0].length));
        add(gridPanel);

        int cycles = 0; 
        int counted = 0;

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                JLabel label = new JLabel();
                label.setOpaque(true);
                label.setBackground(colors[dataArray[i][j].getValue()-1]);
                gridPanel.add(label);
                cycles++;
                if((cycles - (counted * length * width / 100)) >= length * width / 100) {
                    counted++; 
                    System.out.println(counted + "% Done Assembling Image...");
                } 
            } 
        }
    }

    public static void createWindow(Cell[][] arr, int length, int width) {
        SwingUtilities.invokeLater(() -> new Window(arr, length, width));
    }
}
