package com.roshanprabhakar.filter;

import java.awt.*;
import java.util.HashMap;

public class EdgeDetector extends ConvolutionFilter {

    private static HashMap<Integer, double[][]> AVAILABLE_KERNELS = new HashMap<>();

    public static final int HORIZONTAL_KERNEL = 1;
    public static final int VERTICAL_KERNEL = 2;

    static {
        double[][] HORIZONTAL_KERNEL = {
                {-1, 0, 1},
                {-1, 0, 1},
                {-1, 0, 1}
        };

        double[][] VERTICAL_KERNEL = {
                {1, 1, 1},
                {0, 0, 0},
                {-1, -1, -1}
        };

        AVAILABLE_KERNELS.put(1, HORIZONTAL_KERNEL);
        AVAILABLE_KERNELS.put(2, VERTICAL_KERNEL);
    }

    public EdgeDetector(int kernel) {
        super(AVAILABLE_KERNELS.get(kernel));
    }

    @Override
    public int[][] filtered(int[][] frame) {

        convertToGreyByte(frame);
        frame = convolve(frame, 255);
        greyByteToInteger(frame);

        return frame;

    }
}
