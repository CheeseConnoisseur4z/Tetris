package tom.Tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardControls
{
    BlockMovement blockMovement;
    TetrisGraphics tetrisGraphics;
    CountThread countThread;

    int saveSpeed;
    Set<Integer> keysDown = new HashSet<>();


    public KeyboardControls(BlockMovement blockMovement, TetrisGraphics tetrisGraphics, CountThread countThread) {
        this.blockMovement = blockMovement;
        this.tetrisGraphics = tetrisGraphics;
        this.countThread = countThread;

        addKeyPressed();
        addKeyReleased();
    }


    /*
    adds key to HashSet;
     */
    public void addKeyPressed() {
        this.tetrisGraphics.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysDown.add(e.getKeyCode());

                if (!keysDown.isEmpty()) {
                    for (Integer i : keysDown) {
                        switch (i) {
                            case KeyEvent.VK_A:
                                moveHorizontal(-1);
                                break;
                            case KeyEvent.VK_D:
                                moveHorizontal(1);
                                break;
                            case KeyEvent.VK_S:
                                fastFall();
                                break;
                            case KeyEvent.VK_RIGHT:
                                rotate(1);
                                break;
                            case KeyEvent.VK_LEFT:
                                rotate(-1);
                                break;
                            case KeyEvent.VK_SPACE:
                                instantFall();
                                break;
                        }
                    }
                }
            }
        });
    }

    /*
    removes key from HashSet;
     */
    public void addKeyReleased() {
        this.tetrisGraphics.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                keysDown.remove(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_S) endFastFall();
            }
        });
    }


    public void rotate(int dir) {
        if (blockMovement.current.type.equals("O")) return;
        if (blockMovement.checkRotation(dir)) {
            blockMovement.current.rotation = (4 - blockMovement.current.rotation + dir) % 4;
            blockMovement.paintShape(0, 0, true);
        }
    }


    public void instantFall() {
        if (!blockMovement.endInstantFall) return;
        blockMovement.endInstantFall = false;
        while (!blockMovement.endInstantFall) {
            blockMovement.initiateMove(0, -1);
        }
    }


    public void moveHorizontal(int dir) {
        blockMovement.initiateMove(dir, 0);
    }


    public void fastFall() {
        if (countThread.speed > 50) saveSpeed = countThread.speed;
        countThread.speed = 50;
    }

    public void endFastFall() {
        countThread.speed = saveSpeed;
    }
}