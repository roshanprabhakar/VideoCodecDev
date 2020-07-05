package com.roshanprabhakar.feed;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public abstract class Feed extends Thread {


    protected volatile BlockingQueue<int[][]> feedQueue;

    public abstract void run();

    public synchronized int[][] take() {
        try {
            return feedQueue.take();
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    public abstract Dimension getFrameDimension();
}