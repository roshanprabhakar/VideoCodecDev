package org.roshanp.feed;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

/**
 * Optimized for video streaming, includes static methods for video processing
 */
public abstract class VideoFeed extends Feed {

    /**
     * streams video from some abstract source
     */
    public abstract void run();

    /**
     * returns the size characteristics of the streamed video frames
     */
    public abstract Dimension getFrameDimension();

    public int[][] imageToIntArray(BufferedImage image) {
        int[][] out = new int[image.getHeight()][image.getWidth()];
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                out[r][c] = image.getRGB(c, r);
            }
        }
        return out;
    }

    /**
     * converts opencv generated frames to java readable images
     */
    public static BufferedImage matToBufferedImage(Mat frame) {
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

    /**
     * initiates streaming from a VideoCapture device
     */
    protected void initiate(VideoCapture video) {
        Mat frame = new Mat();

        while (video.read(frame)) {
            feedQueue.add(imageToIntArray(matToBufferedImage(frame)));
        }
    }
}
