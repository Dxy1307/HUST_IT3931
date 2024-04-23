package com.pokemon.controller;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class LinePanel extends JPanel{
    private Point p1;
    private Point p2;

    public LinePanel() {
        // setOpaque(false);
    }

    public void setPoints(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(p1 != null && p2 != null) {
            g.setColor(Color.RED);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
