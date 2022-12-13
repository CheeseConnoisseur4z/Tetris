package tom.Tetris;

import java.util.concurrent.TimeUnit;

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
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(countThread.speed);
                blockMovement.initiateMove(0, -1);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}


class CountThread extends Thread
{
    int speed;


    public CountThread(int speed) {
        this.speed = speed;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
                this.speed--;
            } catch (Exception e) {
                System.out.println("TimeUnit error");
            }
        }
    }
}