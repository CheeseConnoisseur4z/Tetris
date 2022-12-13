package tom.Tetris;

import javax.swing.*;
import java.awt.*;

public class GraphicComponents
{
    JFrame frame = createFrame();
    JPanel[][] grid = getPanels(this.frame);


    public static JFrame createFrame() {
        JFrame frame = new JFrame("TETRIS");
        frame.setSize(310, 635);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        return frame;
    }


    public static JPanel[][] getPanels(JFrame frame) {
        JPanel temp;
        JPanel[][] grid = new JPanel[10][21];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 21; y++) {
                temp = new JPanel();
                temp.setBounds(x * 30, 570 - y * 30, 30, 30);
                temp.setBackground(Color.white);
                temp.setVisible(false);
                frame.add(temp);
                grid[x][y] = temp;
            }
        }
        return grid;
    }
}
