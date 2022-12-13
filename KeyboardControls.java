package tom.Tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class KeyboardControls
{
    BlockMovement blockMovement;
    GraphicComponents graphicComponents;
    CountThread countThread;

    int saveSpeed;
    Set<Integer> keysDown = new HashSet<>();

    public KeyboardControls(BlockMovement blockMovement, GraphicComponents graphicComponents, CountThread countThread) {
        this.blockMovement = blockMovement;
        this.graphicComponents = graphicComponents;
        this.countThread = countThread;

        addKeyPressed();
        addKeyReleased();
    }

    public void addKeyPressed() {
        this.graphicComponents.frame.addKeyListener(new KeyAdapter() {
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
                        }
                    }
                }
            }
        });
    }

    public void addKeyReleased() {
        this.graphicComponents.frame.addKeyListener(new KeyAdapter() {
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
            blockMovement.current.rotation = Math.abs((blockMovement.current.rotation + dir) % 4);
            blockMovement.paintShape(0, 0, true);
        }
    }

    public void moveHorizontal(int dir) {
        blockMovement.moveHorizontal(dir);
    }

    public void fastFall() {
        if (countThread.speed > 50) saveSpeed = countThread.speed;
        countThread.speed = 50;
    }

    public void endFastFall() {
        countThread.speed = saveSpeed;
    }
}
