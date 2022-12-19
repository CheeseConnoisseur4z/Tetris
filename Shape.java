package tom.Tetris;

import java.util.ArrayList;
import java.util.List;

public class Shape
{
    BlockMovement blockMovement;
    int[][][] bounds = new int[4][4][2];
    int[] position;
    List<int[]> positions = new ArrayList<>();
    int rotation;
    final String type;


    public Shape(String type, BlockMovement blockMovement, int[] position, int rotation) {
        this.type = type;
        this.blockMovement = blockMovement;
        this.position = position;
        this.rotation = rotation;

        switch (type) {
            case "T":
                createBounds(createPos(1, 0), createPos(-1, 0), createPos(0, 1));
                break;
            case "I":
                createBounds(createPos(-1, 0), createPos(1, 0), createPos(2, 0));
                break;
            case "O":
                createBounds(createPos(1, 0), createPos(0, 1), createPos(1, 1));
                break;
            case "S":
                createBounds(createPos(-1, 0), createPos(0, 1), createPos(1, 1));
                break;
            case "Z":
                createBounds(createPos(1, 0), createPos(0, 1), createPos(-1, 1));
                break;
            case "L":
                createBounds(createPos(-1, 0), createPos(1, 0), createPos(1, 1));
                break;
            case "J":
                createBounds(createPos(1, 0), createPos(-1, 0), createPos(-1, 1));
                break;
        }
    }


    public void updatePositions() {
        positions.clear();
        for (int[] offset : bounds[rotation]) {
            positions.add(new int[]{position[0] + offset[0], position[1] + offset[1]});
        }
    }


    public int[][] getBounds(int[] pos1, int[] pos2, int[] pos3) {
        int[][] dirBounds = new int[4][2];
        dirBounds[0] = new int[]{0, 0};
        dirBounds[1] = pos1;
        dirBounds[2] = pos2;
        dirBounds[3] = pos3;
        return dirBounds;
    }

    /*
    creates base position;
    rotates base position -> put into an array;
     */
    private void createBounds(int[] pos1, int[] pos2, int[] pos3) {
        bounds[0] = getBounds(pos1, pos2, pos3);
        int c;
        for (int i = 1; i < 4; i++) {
            c = 0;
            for (int[] point : bounds[i - 1]) {
                bounds[i][c] = new int[]{point[1], -point[0]};
                c++;
            }
        }
    }


    private int[] createPos(int x, int y) {
        return new int[]{x, y};
    }
}