package com.roshanprabhakar.filter;

public abstract class ConvolutionFilter extends Filter {

    protected double[][] kernel;

    public ConvolutionFilter(double[][] kernel) {
        this.kernel = kernel;
    }

    protected int[][] convolve(int[][] frame, int limit) {

        int[][] out = new int[frame.length][frame[0].length];

        for (int r = 0; r < frame.length - kernel.length; r++) {
            for (int c = 0; c < frame[r].length - kernel[0].length; c++) {

                double sum = 0;
                for (int i = 0; i < kernel.length; i++) {
                    for (int j = 0; j < kernel[i].length; j++) {

                        sum += frame[r + i][c + j] * kernel[i][j];

//                        System.out.println("frame@here: " + frame[r][c] + " kernel@here: " + kernel[i][j]);
//                        System.out.println("sum: " + frame[r][c] * kernel[i][j]);

                    }
                }

                if (sum > limit) {
                    sum = limit;
                } else if (sum < 0) {
                    sum = 0;
                }

                out[r + kernel.length / 2][c + kernel.length / 2] = (int) sum;

//                System.out.println(sum);
            }
        }

        return out;
    }
}
