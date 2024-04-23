package com.pokemon.controller;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;

import javax.swing.JProgressBar;
import javax.swing.Painter;

public class GradientPainter implements Painter<JProgressBar>{
    private final Color color1 = Color.BLUE;
    private final Color color2 = Color.YELLOW;
    private final Color color3 = new Color(189, 35, 14);


    @Override
    public void paint(Graphics2D g, JProgressBar c, int width, int height) {
        float[] fractions = {0f, 0.5f, 1f};
        Color[] colors = {color1, color2, color3};
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, width, height, color2);
        // LinearGradientPaint linearGradient = new LinearGradientPaint(0, 0, width, height, fractions, colors);
        g.setPaint(gradientPaint);
        g.fillRect(0, 0, width, height);
    }
}
