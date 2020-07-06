package com.roshanprabhakar.feed;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * A class for the implementation of a datafeed, where data is streamed in the form of integer arrays
 */
public abstract class Feed extends Thread {

    protected volatile BlockingQueue<int[][]> feedQueue;

    /**
     * data is streamed to the feedQueue Queue
     */
    public abstract void run();

    /**
     * data chunks are taken from the feedQueue, synchronization enforces only one thread access the feedQueue at a time
     */
    public synchronized int[][] take() {
        try {
            return feedQueue.take();
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    public abstract Dimension getFrameDimension();
}