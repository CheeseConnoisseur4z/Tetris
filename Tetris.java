package tom.Tetris;

public class Tetris extends Thread
{
    public static void main(String[] args) {
        Tetris newGame = new Tetris();
        newGame.start();
    }


    public void start() {
        TetrisGraphics tetrisGraphics = new TetrisGraphics();

        BlockMovement blockMovement = new BlockMovement(tetrisGraphics.grid);
        MoveThread moveTickThread = new MoveThread(blockMovement, new CountThread(1000));

        new KeyboardControls(blockMovement, tetrisGraphics, moveTickThread.countThread);
    }
}