package com.roshanprabhakar.filter;

import java.awt.*;
import java.util.ArrayList;

public abstract class Filter {

    public ArrayList<int[][]> filtered(ArrayList<int[][]> frames) {
        for (int i = 0; i < frames.size(); i++) {
            frames.set(i, filtered(frames.get(i)));
        }
        return frames;
    }

    protected abstract int[][] filtered(int[][] frame);

    protected void convertToGreyByte(int[][] frame) {

        Color pixel;
        for (int r = 0; r < frame.length; r++) {
            for (int c = 0; c < frame[r].length; c++) {

                pixel = new Color(frame[r][c]);
                int avg = (pixel.getRed() + pixel.getBlue() + pixel.getGreen()) / 3;
                frame[r][c] = avg;

            }
        }
    }

    protected void greyByteToInteger(int[][] greyscaleIntensities) {
        for (int r = 0; r < greyscaleIntensities.length; r++) {
            for (int c = 0; c < greyscaleIntensities[r].length; c++) {
                greyscaleIntensities[r][c] = new Color(
                        greyscaleIntensities[r][c],
                        greyscaleIntensities[r][c],
                        greyscaleIntensities[r][c]).getRGB();
            }
        }
    }
}
