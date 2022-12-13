package tom.Tetris;

import java.util.ArrayList;
import java.util.List;

public class Shape
{
    int[][][] bounds = new int[4][4][2];
    int rotation;
    String type;

    int[] position;
    List<int[]> positions = new ArrayList<>();

    BlockMovement blockMovement;

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

    public int[] createPos(int x, int y) {
        return new int[]{x, y};
    }

    public int[][] getBounds(int[] pos1, int[] pos2, int[] pos3) {
        int[][] dirBounds = new int[4][2];
        dirBounds[0] = new int[]{0, 0};
        dirBounds[1] = pos1;
        dirBounds[2] = pos2;
        dirBounds[3] = pos3;
        return dirBounds;
    }

    public void createBounds(int[] pos1, int[] pos2, int[] pos3) {
        int[][] rotate = {{1, -1}, {-1, -1}, {-1, 1}};
        bounds[0] = getBounds(pos1, pos2, pos3);
        int c;
        for (int i = 1; i < 4; i++) {
            c = 0;
            for (int[] point : bounds[0]) {
                bounds[i][c] = new int[]{point[1] * rotate[i - 1][0], point[0] * rotate[i - 1][1]};
                c++;
            }
        }
    }
}