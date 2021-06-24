package org.roshanp.filter;

/**
 * reflects video frames to give the impresion that (if read from webcam), the video source mirrors the feed
 */
public class MirrorFilter extends Filter {

    @Override
    public int[][] filter(int[][] frame) {
        int[][] out = new int[frame.length][frame[0].length];
        for (int r = 0; r < frame.length; r++) {
            for (int c = 0; c < frame[r].length; c++) {
                out[r][c] = frame[r][frame[r].length - c - 1];
            }
        }
        return out;
    }
}
