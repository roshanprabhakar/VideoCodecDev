package com.roshanprabhakar;


import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import static org.opencv.videoio.Videoio.CAP_PROP_FRAME_COUNT;

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
        System.setProperty("java.library.path", "/usr/local/Cellar/opencv/4.3.0_5/share/java/opencv4/");
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) throws InterruptedException {

        Codec codec = new Codec() {
            @Override
            public int[] encode(ArrayList<int[][]> frames) {
                return null;
            }

            @Override
            public ArrayList<int[][]> decode(int[] frames) {
                return null;
            }
        };

        VideoCapture capture = new VideoCapture();
        capture.open(path);

        Mat firstFrame = new Mat();
        capture.read(firstFrame);

        Renderer finalVideoRenderer = new Renderer(firstFrame.width(), firstFrame.height(), capture.get(Videoio.CAP_PROP_FPS) + 1000);
        Channel channel = new Channel(codec, finalVideoRenderer, (int) (capture.get(Videoio.CAP_PROP_FRAME_COUNT) / framesPerPacket), framesPerPacket * firstFrame.width() * firstFrame.height());
        Feed videoFeed = new VideoFeed(capture, path, channel, framesPerPacket);

        finalVideoRenderer.setVisible(true);

        videoFeed.start();
        channel.start();

    }
}
