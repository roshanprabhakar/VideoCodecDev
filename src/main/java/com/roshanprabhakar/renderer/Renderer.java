package com.roshanprabhakar.renderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Renderer extends Thread {

    private JFrame frame;

    private JLabel imageLabel;
    private BufferedImage image;

    public volatile BlockingQueue<int[][]> renderQueue;

    private volatile int framesAdded = 0;

    public Renderer(int width, int height) {

        this.frame = new JFrame();
        this.imageLabel = new JLabel();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.renderQueue = new ArrayBlockingQueue<>(1000);

        this.frame.getContentPane().add(this.imageLabel);
        update();
        this.frame.pack();
    }

    public synchronized void add(int[][] frame) {
        renderQueue.add(frame);
        framesAdded++;
    }

    public void run() {

        while (true) {

            if (framesAdded < 1) continue;

            try {
                render(renderQueue.take());
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void render(int[][] frame) {
        paint(frame);
    }

    private void paint(int[][] frame) {
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                image.setRGB(c, r, frame[r][c]);
            }
        }
        update();
    }

    private void update() {
        this.imageLabel.setIcon(new ImageIcon(image));
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

}
