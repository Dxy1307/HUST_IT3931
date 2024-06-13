package com.pokemon.controller;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class InitMatrixController {
    private int row;
    private int col;
    private int[][] matrix;
    private ArrayList<Point> paths = new ArrayList<Point>();
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
                int i1 = rand.nextInt(i + 1) + 1;
                int j1 = rand.nextInt(j + 1) + 1;

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
            // System.out.println("Graphics context is null");
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
        // System.out.println("check line x");

        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        for(int y = min + 1; y < max; y++) {
            if(matrix[x][y] != 0) {
                // System.out.println("die: " + x + "" + y);
                return false;
            }
            // System.out.println("ok: " + x + "" + y);
        }
        // System.out.println("line x");
        // System.out.println("(" + x + "," + min + ") -> (" + x + "," + max + ")");
        // paths.add(new Point(x, min));
        // paths.add(new Point(x, max));
        return true;
    }

    private boolean checkLineX_temp(int y1, int y2, int x) {
        int min = Math.min(y1, y2);
        int max = Math.max(y1, y2);

        for(int y = min + 1; y < max; y++) {
            if(matrix[x][y] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLineY(int x1, int x2, int y) {
        // System.out.println("check line y");

        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);

        for(int x = min + 1; x < max; x++) {
            if(matrix[x][y] != 0) {
                // System.out.println("die: " + x + "" + y);
                return false;
            }
            // System.out.println("ok: " + x + "" + y);
        }
        // System.out.println("line y");
        // System.out.println("(" + min + "," + y + ") -> (" + max + "," + y + ")");
        // paths.add(new Point(min, y));
        // paths.add(new Point(max, y));
        return true;
    }

    private boolean checkLineY_temp(int x1, int x2, int y) {
        int min = Math.min(x1, x2);
        int max = Math.max(x1, x2);

        for(int x = min + 1; x < max; x++) {
            if(matrix[x][y] != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRectX(Point p1, Point p2) {
		System.out.println("check rect x");
		// find point have y min and max
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for (int y = pMinY.y; y <= pMaxY.y; y++) {
			if (y > pMinY.y && matrix[pMinY.x][y] != 0) {
				return false;
			}
			// check two line
			if ((matrix[pMaxY.x][y] == 0 || y == pMaxY.y)
					&& checkLineY(pMinY.x, pMaxY.x, y)
					&& checkLineX(y, pMaxY.y, pMaxY.x)) {

				System.out.println("Rect x");
				System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
						+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
						+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");

                paths.add(pMinY);
                paths.add(new Point(pMinY.x, y));
                paths.add(new Point(pMaxY.x, y));
                paths.add(pMaxY);
				// if three line is true return column y
				return true;
			}
		}
		// have a line in three line not true then return -1
		return false;
	}

    // private boolean checkRectX(Point p1, Point p2) {
    //     paths.clear();

    //     Point pMiny = p1, pMaxy = p2;
    //     if(p1.y > p2.y) {
    //         pMiny = p2;
    //         pMaxy = p1;
    //     }

    //     for(int y = pMiny.y; y <= pMaxy.y; y++) {
    //         if(y > pMiny.y && matrix[pMiny.x][y] != 0) {
    //             return false;
    //         }

    //         if((matrix[pMaxy.x][y] == 0) && checkLineY(pMiny.x, pMaxy.x, y) && checkLineX(y, pMaxy.y, pMaxy.x)) {
    //             // System.out.println("Rect x");
    //             // System.out.println("(" + pMiny.x + "," + pMiny.y + ") -> ("
    //             //         + pMiny.x + "," + y + ") -> (" + pMaxy.x + "," + y
    //             //         + ") -> (" + pMaxy.x + "," + pMaxy.y + ")");

    //             paths.add(pMiny);
    //             paths.add(new Point(pMiny.x, y));
    //             paths.add(new Point(pMaxy.x, y));
    //             paths.add(pMaxy);
                
    //             return true;
    //         }
    //     }

    //     return false;
    // }

    private boolean checkRectX_temp(Point p1, Point p2) {
        System.out.println("check rect x");
		// find point have y min and max
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		for (int y = pMinY.y; y <= pMaxY.y; y++) {
			if (y > pMinY.y && matrix[pMinY.x][y] != 0) {
				return false;
			}
			// check two line
			if ((matrix[pMaxY.x][y] == 0 || y == pMaxY.y)
					&& checkLineY(pMinY.x, pMaxY.x, y)
					&& checkLineX(y, pMaxY.y, pMaxY.x)) {
				// if three line is true return column y
				return true;
			}
		}
		// have a line in three line not true then return -1
		return false;
    }

    private boolean checkRectY(Point p1, Point p2) {
		System.out.println("check rect y");
		// find point have y min
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		// find line and y begin
		for (int x = pMinX.x; x <= pMaxX.x; x++) {
			if (x > pMinX.x && matrix[x][pMinX.y] != 0) {
				return false;
			}
			if ((matrix[x][pMaxX.y] == 0 || x == pMaxX.x)
					&& checkLineX(pMinX.y, pMaxX.y, x)
					&& checkLineY(x, pMaxX.x, pMaxX.y)) {

				System.out.println("Rect y");
				System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> (" + x
						+ "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
						+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");

                paths.add(pMinX);
                paths.add(new Point(x, pMinX.y));
                paths.add(new Point(x, pMaxX.y));
                paths.add(pMaxX);
				return true;
			}
		}
		return false;
	}

    // private boolean checkRectY(Point p1, Point p2) {
    //     paths.clear();

    //     Point pMinx = p1, pMaxx = p2;
    //     if(p1.x > p2.x) {
    //         pMinx = p2;
    //         pMaxx = p1;
    //     }

    //     for(int x = pMinx.x; x <= pMaxx.x; x++) {
    //         if(x > pMinx.x && matrix[x][pMinx.y] != 0) {
    //             return false;
    //         }

    //         if((matrix[x][pMaxx.y] == 0) && checkLineX(pMinx.y, pMaxx.y, x) && checkLineY(x, pMaxx.x, pMaxx.y)) {
    //             // System.out.println("Rect y");
    //             // System.out.println("(" + pMinx.x + "," + pMinx.y + ") -> (" + x
    //             //         + "," + pMinx.y + ") -> (" + x + "," + pMaxx.y
    //             //         + ") -> (" + pMaxx.x + "," + pMaxx.y + ")");

    //             paths.add(pMinx);
    //             paths.add(new Point(x, pMinx.y));
    //             paths.add(new Point(x, pMaxx.y));
    //             paths.add(pMaxx);
                        
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    private boolean checkRectY_temp(Point p1, Point p2) {
        System.out.println("check rect y");
		// find point have y min
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		// find line and y begin
		for (int x = pMinX.x; x <= pMaxX.x; x++) {
			if (x > pMinX.x && matrix[x][pMinX.y] != 0) {
				return false;
			}
			if ((matrix[x][pMaxX.y] == 0 || x == pMaxX.x)
					&& checkLineX(pMinX.y, pMaxX.y, x)
					&& checkLineY(x, pMaxX.x, pMaxX.y)) {
				return true;
			}
		}
		return false;
    }

    private boolean checkMoreLineX(Point p1, Point p2, int type) {
		System.out.println("check chec more x");
		// find point have y min
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		// find line and y begin
		int y = pMaxY.y + type;
		int row = pMinY.x;
		int colFinish = pMaxY.y;
		if (type == -1) {
			colFinish = pMinY.y;
			y = pMinY.y + type;
			row = pMaxY.x;
			System.out.println("colFinish = " + colFinish);
		}

		// find column finish of line

		// check more
		if ((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, row)) {
			while (matrix[pMinY.x][y] == 0
					&& matrix[pMaxY.x][y] == 0) {
				if (checkLineY(pMinY.x, pMaxY.x, y)) {

					System.out.println("TH X " + type);
					System.out.println("(" + pMinY.x + "," + pMinY.y + ") -> ("
							+ pMinY.x + "," + y + ") -> (" + pMaxY.x + "," + y
							+ ") -> (" + pMaxY.x + "," + pMaxY.y + ")");

                    paths.add(pMinY);
                    paths.add(new Point(pMinY.x, y));
                    paths.add(new Point(pMaxY.x, y));
                    paths.add(pMaxY);
					return true;
				}
				y += type;
			}
		}
		return false;
	}

    // private boolean checkMoreLineX(Point p1, Point p2, int type) {
    //     paths.clear();

    //     Point pMiny = p1, pMaxy = p2;
    //     if(p1.y > p2.y) {
    //         pMiny = p2;
    //         pMaxy = p1;
    //     }
    //     int y = pMaxy.y + type;
    //     // int y = pMaxy.y;
    //     int row = pMiny.x;
    //     int colFinish = pMaxy.y;
    //     if(type == -1) {
    //         colFinish = pMiny.y;
    //         y = pMiny.y + type;
    //         // y = pMiny.y;
    //         row = pMaxy.x;
    //     }

    //     if((matrix[row][colFinish] == 0 || pMiny.y == pMaxy.y) && checkLineX(pMiny.y, pMaxy.y, row)) {
    //         // if((type == 1 && y == pMaxy.y) || (type == -1 && y == pMiny.y)) {
    //         //     if(checkLineY(pMiny.x, pMaxy.x, colFinish)) {
    //         //         System.out.println("L X " + type);
    //         //         System.out.println("(" + pMiny.x + "," + pMiny.y + ") -> ("
    //         //                 + pMiny.x + "," + colFinish + ") -> (" + pMaxy.x + "," + pMaxy.y + ")");

    //         //         paths.add(pMiny);
    //         //         paths.add(new Point(pMiny.x, colFinish));
    //         //         paths.add(pMaxy);

    //         //         return true;                
    //         //     }
    //         // } else {
    //         //     y += type;
    //             while(matrix[pMiny.x][y] == 0 && matrix[pMaxy.x][y] == 0) {
    //                 if(checkLineY(pMiny.x, pMaxy.x, y)) {
    //                     // System.out.println("TH X " + type);
    //                     // System.out.println("(" + pMiny.x + "," + pMiny.y + ") -> ("
    //                     //         + pMiny.x + "," + y + ") -> (" + pMaxy.x + "," + y
    //                     //         + ") -> (" + pMaxy.x + "," + pMaxy.y + ")");
    
    //                     paths.add(pMiny);
    //                     paths.add(new Point(pMiny.x, y));
    //                     paths.add(new Point(pMaxy.x, y));
    //                     paths.add(pMaxy);
    
    //                     return true;
    //                 }
    //                 y += type;
    //             }
    //         // }
    //     }
    //     return false;
    // }

    private boolean checkMoreLineX_temp(Point p1, Point p2, int type) {
		// find point have y min
		Point pMinY = p1, pMaxY = p2;
		if (p1.y > p2.y) {
			pMinY = p2;
			pMaxY = p1;
		}
		// find line and y begin
		int y = pMaxY.y + type;
		int row = pMinY.x;
		int colFinish = pMaxY.y;
		if (type == -1) {
			colFinish = pMinY.y;
			y = pMinY.y + type;
			row = pMaxY.x;
		}

		// find column finish of line

		// check more
		if ((matrix[row][colFinish] == 0 || pMinY.y == pMaxY.y)
				&& checkLineX(pMinY.y, pMaxY.y, row)) {
			while (matrix[pMinY.x][y] == 0
					&& matrix[pMaxY.x][y] == 0) {
				if (checkLineY(pMinY.x, pMaxY.x, y)) {
					return true;
				}
				y += type;
			}
		}
		return false;
    }

    private boolean checkMoreLineY(Point p1, Point p2, int type) {
		System.out.println("check more y");
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		int x = pMaxX.x + type;
		int col = pMinX.y;
		int rowFinish = pMaxX.x;
		if (type == -1) {
			rowFinish = pMinX.x;
			x = pMinX.x + type;
			col = pMaxX.y;
		}
		if ((matrix[rowFinish][col] == 0 || pMinX.x == pMaxX.x)
				&& checkLineY(pMinX.x, pMaxX.x, col)) {
			while (matrix[x][pMinX.y] == 0
					&& matrix[x][pMaxX.y] == 0) {
				if (checkLineX(pMinX.y, pMaxX.y, x)) {
					System.out.println("TH Y " + type);
					System.out.println("(" + pMinX.x + "," + pMinX.y + ") -> ("
							+ x + "," + pMinX.y + ") -> (" + x + "," + pMaxX.y
							+ ") -> (" + pMaxX.x + "," + pMaxX.y + ")");

                    paths.add(pMinX);
                    paths.add(new Point(x, pMinX.y));
                    paths.add(new Point(x, pMaxX.y));
                    paths.add(pMaxX);
					return true;
				}
				x += type;
			}
		}
		return false;
	}

    // private boolean checkMoreLineY(Point p1, Point p2, int type) {
    //     paths.clear();

    //     Point pMinx = p1, pMaxx = p2;
    //     if (p1.x > p2.x) {
    //         pMinx = p2;
    //         pMaxx = p1;
    //     }
    //     int x = pMaxx.x + type;
    //     // int x = pMaxx.x;
    //     int col = pMinx.y;
    //     int rowFinish = pMaxx.x;
    //     if (type == -1) {
    //         rowFinish = pMinx.x;
    //         x = pMinx.x + type;
    //         // x = pMinx.x;
    //         col = pMaxx.y;
    //     }
    //     if ((matrix[rowFinish][col] == 0|| pMinx.x == pMaxx.x)
    //             && checkLineY(pMinx.x, pMaxx.x, col)) {
    //         // if((type == 1 && x == pMaxx.x) || (type == -1 && x == pMinx.x)) {
    //         //     if(checkLineX(pMinx.y, pMaxx.y, x)) {
    //         //         System.out.println("TH Y " + type);
    //         //         System.out.println("(" + pMinx.x + "," + pMinx.y + ") -> ("
    //         //                 + x + "," + pMinx.y + ") -> (" + pMaxx.x + "," + pMaxx.y + ")");
    
    //         //         paths.add(pMinx);
    //         //         paths.add(new Point(x, pMinx.y));
    //         //         paths.add(pMaxx);
    
    //         //         return true;
    //         //     }
    //         // } else {
    //         //     x += type;
    //             while (matrix[x][pMinx.y] == 0 && matrix[x][pMaxx.y] == 0) {
    //                 if (checkLineX(pMinx.y, pMaxx.y, x)) {
    //                     System.out.println("L Y " + type);
    //                     System.out.println("(" + pMinx.x + "," + pMinx.y + ") -> ("
    //                             + x + "," + pMinx.y + ") -> (" + x + "," + pMaxx.y
    //                             + ") -> (" + pMaxx.x + "," + pMaxx.y + ")");
    
    //                     paths.add(pMinx);
    //                     paths.add(new Point(x, pMinx.y));
    //                     paths.add(new Point(x, pMaxx.y));
    //                     paths.add(pMaxx);
    
    //                     return true;
    //                 }
    //                 x += type;
    //             }
    //         // }
    //     }
    //     return false;
    // }

    private boolean checkMoreLineY_temp(Point p1, Point p2, int type) {
		Point pMinX = p1, pMaxX = p2;
		if (p1.x > p2.x) {
			pMinX = p2;
			pMaxX = p1;
		}
		int x = pMaxX.x + type;
		int col = pMinX.y;
		int rowFinish = pMaxX.x;
		if (type == -1) {
			rowFinish = pMinX.x;
			x = pMinX.x + type;
			col = pMaxX.y;
		}
		if ((matrix[rowFinish][col] == 0 || pMinX.x == pMaxX.x)
				&& checkLineY(pMinX.x, pMaxX.x, col)) {
			while (matrix[x][pMinX.y] == 0
					&& matrix[x][pMaxX.y] == 0) {
				if (checkLineX(pMinX.y, pMaxX.y, x)) {
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
                paths.clear();
                if(checkLineX(p1.y, p2.y, p1.x)) {
                    paths.add(p1);
                    paths.add(p2);
                    return new PointLine(p1, p2);
                }
            }

            if(p1.y == p2.y) {
                paths.clear();
                if(checkLineY(p1.x, p2.x, p1.y)) {
                    paths.add(p1);
                    paths.add(p2);
                    return new PointLine(p1, p2);
                }
            }

            paths.clear();
            if(checkRectX(p1, p2)) {
                return new PointLine(p1, p2);
            }

            paths.clear();
            if(checkRectY(p1, p2)) {
                return new PointLine(p1, p2);
            }

            paths.clear();
            if(checkMoreLineX(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }

            paths.clear();
            if(checkMoreLineX(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }

            paths.clear();
            if(checkMoreLineY(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }

            paths.clear();
            if(checkMoreLineY(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }
        }

        return null;
    }

    public PointLine checkTwoPoint_temp(Point p1, Point p2) {
        if(!p1.equals(p2) && matrix[p1.x][p1.y] == matrix[p2.x][p2.y]) {
            if(p1.x == p2.x) {
                if(checkLineX_temp(p1.y, p2.y, p1.x)) {
                    return new PointLine(p1, p2);
                }
            }

            if(p1.y == p2.y) {
                if(checkLineY_temp(p1.x, p2.x, p1.y)) {
                    return new PointLine(p1, p2);
                }
            }

            if(checkRectX_temp(p1, p2)) {
                return new PointLine(p1, p2);
            }

            if(checkRectY_temp(p1, p2)) {
                return new PointLine(p1, p2);
            }

            if(checkMoreLineX_temp(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }

            if(checkMoreLineX_temp(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }

            if(checkMoreLineY_temp(p1, p2, 1)) {
                return new PointLine(p1, p2);
            }

            if(checkMoreLineY_temp(p1, p2, -1)) {
                return new PointLine(p1, p2);
            }
        }
        return null;
    }

    public boolean checkAllIcons() {
        for(int i = 1; i < row - 1; i++) {
            for(int j = 1; j < col - 1; j++) {
                if(matrix[i][j] != 0) {
                    for(int k = i; k < row - 1; k++) {
                        for(int l = j; l < col - 1; l++) {
                            if(matrix[k][l] == 0 || (k == i && l == j)) {
                                continue;
                            } else {
                                if(matrix[i][j] == matrix[k][l]) {
                                    PointLine pointLine = checkTwoPoint_temp(new Point(i, j), new Point(k, l));
                                    if(pointLine != null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false;
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

    public ArrayList<Point> getPaths() {
        return paths;
    }
}
