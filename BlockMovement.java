package tom.Tetris;

import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.List;

public class BlockMovement
{
    boolean[][] block = new boolean[10][25];
    final JPanel[][] grid;
    Shape current;
    private final String[] blockTypes = {"T", "I", "O", "S", "Z", "L", "J"};
    boolean endInstantFall = false;
    BlockReading blockReading;


    public BlockMovement(TetrisGraphics tetrisGraphics) {
        this.grid = tetrisGraphics.grid;
        this.blockReading = new BlockReading(this, tetrisGraphics);
        newBlock();
    }

    /*
    checks if shape is out of range of field;
    checks if move is valid -> ...
    creates new block if conditions are met;
     */
    public synchronized void initiateMove(int dirH, int dirV) {
        for (int[] offset : current.bounds[current.rotation]) {
            if (outOfRange(current.position[0] + dirH + offset[0], current.position[1] + dirV + offset[1])) {
                if (current.position[1] + offset[1] == 0) newBlock();
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
    private synchronized boolean validateMove(int x, int y) {
        boolean[][] copyBlock;
        int compare1;
        int compare2;

        List<int[]> copyPositions = new ArrayList<>(current.positions);
        int[] copyPosition = {current.position[0], current.position[1]};

        copyBlock = blockReading.copyBoolean(block);
        compare1 = blockReading.countBoolean();

        paintShape(x, y, false);

        compare2 = blockReading.countBoolean();
        block = blockReading.copyBoolean(copyBlock);

        current.positions = copyPositions;
        current.position = copyPosition;

        return compare1 == compare2;
    }


    /*
    rotates shape and validates current.rotation by similar means to initiateMove();
     */
    public synchronized boolean checkRotation(int dir) {
        int tempRotation = current.rotation;
        current.rotation  = (4 - current.rotation + dir) % 4;
        boolean check = true;

        for (int[] offset : current.bounds[current.rotation]) {
            if (outOfRange(current.position[0] + offset[0], current.position[1] + offset[1])) {
                check = false;
                break;
            }
        }

        if (check && !validateMove(0, 0)) check = false;

        current.rotation = (4 - tempRotation) % 4;
        return check;
    }


    /*
    sets or clears bits of Shape's positions in the array;
    colors them if graphics = true;
     */
    private synchronized void moveOnArray(boolean set, boolean graphics) {
        current.positions.forEach(position -> {
            block[position[0]][position[1]] = set;
            if (position[1] < 20 && graphics) grid[position[0]][position[1]].setVisible(set);
        });
    }


    /*
    clears Shape's "location" on array;
    changes Shape's center's position -> sets Shape's location on array;
     */
    public synchronized void paintShape(int dirH, int dirV, boolean graphics) {
        moveOnArray(false, graphics);

        current.position[0] += dirH;
        current.position[1] += dirV;
        current.updatePositions();

        moveOnArray(true, graphics);
    }


    public synchronized void newBlock() {
        blockReading.checkGameOver();
        current = new Shape(blockTypes[(int) (Math.random() * 7)], this, new int[]{5, 20}, 0);
        current.updatePositions();
        blockReading.clearLines();
        paintShape(0, 0, false);
        endInstantFall = true;
    }


    private boolean outOfRange(int x, int y) {
        return x < 0 || x >= 10 || y < 0 || y >= 25;
    }
}