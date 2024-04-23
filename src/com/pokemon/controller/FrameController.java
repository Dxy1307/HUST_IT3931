package com.pokemon.controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

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
    private JButton btnShuffle;
    
    private boolean pause = false;
    private boolean resume = false;

    public FrameController() {
        // try {
        //     mainPanel = new ImagePanel("src/com/pokemon/background/bgpikachu2.png");
        //     mainPanel.setLayout(new BorderLayout());
        //     mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
        //     mainPanel.add(createControlPanel(), BorderLayout.EAST);
        //     // mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 100));
        //     // mainPanel.setBackground(Color.white);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        add(mainPanel = createMainPanel());
        setTitle("Pokemon Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                // Image backgroudImage = ImageIO.read(new File("src/com/pokemon/background/bgpikachu2.png"));
                ImageIcon icon = new ImageIcon("src/com/pokemon/background/bgpikachu2.png");
                Dimension d = getSize();                
                g.drawImage(icon.getImage(), 0, 0, this);
                setOpaque(false);
                super.paintComponent(g);
            }
        };
        panel.add(createGraphicsPanel(), BorderLayout.CENTER);
        panel.add(createControlPanel(), BorderLayout.EAST); // khởi tạo các thanh điểm, score, new game
        return panel;
    }

    private JPanel createGraphicsPanel() {
        graphicsPanel = new IconEventController(this, row, col);
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel shuffleGrapJPanel() {
        graphicsPanel.shuffleGraphicsPanel();
        JPanel panel = new JPanel(new GridBagLayout());

        panel.add(graphicsPanel);
        return panel;
    }

    private JPanel createControlPanel() {
        // try {
        //     for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        //         if ("Nimbus".equals(info.getName())) {
        //             UIManager.setLookAndFeel(info.getClassName());
        //             break;
        //         }
        //     }
        // } catch (Exception e) {
        //     // If Nimbus is not available, you can handle the exception here
        // }

        Painter<JProgressBar> painter = new GradientPainter();

        UIManager.put("ProgressBar[Enabled].foregroundPainter", painter);
        UIManager.put("ProgressBar[Enabled+Finished].foregroundPainter", painter);
        UIManager.put("ProgressBar[Enabled+Indeterminate].foregroundPainter", painter);

        lbScore = new JLabel("0");
        progressTime = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        progressTime.setValue(100);

        // Apply the new UI
        SwingUtilities.updateComponentTreeUI(progressTime);

        JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
        panelLeft.setOpaque(false);
        panelLeft.add(new JLabel("Score: "));
        panelLeft.add(new JLabel("Time: "));

        JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
        panelCenter.setOpaque(false);
        panelCenter.add(lbScore);
        panelCenter.add(progressTime);

        JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 0));
        panelScoreAndTime.setOpaque(false);
        panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
        panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

        JPanel panelControl = new JPanel(new BorderLayout(10, 10));
        panelControl.setOpaque(false);
        panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
        panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
        panelControl.add(btnNewGame = createButton("New Game"), BorderLayout.PAGE_END);
        panelControl.add(btnShuffle = createButton("Shuffle"), BorderLayout.PAGE_START);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new TitledBorder("Good luck"));
        panel.add(panelControl, BorderLayout.PAGE_START);

        return panel;
    }

    private JButton createButton(String buttonName) {
        JButton btn = new JButton(buttonName);
        btn.addActionListener(this);
        return btn;
    }

    public void shuffleGame() {
        // graphicsPanel.removeAll();
        mainPanel.add(shuffleGrapJPanel(), BorderLayout.CENTER);
        mainPanel.validate();
        mainPanel.setVisible(true);
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
        if(e.getSource() == btnShuffle) {
            shuffleGame();
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
