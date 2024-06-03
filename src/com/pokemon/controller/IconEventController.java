package com.pokemon.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IconEventController extends JPanel implements ActionListener {
    private int row;
    private int col;
    private int bound = 5;
    private int size = 50;
    private JButton[][] btn;
    private InitMatrixController initMatrixController;
    private Color backgroundColor = Color.white;
    private FrameController frame;

    private Point p1 = null;
    private Point p2 = null;
    private PointLine line;
    private int score = 0;
    private int level = 1;
    private int item;

    public IconEventController(FrameController frame, int row, int col) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        item = row * col / 2;

        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backgroundColor);
        setPreferredSize(new Dimension((48 + bound) * col, (60 + bound) * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        newGame();
    }

    private void newGame() {
        initMatrixController = new InitMatrixController(this.frame, this.row, this.col);
        addArrayButton();
    }

    private void addArrayButton() {
        btn = new JButton[row][col];
        for(int i = 1; i < row - 1; ++i) {
            for(int j = 1; j < col - 1; ++j) {
                btn[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(initMatrixController.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
                add(btn[i][j]);
            }
        }
    }

    private Icon getIcon(int index) {
        int width = 48;
        int height = 60;
        Image image = new ImageIcon("src/com/pokemon/icon/" + index + ".png").getImage();
        Icon icon = new ImageIcon(image.getScaledInstance(width, height, Image.SCALE_SMOOTH));
        return icon;
    }

    private JButton createButton(String action) {
        JButton btn = new JButton();
        btn.setActionCommand(action);
        btn.setBorder(null);
        btn.addActionListener(this);
        return btn;
    }

    public void execute(Point p1, Point p2) {
        System.out.println("delete");
        setDisable(btn[p1.x][p1.y]);
        setDisable(btn[p2.x][p2.y]);
    }

    private void setDisable(JButton btn) {
        btn.setIcon(null);
        btn.setBackground(backgroundColor);
        btn.setEnabled(false);
    }

    public void shuffleGraphicsPanel() {
        initMatrixController.shuffleMatrix();
        for(int i = 1; i < row - 1; ++i) {
            for(int j = 1; j < col - 1; ++j) {
                Icon icon = getIcon(initMatrixController.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
            }
        }
    }

    public void continueGraphicsPanel(FrameController frame, int row, int col) {
        this.frame = frame;
        this.row = row + 2;
        this.col = col + 2;
        item = row * col / 2;

        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backgroundColor);
        setPreferredSize(new Dimension((48 + bound) * col, (60 + bound) * row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);
        level++;

        newGame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LinePanel linePanel = new LinePanel();
        String btnIndex = e.getActionCommand();
        int indexDot = btnIndex.lastIndexOf(",");
        int x = Integer.parseInt(btnIndex.substring(0, indexDot));
        int y = Integer.parseInt(btnIndex.substring(indexDot + 1, btnIndex.length()));

        if(p1 == null) {
            p1 = new Point(x, y);
            btn[p1.x][p1.y].setBorder(new LineBorder(Color.red, 3));
        } else {
            p2 = new Point(x, y);
            System.out.println("(" + p1.x + "," + p1.y + ")" + "--> " + "(" + p2.x + "," + p2.y + ")");
            line = initMatrixController.checkTwoPoint(p1, p2);
            if(line != null) {
                System.out.println("line != null");
                initMatrixController.getMatrix()[p1.x][p1.y] = 0;
                initMatrixController.getMatrix()[p2.x][p2.y] = 0;
                initMatrixController.showMatrix();

                linePanel.setPoints(p1, p2);

                if(level == 1)
                    execute(p1, p2);
                // line = null;
                // score += 10;
                // item--;
                // frame.time++;
                // frame.lbScore.setText(score + "");

                if(level == 2) {
                    for (int i = p1.x; i >= 1; --i) {
                        if(i == 1) {
                            initMatrixController.getMatrix()[i][p1.y] = 0;
                            btn[i][p1.y].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[i][p1.y] = initMatrixController.getMatrix()[i - 1][p1.y];
                            Icon icon = getIcon(initMatrixController.getMatrix()[i-1][p1.y]);
                            btn[i][p1.y].setIcon(icon);
                        }
                    }
    
                    for (int i = p2.x; i >= 1; --i) {
                        if(i == 1) {
                            initMatrixController.getMatrix()[i][p2.y] = 0;
                            btn[i][p2.y].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[i][p2.y] = initMatrixController.getMatrix()[i - 1][p2.y];
                            Icon icon = getIcon(initMatrixController.getMatrix()[i-1][p2.y]);
                            btn[i][p2.y].setIcon(icon);
                        }
                    }
                }

                if(level == 3) {
                    for(int i = p1.x; i < row - 1; ++i) {
                        if(i == row - 2) {
                            initMatrixController.getMatrix()[i][p1.y] = 0;
                            btn[i][p1.y].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[i][p1.y] = initMatrixController.getMatrix()[i + 1][p1.y];
                            Icon icon = getIcon(initMatrixController.getMatrix()[i+1][p1.y]);
                            btn[i][p1.y].setIcon(icon);
                        }
                    }

                    for(int i = p2.x; i < row - 1; ++i) {
                        if(i == row - 2) {
                            initMatrixController.getMatrix()[i][p2.y] = 0;
                            btn[i][p2.y].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[i][p2.y] = initMatrixController.getMatrix()[i + 1][p2.y];
                            Icon icon = getIcon(initMatrixController.getMatrix()[i+1][p2.y]);
                            btn[i][p2.y].setIcon(icon);
                        }
                    }
                }

                if(level == 4) {
                    for(int j = p1.y; j > 0; --j) {
                        if(j == 1) {
                            initMatrixController.getMatrix()[p1.x][j] = 0;
                            btn[p1.x][j].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[p1.x][j] = initMatrixController.getMatrix()[p1.x][j - 1];
                            Icon icon = getIcon(initMatrixController.getMatrix()[p1.x][j-1]);
                            btn[p1.x][j].setIcon(icon);
                        }
                    }

                    for(int j = p2.y; j > 0; --j) {
                        if(j == 1) {
                            initMatrixController.getMatrix()[p2.x][j] = 0;
                            btn[p2.x][j].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[p2.x][j] = initMatrixController.getMatrix()[p2.x][j - 1];
                            Icon icon = getIcon(initMatrixController.getMatrix()[p2.x][j-1]);
                            btn[p2.x][j].setIcon(icon);
                        }
                    }
                }

                if(level == 5) {
                    for(int j = p1.y; j < col - 1; ++j) {
                        if(j == col - 2) {
                            initMatrixController.getMatrix()[p1.x][j] = 0;
                            btn[p1.x][j].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[p1.x][j] = initMatrixController.getMatrix()[p1.x][j + 1];
                            Icon icon = getIcon(initMatrixController.getMatrix()[p1.x][j+1]);
                            btn[p1.x][j].setIcon(icon);
                        }
                    }

                    for(int j = p2.y; j < col - 1; ++j) {
                        if(j == col - 2) {
                            initMatrixController.getMatrix()[p2.x][j] = 0;
                            btn[p2.x][j].setIcon(null);
                        } else {
                            initMatrixController.getMatrix()[p2.x][j] = initMatrixController.getMatrix()[p2.x][j + 1];
                            Icon icon = getIcon(initMatrixController.getMatrix()[p2.x][j+1]);
                            btn[p2.x][j].setIcon(icon);
                        }
                    }
                }

                if(level == 6) {
                    shiftIconsToCenter(p1.x);
                    shiftIconsToCenter(p2.x);
                }

                line = null;
                score += 10;
                item--;
                frame.time++;
                frame.lbScore.setText(score + "");
            }            
            btn[p1.x][p1.y].setBorder(null);
            p1 = null;
            p2 = null;

            linePanel.setPoints(null, null);

            System.out.println("done");
            if(item == 0) {
                frame.showDialogNewGame("Win!\nDo you want to continue?", "Win", 1);
            }
        }
    }

    private void shiftIconsToCenter(int row) {
        int left = 0;
        int right = col - 1;
    
        while (left < right) {
            // If both left and right icons are 0, move both pointers
            if (initMatrixController.getMatrix()[row][left] == 0 && initMatrixController.getMatrix()[row][right] == 0) {
                left++;
                right--;
            }
            // If left icon is 0, shift icons from right to left
            else if (initMatrixController.getMatrix()[row][left] == 0) {
                initMatrixController.getMatrix()[row][left] = initMatrixController.getMatrix()[row][right];
                initMatrixController.getMatrix()[row][right] = 0;
                Icon icon = getIcon(initMatrixController.getMatrix()[row][left]);
                btn[row][left].setIcon(icon);
                btn[row][right].setIcon(null);
                left++;
                right--;
            }
            // If right icon is 0, shift icons from left to right
            else if (initMatrixController.getMatrix()[row][right] == 0) {
                initMatrixController.getMatrix()[row][right] = initMatrixController.getMatrix()[row][left];
                initMatrixController.getMatrix()[row][left] = 0;
                Icon icon = getIcon(initMatrixController.getMatrix()[row][right]);
                btn[row][right].setIcon(icon);
                btn[row][left].setIcon(null);
                left++;
                right--;
            }
            // If neither left nor right icon is 0, move both pointers
            else {
                left++;
                right--;
            }
        }
    }
}
