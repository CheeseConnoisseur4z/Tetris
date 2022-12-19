package tom.Tetris;

import java.util.concurrent.TimeUnit;

public class MoveThread extends Thread
{
    private final BlockMovement blockMovement;
    final CountThread countThread;
    private final TetrisGraphics tetrisGraphics;


    public MoveThread(BlockMovement blockMovement, CountThread countThread, TetrisGraphics tetrisGraphics) {
        this.blockMovement = blockMovement;
        this.countThread = countThread;
        this.tetrisGraphics = tetrisGraphics;
        this.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                TimeUnit.MILLISECONDS.sleep(countThread.speed);
                blockMovement.initiateMove(0, -1);
                tetrisGraphics.points.addAndGet(5);
                tetrisGraphics.setPoints();
            } catch (Exception ignored) {}
        }
    }
}

