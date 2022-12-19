package tom.Tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardControls
{
    private final BlockMovement blockMovement;
    private final TetrisGraphics tetrisGraphics;
    private final CountThread countThread;
    private int saveSpeed;
    private Set<Integer> keysDown = new HashSet<>();


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
    private void addKeyPressed() {
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
    private void addKeyReleased() {
        this.tetrisGraphics.frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                keysDown.remove(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_S) endFastFall();
            }
        });
    }


    private void rotate(int dir) {
        if (blockMovement.current.type.equals("O")) return;
        if (blockMovement.checkRotation(dir)) {
            blockMovement.current.rotation = (4 - blockMovement.current.rotation + dir) % 4;
            blockMovement.paintShape(0, 0, true);
        }
    }


    private void instantFall() {
        if (!blockMovement.endInstantFall) return;
        blockMovement.endInstantFall = false;
        while (!blockMovement.endInstantFall) {
            blockMovement.initiateMove(0, -1);
        }
    }


    private void moveHorizontal(int dir) {
        blockMovement.initiateMove(dir, 0);
    }


    private void fastFall() {
        if (countThread.speed > 50) saveSpeed = countThread.speed;
        countThread.speed = 50;
    }

    private void endFastFall() {
        countThread.speed = saveSpeed;
    }
}