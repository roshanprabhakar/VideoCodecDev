package com.roshanprabhakar;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Channel extends Thread {

    private Codec codec;
    private Renderer videoRenderer;

    private int pixelsPerPacket;

    private BlockingQueue<ArrayList<int[][]>> queue;

    public Channel(Codec codec, Renderer videoRenderer, int bufferCapacityInPackets, int pixelsPerPacket) {

        this.codec = codec;
        this.videoRenderer = videoRenderer;

        this.pixelsPerPacket = pixelsPerPacket;

        this.queue = new ArrayBlockingQueue<>(bufferCapacityInPackets);
    }

    public void send(ArrayList<int[][]> packet) {
        System.out.println("------------------------");
        System.out.println("size before: " + queue.size());
//        int[] encoded = codec.encode(packet);
//        int[] transmitted = transmit(encoded);
//        ArrayList<int[][]> frames = codec.decode(transmitted);
//        videoRenderer.render(frames);
        queue.add(packet);

        //1056000000 is temporary, its just the heap size available for the Feed thread on my mac
        if (queue.size() * pixelsPerPacket >= 1056000000) queue.clear();

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
