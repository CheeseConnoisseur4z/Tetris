package tom.Tetris;

import java.util.concurrent.TimeUnit;

/*
speed * MILLISECOND;
calls function that makes shapes fall;
 */
public class MoveThread extends Thread
{
    BlockMovement blockMovement;
    CountThread countThread;


    public MoveThread(BlockMovement blockMovement, CountThread countThread) {
        this.blockMovement = blockMovement;
        this.countThread = countThread;
        this.start();
    }

    @Override
    public void run() {
        while (Thread.currentThread().isAlive()) {
            try {
                TimeUnit.MILLISECONDS.sleep(countThread.speed);
                blockMovement.initiateMove(0, -1);
            } catch (Exception ignored) {}
        }
    }
}

