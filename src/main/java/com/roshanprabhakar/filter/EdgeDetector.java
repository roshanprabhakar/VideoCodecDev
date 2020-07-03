package com.roshanprabhakar.filter;

public class EdgeDetector extends ConvolutionFilter {

    private static final double[][] edgeKernel = {
            {3, 0, -3},
            {10, 0, -10},
            {3, 0, -3}
    };

    public EdgeDetector() {
        super(edgeKernel);
    }

    @Override
    protected int[][] filtered(int[][] frame) {
        convertToGreyByte(frame);
        frame = convolve(frame, 255);
        greyByteToInteger(frame);

        return  frame;
    }
}
