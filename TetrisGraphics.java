package tom.Tetris;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TetrisGraphics
{
    JFrame frame;
    JPanel[][] grid;
    private JLabel pointsDisplay;
    AtomicInteger points = new AtomicInteger();


    public TetrisGraphics() {
        createFrame();
        createPointsDisplay();
        createGrid();
    }


    private void createFrame() {
        frame = new JFrame("TETRIS");
        frame.setSize(365, 665);
        frame.setVisible(true);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }


    private void createPointsDisplay() {
        pointsDisplay = new JLabel();
        pointsDisplay.setBounds(0, 0, 400, 30);
        pointsDisplay.setFont(new Font("points display font", Font.BOLD, 30));
        pointsDisplay.setForeground(Color.white);
        pointsDisplay.setText("POINTS : " + points);
        frame.add(pointsDisplay);
    }


    public void setPoints() {
        pointsDisplay.setText("POINTS : " + points);
    }


    private void createGrid() {
        JPanel border = new JPanel();
        border.setBounds(0, 30, 350, 650);
        border.setBackground(Color.black);
        border.setBorder(BorderFactory.createLineBorder(Color.white, 25));

        JPanel temp;
        grid = new JPanel[10][21];
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 20; y++) {
                temp = new JPanel();
                temp.setBounds(25 + x * 30, 30 + 570 - y * 30, 31, 31);
                temp.setBackground(Color.white);
                temp.setVisible(false);
                frame.add(temp);
                grid[x][y] = temp;
            }
        }

        frame.add(border);
    }
}
