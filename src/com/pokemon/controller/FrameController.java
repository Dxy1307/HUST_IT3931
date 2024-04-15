package com.pokemon.controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FrameController extends JFrame implements ActionListener, Runnable {
    private int row = 9;
    private int col = 16;
    private int width = 1200;
    private int height = 800;
    private IconEventController graphicsPanel;
    private JPanel mainPanel;

    private int MAX_TIME = 500;
    public int time = MAX_TIME;
    public JLabel lbScore;
    private JProgressBar progressTime;
    private JButton btnNewGame;
    
    private boolean pause = false;
    private boolean resume = false;

    public FrameController() {
        // try {
        //     mainPanel = new ImagePanel("src/com/pokemon/background/bgpikachu2.png");
        //     mainPanel.setLayout(new BorderLayout());
        //     mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        //     mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        //     mainPanel.setBackground(Color.white);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        add(mainPanel = createMainPanel());
        setTitle("Pokemon Game");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);

        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // int x = screenSize.width - width;
        // int y = screenSize.height - height;
        // setLocation(x, y);

        setLocationRelativeTo(null);
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.EAST); // khởi tạo các thanh điểm, score, new game
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new IconEventController(this, row, col);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.white);

        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.anchor = GridBagConstraints.EAST;
        // gbc.weightx = 1.0;
        // gbc.weighty = 1.0;

        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel() {
        lbScore = new JLabel("0");
        progressTime = new JProgressBar(0, 100);
        progressTime.setValue(100);

        JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
        panelLeft.add(new JLabel("Score: "));
        panelLeft.add(new JLabel("Time: "));

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
        panelControl.add(btnNewGame = createButton("New Game"), BorderLayout.PAGE_END);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Good luck"));
        panel.add(panelControl, BorderLayout.PAGE_START);

        return panel;
    }

    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    public void newGame() {
        time = MAX_TIME;
        graphicsPanel.removeAll();
        mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
        lbScore.setText("0");
    }

    public void showDialogNewGame(String message, String title, int t) {
        pause = true;
        resume = false;
        int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, 
                                                    JOptionPane.QUESTION_MESSAGE, null, null, null);
        if(select == 0) {
            pause = false;
            newGame();
        } else {
            if(t == 1) {
                System.exit(0);
            } else {
                resume = true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnNewGame) {
            showDialogNewGame("Your game hasn't done. Do you want to start a new game?", "Warning", 0);            
        }
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressTime.setValue((int) ((double) time / MAX_TIME * 100));
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isResume() {
        return resume;
    }

    public void setResume(boolean resume) {
        this.resume = resume;
    }

    public JProgressBar getProgressTime() {
        return progressTime;
    }

    public void setProgressTime(JProgressBar progressTime) {
        this.progressTime = progressTime;
    }
}
