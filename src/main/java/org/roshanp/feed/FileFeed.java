package org.roshanp.feed;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Writeen for the streaming of video data from a video file
 */
public class FileFeed extends VideoFeed {

    private String filepath;

    public FileFeed(String filepath) {
        this.filepath = filepath;
        feedQueue = new ArrayBlockingQueue<>(countFrames(filepath));
    }

    @Override
    public void run() {

        VideoCapture video = new VideoCapture();
        video.open(filepath);
        initiate(video);

    }

    @Override
    public Dimension getFrameDimension() {
        VideoCapture capture = new VideoCapture();
        capture.open(filepath);

        Mat firstFrame = new Mat();
        capture.read(firstFrame);

        return new Dimension(firstFrame.width(), firstFrame.height());
    }

    /**
     * conts the number of frames in the video file specified
     */
    protected static int countFrames(String video) {
        VideoCapture camera = new VideoCapture();
        camera.open(video);

        int numFrames = 0;
        Mat mat = new Mat();

        while (camera.read(mat)) {
            numFrames++;
        }

        return numFrames;
    }
}
