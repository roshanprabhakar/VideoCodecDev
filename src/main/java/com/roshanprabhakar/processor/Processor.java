package com.roshanprabhakar.processor;

import com.roshanprabhakar.feed.Feed;
import com.roshanprabhakar.filter.Filter;
import com.roshanprabhakar.renderer.Renderer;

public class Processor extends Thread {

    private Filter filter;

    private Feed feed;
    private Renderer renderer;

    public Processor(Feed feed, Renderer renderer, Filter filter) {
        this.feed = feed;
        this.renderer = renderer;
        this.filter = filter;
    }

    public void run() {
        while (true) {
            int[][] frame = feed.take();
            frame = filter.filter(frame);
            renderer.add(frame);
        }
    }
}
