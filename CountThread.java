package tom.Tetris;

import java.util.concurrent.TimeUnit;

/*
speed = 1 second;
increases speed of MoveThread;
 */
public class CountThread extends Thread
{
    int speed;
    TetrisGraphics tetrisGraphics;


    public CountThread(int speed, TetrisGraphics  tetrisGraphics) {
        this.speed = speed;
        this.tetrisGraphics = tetrisGraphics;
        this.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                tetrisGraphics.points.addAndGet(1000);
                tetrisGraphics.setPoints();
                this.speed -= 2;
            } catch (Exception ignored) {
            }
        }
    }
}
