package com.roshanprabhakar.channel;

import com.roshanprabhakar.renderer.Renderer;
import com.roshanprabhakar.filter.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Channel extends Thread {

    private Filter filter;
    private Renderer videoRenderer;

    private BlockingQueue<ArrayList<int[][]>> queue;

    public Channel(Filter filter, Renderer videoRenderer, int bufferCapacityInPackets) {

        this.filter = filter;
        this.videoRenderer = videoRenderer;

        this.queue = new ArrayBlockingQueue<>(bufferCapacityInPackets);
    }

    public void send(ArrayList<int[][]> packet) {
        System.out.println("------------------------");
        System.out.println("size before: " + queue.size());

         ArrayList<int[][]> filtered = filter.filtered(packet);
//        ArrayList<int[][]> filtered = packet;
        queue.add(filtered);

        System.out.println("size after: " + queue.size());
        System.out.println("------------------------");
    }

    public void run() {
        while (true) {
            try {
                videoRenderer.render(queue.take());
            } catch (InterruptedException ignored) {
            }
        }
    }

    public int[] transmit(int[] encoded) {
        try {Thread.sleep(0);} catch (InterruptedException ignored) {}
        return encoded;
    }
}
