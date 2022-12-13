package tom.Tetris;

public class GameMain extends Thread
{
    public static void main(String[] args) {
        GraphicComponents graphicComponents = new GraphicComponents();
        BlockMovement blockMovement = new BlockMovement(graphicComponents.grid);
        MoveThread moveTickThread = new MoveThread(blockMovement, new CountThread(1000));
        new KeyboardControls(blockMovement, graphicComponents, moveTickThread.countThread);
    }
}