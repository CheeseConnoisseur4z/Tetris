package tom.Tetris;

import java.util.ArrayList;
import java.util.List;

public class BlockReading
{
    private final BlockMovement blockMovement;
    private final TetrisGraphics tetrisGraphics;

    public BlockReading(BlockMovement blockMovement, TetrisGraphics tetrisGraphics) {
        this.blockMovement = blockMovement;
        this.tetrisGraphics = tetrisGraphics;
    }

    public synchronized int countBoolean() {
        int c = 0;
        for (boolean[] row : blockMovement.block) {
            for (boolean bool : row) if (bool) {
                c++;
            }
        }
        return c;
    }

    public synchronized boolean[][] copyBoolean(boolean[][] toCopy) {
        boolean[][] copied = new boolean[10][25];
        for (int i = 0; i < 10; i++) System.arraycopy(toCopy[i], 0, copied[i], 0, 25);
        return  copied;
    }


    public void clearLines() {
        int c;
        List<Integer> toClear = new ArrayList<>();

        for (int i = 19; i >= 0; i--) {
            c = 0;
            for (int i2 = 0; i2 < 10; i2++) {
                if (blockMovement.block[i2][i]) c++;
            }
            if (c == 10) toClear.add(i);
        }

        toClear.forEach(this::shiftDown);
        toClear.forEach(ignored -> tetrisGraphics.points.addAndGet(25000));
        tetrisGraphics.setPoints();
        repaint();
    }

    public void shiftDown(int row) {
        for (int i = row; i < 20; i++) {
            for (int i2 = 0; i2 < 10; i2++) blockMovement.block[i2][i] = blockMovement.block[i2][i + 1];
        }
    }

    public void repaint() {
        for (int i = 0; i < 10; i++) {
            for (int i2 = 0; i2 < 20; i2++) blockMovement.grid[i][i2].setVisible(blockMovement.block[i][i2]);
        }
    }

    public void checkGameOver() {
        for (int i = 0; i < 10; i++) {
            if (blockMovement.block[i][20]) {
                System.out.println("GAME OVER!");
                return;
            }
        }
    }
}
