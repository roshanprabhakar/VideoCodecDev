package org.roshanp.renderer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * reads frames from an in queue for display
 */
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

            if (framesAdded < 1) continue; //buffer

            try {
                render(renderQueue.take());
            } catch (InterruptedException ignored) {
            }
        }
    }
//
//    public void run() {
//        try {
//            int[][] initFrame = renderQueue.take();
//            render(initFrame);
//
//            while (true) {
//
//                int[][] nextFrame = renderQueue.take();
//                int[][] delta = getDelta(initFrame, nextFrame, 1000000);
//
//                if (isBlank(delta)) {
//                    render(initFrame);
//                } else {
//                    render(delta);
//                }
//
//                initFrame = delta;
//            }
//        } catch (InterruptedException ignored) {}
//    }

    public int[][] getDelta(int[][] initFrame, int[][] nextFrame, int threshold) {
        int[][] out = new int[initFrame.length][initFrame[0].length];
        for (int r = 0; r < initFrame.length; r++) {
            for (int c = 0; c < initFrame[r].length; c++) {
                if (Math.abs(nextFrame[r][c] - initFrame[r][c]) > threshold) {
                    out[r][c] = nextFrame[r][c];
                }
            }
        }
        return out;
    }

    public boolean isBlank(int[][] frame) {
        for (int r = 0; r < frame.length; r++) {
            for (int c = 0; c < frame[r].length; c++) {
                if (frame[r][c] != 0) return false;
            }
        }
        return true;
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
