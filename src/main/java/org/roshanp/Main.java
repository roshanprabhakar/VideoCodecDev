package org.roshanp;


import org.roshanp.feed.Feed;
import org.roshanp.feed.WebcamFeed;
import org.roshanp.filter.Filter;
import org.roshanp.filter.FilterChain;
import org.roshanp.filter.MirrorFilter;
import org.roshanp.filter.convolution.EdgeDetector;
import org.roshanp.processor.Processor;
import org.roshanp.renderer.Renderer;
import org.opencv.core.Core;

import java.awt.*;
import java.util.ArrayList;

public class Main {

//    private static final int frameWidth = 600;
//    private static final int frameHeight = 500;

//    private static final int fps = 40;
//    private static final int fpp = 20;

//    private static final int packets = 10;
//    private static final int channelBuffer = packets;

    public static ArrayList<Processor> processors = new ArrayList<>();

    private static Dimension FRAME_DIMENSION;

    static {
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
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

        createProcesses(feed, renderer, filter, 10);

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
