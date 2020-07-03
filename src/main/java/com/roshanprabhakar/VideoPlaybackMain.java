package com.roshanprabhakar;


import com.roshanprabhakar.channel.Channel;
import com.roshanprabhakar.feed.Feed;
import com.roshanprabhakar.feed.VideoFeed;
import com.roshanprabhakar.filter.EdgeDetector;
import com.roshanprabhakar.filter.Filter;
import com.roshanprabhakar.renderer.Renderer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.util.ArrayList;

public class VideoPlaybackMain {

//    private static final int frameWidth = 600;
//    private static final int frameHeight = 500;

//    private static final int fps = 40;
//    private static final int fpp = 20;

//    private static final int packets = 10;
//    private static final int channelBuffer = packets;

    private static final String path = "Untitled.mov";

    private static final int framesPerPacket = 10;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        EdgeDetector filter = new EdgeDetector();

        VideoCapture capture = new VideoCapture();
        capture.open(path);

        Mat firstFrame = new Mat();
        capture.read(firstFrame);

        Renderer finalVideoRenderer = new Renderer(firstFrame.width(), firstFrame.height(), capture.get(Videoio.CAP_PROP_FPS) + 1000);
        Channel channel = new Channel(filter, finalVideoRenderer, (int) (capture.get(Videoio.CAP_PROP_FRAME_COUNT) / framesPerPacket));
        Feed videoFeed = new VideoFeed(capture, path, channel, framesPerPacket);

        finalVideoRenderer.setVisible(true);

        videoFeed.start();
        channel.start();

    }
}
