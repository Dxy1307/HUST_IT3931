package com.pokemon.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameController extends JFrame implements ActionListener, Runnable {
    private int row = 8;
    private int col = 8;
    private int width = 700;
    private int height = 500;
    private IconEventController graphicsPanel;
    private JPanel mainPanel;

    public FrameController() {
        add(mainPanel = createMainPanel());
        setTitle("Pokemon Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new IconEventController(this, row, col);
        JPanel panel = new JPanel(new GridLayout());
        panel.setBackground(Color.gray);
        panel.add(graphicsPanel);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {

    }
}