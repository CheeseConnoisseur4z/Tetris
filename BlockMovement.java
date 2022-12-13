package tom.Tetris;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class BlockMovement
{
    boolean[][] block = new boolean[10][25];
    JPanel[][] grid;
    Shape current;
    String[] blockTypes = {"T", "I", "O", "S", "Z", "L", "J"};

    public BlockMovement(JPanel[][] grid) {
        this.grid = grid;
        newBlock();
    }


    /*
    checks if shape is out of range of field;
    checks if move is valid -> ...
    creates new block if conditions are met;
     */
    public synchronized void initiateMove(int dirH, int dirV) {
        for (int[] offset : current.bounds[current.rotation]) {
            if (outOfRange(current.position[0] + dirH + offset[0], current.position[1] + dirV + offset[1], 25)) {
                if (dirH == 0 && dirV == -1) newBlock();
                return;
            }
        }

        if (validateMove(dirH, dirV)) paintShape(dirH, dirV, true);
        else if (dirH == 0) newBlock();
    }


    /*
    compares 2 states of field;
    if number of bits is different move is invalid;
     */
    public boolean validateMove(int x, int y) {
        boolean[][] copyBlock;
        int compare1;
        int compare2;

        List<int[]> copyPositions = new ArrayList<>(current.positions);
        int[] copyPosition = {current.position[0], current.position[1]};

        copyBlock = copyBoolean(block);
        compare1 = countBoolean();

        paintShape(x, y, false);

        compare2 = countBoolean();
        block = copyBoolean(copyBlock);

        current.positions = copyPositions;
        current.position = copyPosition;

        return compare1 == compare2;
    }


    /*
    rotates shape and validates rotation by similar means to initiateMove();
     */
    public boolean checkRotation(int dir) {
        int rotation = current.rotation;
        current.rotation = Math.abs((current.rotation + dir) % 4);
        boolean check = true;

        for (int[] offset : current.bounds[current.rotation]) {
            if (outOfRange(current.position[0] + offset[0], current.position[1] + offset[1], 25)) {
                check = false;
                break;
            }
        }

        if (check && !validateMove(0, 0)) check = false;

        current.rotation = rotation;
        return check;
    }


    /*
    sets or clears bits of Shape's positions in the array;
    colors them if graphics = true;
     */
    public void moveOnArray(boolean set, boolean graphics) {
        current.positions.forEach(position -> {
            block[position[0]][position[1]] = set;
            if (position[1] < 21 && graphics) grid[position[0]][position[1]].setVisible(set);
        });
    }

    /*
    clears Shape's "location" on array;
    changes Shape's center's position -> sets Shape's location on array;
     */
    public void paintShape(int dirH, int dirV, boolean graphics) {
        moveOnArray(false, graphics);

        current.position[0] += dirH;
        current.position[1] += dirV;
        current.updatePositions();

        moveOnArray(true, graphics);
    }


    public int countBoolean() {
        int c = 0;
        for (boolean[] row : block) {
            for (boolean bool : row) if (bool) {
                c++;
            }
        }
        return c;
    }

    public boolean[][] copyBoolean(boolean[][] toCopy) {
        boolean[][] copied = new boolean[10][25];
        for (int i = 0; i < 10; i++) System.arraycopy(toCopy[i], 0, copied[i], 0, 25);
        return  copied;
    }


    /*
    randomly generates a new block (should be changed with probability)
     */
    public void newBlock() {
        current = new Shape(blockTypes[(int) (Math.random() * 7)], this, new int[]{5, 21}, 0);
        current.updatePositions();
        paintShape(0, 0, false);
    }


    public boolean outOfRange(int x, int y, int height) {
        return x < 0 || x >= 10 || y < 0 || y >= height;
    }
}