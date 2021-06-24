package org.roshanp.feed;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * streams video data from the webcam
 */
public class WebcamFeed extends VideoFeed {

    private static final int WEBCAM = 1;

    public WebcamFeed() {
        feedQueue = new ArrayBlockingQueue<>(100);
    }

    @Override
    public void run() {
        VideoCapture video = new VideoCapture();
        video.open(WEBCAM);
        initiate(video);
    }

    @Override
    public Dimension getFrameDimension() {
        VideoCapture capture = new VideoCapture();
        capture.open(WEBCAM);

        Mat firstFrame = new Mat();
        capture.read(firstFrame);

        return new Dimension(firstFrame.width(), firstFrame.height());
    }
}
