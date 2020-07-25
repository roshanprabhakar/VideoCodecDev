package com.roshanprabhakar;


import com.roshanprabhakar.feed.Feed;
import com.roshanprabhakar.feed.FileFeed;
import com.roshanprabhakar.feed.WebcamFeed;
import com.roshanprabhakar.filter.Filter;
import com.roshanprabhakar.filter.FilterChain;
import com.roshanprabhakar.filter.MirrorFilter;
import com.roshanprabhakar.filter.convolution.EdgeDetector;
import com.roshanprabhakar.processor.Processor;
import com.roshanprabhakar.renderer.Renderer;
import org.opencv.core.Core;

import java.awt.*;
import java.util.ArrayList;

public class VideoPlaybackMain {

//    private static final int frameWidth = 600;
//    private static final int frameHeight = 500;

//    private static final int fps = 40;
//    private static final int fpp = 20;

//    private static final int packets = 10;
//    private static final int channelBuffer = packets;

    public static ArrayList<Processor> processors = new ArrayList<>();

    private static final String testVideo = "Untitled.mov";

    private static Dimension FRAME_DIMENSION;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {

        Feed feed = new WebcamFeed();
//        Feed feed = new FileFeed(testVideo);
        FRAME_DIMENSION = feed.getFrameDimension();

        Renderer renderer = new Renderer(FRAME_DIMENSION.width, FRAME_DIMENSION.height);

//        Filter filter = new EdgeDetector(EdgeDetector.HORIZONTAL_KERNEL);
        Filter filter = new FilterChain(new Filter[]{new EdgeDetector(EdgeDetector.HORIZONTAL_KERNEL), new MirrorFilter()});
//        Filter filter = new MirrorFilter();
//        Filter filter = new BlankFilter();

        createProcesses(feed, renderer, filter, 2);

        feed.start();

        renderer.setVisible(true);
        renderer.start();

        runProcesses();
    }

    public static void createProcesses(Feed feed, Renderer renderer, Filter filter, int numProcessors) {
        for (int i = 0; i < numProcessors; i++) {
            processors.add(new Processor(feed, renderer, filter));
        }
    }

    public static void runProcesses() {
        for (Processor processor : processors) {
            processor.start();
        }
    }
}
