package com.roshanprabhakar.feed;

import com.roshanprabhakar.channel.Channel;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

public abstract class Feed extends Thread {

    protected Channel channel;

    protected int framesPerPacket;
    protected int numPackets;

    private BlockingQueue<ArrayList<int[][]>> feedQueue;

    public Feed(Channel channel, int framesPerPacket, int numPackets) {
        this.channel = channel;

        this.framesPerPacket = framesPerPacket;
        this.numPackets = numPackets;
    }

    public abstract void run();
}