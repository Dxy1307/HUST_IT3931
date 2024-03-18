package com.pokemon.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IconEventController extends JPanel implements ActionListener {
    private int row;
    private int col;
    private int bound = 2;
    private int size = 50;
    private JButton[][] btn;
    private InitMatrixController initMatrixController;
    private Color backgroundColor = Color.lightGray;
    private FrameController frame;

    public IconEventController(FrameController frame, int row, int col) {
        this.frame = frame;
        this.row = row;
        this.col = col;

        setLayout(new GridLayout(row, col, bound, bound));
        setBackground(backgroundColor);
        setPreferredSize(new Dimension((size + bound) * col, (size + bound) *row));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setAlignmentY(JPanel.CENTER_ALIGNMENT);

        newGame();
    }

    private void newGame() {
        initMatrixController = new InitMatrixController(row, col);
        addArrayButton();
    }

    private void addArrayButton() {
        btn = new JButton[row][col];
        for(int i = 0; i < row; ++i) {
            for(int j = 0; j < col; ++j) {
                btn[i][j] = createButton(i + "," + j);
                Icon icon = getIcon(initMatrixController.getMatrix()[i][j]);
                btn[i][j].setIcon(icon);
                add(btn[i][j]);
            }
        }
    }

    private Icon getIcon(int index) {
        int width = 48;
        int height = 48;
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

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
