package com.roshanprabhakar;

public abstract class Feed extends Thread {

    protected Channel channel;

    protected int framesPerPacket;
    protected int numPackets;

    public Feed(Channel channel, int framesPerPacket, int numPackets) {
        this.channel = channel;

        this.framesPerPacket = framesPerPacket;
        this.numPackets = numPackets;
    }

    public abstract void run();
}