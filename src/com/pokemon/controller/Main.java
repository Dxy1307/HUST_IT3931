package com.pokemon.controller;

import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    FrameController frame;
    public Main() {
        // Frame frames = new Frame();
        frame = new FrameController();
        Time time = new Time();
        time.start();
        new Thread(frame).start();
    }

    class Time extends Thread {
        public void run() {
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }

                if(frame.isPause()) {
                    if(frame.isResume()) {
                        frame.time--;
                    }
                } else {
                    frame.time--;
                }
                if(frame.time == 0) {
                    frame.showDialogNewGame("Full time\nDo you want play again?", "Lose", 1);
                }
            }
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}
