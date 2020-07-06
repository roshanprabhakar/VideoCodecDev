package com.roshanprabhakar.feed;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * streams video data from the webcam
 */
public class WebcamFeed extends VideoFeed {

    public WebcamFeed() {
        feedQueue = new ArrayBlockingQueue<>(100);
    }

    @Override
    public void run() {
        VideoCapture video = new VideoCapture();
        video.open(0);
        initiate(video);
    }

    @Override
    public Dimension getFrameDimension() {
        VideoCapture capture = new VideoCapture();
        capture.open(0);

        Mat firstFrame = new Mat();
        capture.read(firstFrame);

        return new Dimension(firstFrame.width(), firstFrame.height());
    }
}
