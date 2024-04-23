package com.pokemon.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class InitMatrixController {
    private int row;
    private int col;
    private int[][] matrix;
    FrameController frame;

    public InitMatrixController(FrameController frame, int row, int col) {
        this.frame = frame;
        this.row = row;
        this.col = col;
        System.out.println(row + ", " + col);
        createMatrix();
        showMatrix();
    }

    public void shuffleMatrix() {
        Random rand = new Random();
        for(int i = row - 2; i >= 1; i--) {
            for(int j = col - 2; j >= 1; j--) {
                if(matrix[i][j] == 0) {
                    continue;
                }
                int i1 = rand.nextInt(i + 1);
                int j1 = rand.nextInt(j + 1);

                if(matrix[i1][j1] != 0) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[i1][j1];
                    matrix[i1][j1] = temp;
                }
            }
        }
    }

    public void showMatrix() {
        for(int i = 1; i < row - 1; i++) {
            for(int j = 1; j < col - 1; j++) {
                System.out.printf("%3d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void drawLine(Point p1, Point p2, Graphics g) {
        if(g == null) {
            System.out.println("Graphics context is null");
            return;
        }
        g.setColor(Color.RED);
        int x1 = p1.y * 60 + 48 / 2;
        int y1 = p1.x * 48 + 60 / 2;
        int x2 = p2.y * 60 + 48 / 2;
        int y2 = p2.x * 48 + 60 / 2;
        g.drawLine(x1, y1, x2, y2);
    }

    private boolean checkLineX(int y1, int y2, int x) {
        System.out.println("check line x");

        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        for(int y = min + 1; y < max; y++) {
            if(matrix[x][y] != 0) {
                System.out.println("die: " + x + "" + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }

        return true;
    }

    private boolean checkLineY(int x1, int x2, int y) {
        System.out.println("check line y");

        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);

        for(int x = min + 1; x < max; x++) {
            if(matrix[x][y] != 0) {
                System.out.println("die: " + x + "" + y);
                return false;
            }
            System.out.println("ok: " + x + "" + y);
        }

        return true;
    }

    private boolean checkRectX(Point p1, Point p2) {
        System.out.println("check rect x");

        Point pMiny = p1, pMaxy = p2;
        if(p1.y > p2.y) {
            pMiny = p2;
            pMaxy = p1;
        }

        for(int y = pMiny.y; y <= pMaxy.y; y++) {
            if(y > pMiny.y && matrix[pMiny.x][y] != 0) {
                return false;
            }

            if((matrix[pMaxy.x][y] == 0) && checkLineY(pMiny.x, pMaxy.x, y) && checkLineX(y, pMaxy.y, pMaxy.x)) {
                System.out.println("Rect x");
                System.out.println("(" + pMiny.x + "," + pMiny.y + ") -> ("
                        + pMiny.x + "," + y + ") -> (" + pMaxy.x + "," + y
                        + ") -> (" + pMaxy.x + "," + pMaxy.y + ")");
                
                return true;
            }
        }

        return false;
    }

    private boolean checkRectY(Point p1, Point p2) {
        System.out.println("check rect y");
        Point pMinx = p1, pMaxx = p2;
        if(p1.x > p2.x) {
            pMinx = p2;
            pMaxx = p1;
        }

        for(int x = pMinx.x; x <= pMaxx.x; x++) {
            if(x > pMinx.x && matrix[x][pMinx.y] != 0) {
                return false;
            }

            if((matrix[x][pMaxx.y] == 0) && checkLineX(pMinx.y, pMaxx.y, x) && checkLineY(x, pMaxx.x, pMaxx.y)) {
                System.out.println("Rect y");
                System.out.println("(" + pMinx.x + "," + pMinx.y + ") -> (" + x
                        + "," + pMinx.y + ") -> (" + x + "," + pMaxx.y
                        + ") -> (" + pMaxx.x + "," + pMaxx.y + ")");
                        
                return true;
            }
        }
        return false;
    }

    private boolean checkMoreLineX(Point p1, Point p2, int type) {
        System.out.println("check chec more x");
        Point pMiny = p1, pMaxy = p2;
        if(p1.y > p2.y) {
            pMiny = p2;
            pMaxy = p1;
        }
        int y = pMaxy.y + type;
        int row = pMiny.x;
        int colFinish = pMaxy.y;
        if(type == -1) {
            colFinish = pMiny.y;
            y = pMiny.y + type;
            row = pMaxy.x;
            System.out.println("colFinish = " + colFinish);
        }

        if((matrix[row][colFinish] == 0 || pMiny.y == pMaxy.y) && checkLineX(pMiny.y, pMaxy.y, row)) {
            while(matrix[pMiny.x][y] == 0 && matrix[pMaxy.x][y] == 0) {
                if(checkLineY(pMiny.x, pMaxy.x, y)) {
                    System.out.println("TH X " + type);
                    System.out.println("(" + pMiny.x + "," + pMiny.y + ") -> ("
                            + pMiny.x + "," + y + ") -> (" + pMaxy.x + "," + y
                            + ") -> (" + pMaxy.x + "," + pMaxy.y + ")");

                    return true;
                }
                y += type;
            }
        }
        return false;
    }

    private boolean checkMoreLineY(Point p1, Point p2, int type) {
        System.out.println("check more y");
        Point pMinx = p1, pMaxx = p2;
        if (p1.x > p2.x) {
            pMinx = p2;
            pMaxx = p1;
        }
        int x = pMaxx.x + type;
        int col = pMinx.y;
        int rowFinish = pMaxx.x;
        if (type == -1) {
            rowFinish = pMinx.x;
            x = pMinx.x + type;
            col = pMaxx.y;
        }
        if ((matrix[rowFinish][col] == 0|| pMinx.x == pMaxx.x)
                && checkLineY(pMinx.x, pMaxx.x, col)) {
            while (matrix[x][pMinx.y] == 0
                    && matrix[x][pMaxx.y] == 0) {
                if (checkLineX(pMinx.y, pMaxx.y, x)) {
                    System.out.println("TH Y " + type);
                    System.out.println("(" + pMinx.x + "," + pMinx.y + ") -> ("
                            + x + "," + pMinx.y + ") -> (" + x + "," + pMaxx.y
                            + ") -> (" + pMaxx.x + "," + pMaxx.y + ")");
                    return true;
                }
                x += type;
            }
        }
        return false;
    }

    public PointLine checkTwoPoint(Point p1, Point p2) {
        if(!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
            if(p1.x == p2.x) {
                System.out.println("line x");
                if(checkLineX(p1.y, p2.y, p1.x)) {
                    System.out.println("ok line x");
                    // drawLine(p1, p2, component.getGraphics());
                    return new PointLine(p1, p2);
                }
            }

            if(p1.y == p2.y) {
                System.out.println("line y");
                if(checkLineY(p1.x, p2.x, p1.y)) {
                    System.out.println("ok line y");
                    drawLine(p1, p2, null);
                    return new PointLine(p1, p2);
                }
            }

            if(checkRectX(p1, p2)) {
                System.out.println("rect x");
                return new PointLine(p1, p2);
            }

            if(checkRectY(p1, p2)) {
                System.out.println("rect y");
                return new PointLine(p1, p2);
            }

            if(checkMoreLineX(p1, p2, 1)) {
                System.out.println("more right");
                return new PointLine(p1, p2);
            }

            if(checkMoreLineX(p1, p2, -1)) {
                System.out.println("more left");
                return new PointLine(p1, p2);
            }

            if(checkMoreLineY(p1, p2, 1)) {
                System.out.println("more down");
                return new PointLine(p1, p2);
            }

            if(checkMoreLineY(p1, p2, -1)) {
                System.out.println("more up");
                return new PointLine(p1, p2);
            }
        }
        return null;
    }

    private void createMatrix() {
        matrix = new int[row][col];
        for(int i = 0; i < col; i++) {
            matrix[0][i] = matrix[row - 1][i] = 0;
        }

        for(int i = 0; i < row; i++) {
            matrix[i][0] = matrix[i][col - 1] = 0;
        }

        Random rand = new Random();
        int imgCount = 36;
        int max = 6;
        int[] arr = new int[imgCount + 1];
        ArrayList<Point> listPoint = new ArrayList<Point>();

        for(int i = 1; i < row - 1; i++) {
            for(int j = 1; j < col - 1; j++) {
                listPoint.add(new Point(i, j));
            }
        }

        int i = 0;
        do {
            int index = rand.nextInt(imgCount) + 1;
            if(arr[index] < max) {
                arr[index] += 2;
                for(int j = 0; j < 2; j++) {
                    try {
                        int size = listPoint.size();
                        int pointIndex = rand.nextInt(size);
                        matrix[listPoint.get(pointIndex).x][listPoint.get(pointIndex).y] = index;
                        listPoint.remove(pointIndex);
                    } catch (Exception e) {
                    }
                }
                i++;
            }
        } while (i < row * col / 2);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
}
