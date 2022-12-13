package tom.Tetris;

import java.util.concurrent.TimeUnit;

/*
speed = 1 second;
increases speed of MoveThread;
 */
public class CountThread extends Thread
{
    int speed;


    public CountThread(int speed) {
        this.speed = speed;
        this.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                this.speed--;
            } catch (Exception ignored) {
            }
        }
    }
}
