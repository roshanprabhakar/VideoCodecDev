package com.roshanprabhakar.renderer;

import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Renderer {

    private JFrame frame;

    private JLabel imageLabel;
    private BufferedImage image;

    private double fps;

    private static double packetsRendered = 0;

    private static final Runtime runtime = Runtime.getRuntime();

    public Renderer(int width, int height, double fps) {
        this.fps = fps;
        this.frame = new JFrame();
        this.imageLabel = new JLabel();
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        frame.getContentPane().add(this.imageLabel);
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

    public void render(ArrayList<int[][]> packet) {
//        long start = System.currentTimeMillis();
        for (int[][] frame : packet) {
            paint(frame);
            //try {Thread.sleep((int) ((1 / fps) * 1000));} catch (InterruptedException ignored) {}
        }
        packetsRendered++;

//        if (packetsRendered % 15 == 0) {
//            System.out.println("running gc");
//            System.gc();
//        }

        //Print used memory
//        System.out.println("Used Memory:"
//                + (runtime.totalMemory() - runtime.freeMemory()) / 1024 + ", packets rendered: " + packetsRendered);
    }

    public void paint(int[][] frame) {
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                image.setRGB(c, r, frame[r][c]);
            }
        }
//        System.out.println("time per pixel: " + ((System.currentTimeMillis() - start) / (double)(image.getHeight() * image.getWidth())));
        update();
    }

    public void update() {
        this.imageLabel.setIcon(new ImageIcon(image));
        this.frame.pack();
    }
}
