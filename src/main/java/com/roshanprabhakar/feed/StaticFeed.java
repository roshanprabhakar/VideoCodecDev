package com.roshanprabhakar.feed;

import com.roshanprabhakar.channel.Channel;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class StaticFeed extends Feed {

    private int waitTime;

    private int frameWidth;
    private int frameHeight;

    public StaticFeed(Channel channel, int frameWidth, int frameHeight, int framesPerPacket, int packetCap) {
        super(channel, framesPerPacket, packetCap);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.waitTime = (int) (renderTimePerPacket() * 1.1);
    }

    public void run() {
        boolean canStream = true;
        int packetsCreated = 0;

//        long start;
//        long end;

        while (canStream) {

            //packets created
            ArrayList<int[][]> packet = getRandomFrames(frameWidth, frameHeight, framesPerPacket);
            this.channel.send(packet);

            packetsCreated++;
            if (packetsCreated > numPackets) {
                canStream = false;
            }

            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public ArrayList<int[][]> getRandomFrames(int width, int height, int n) {
        ArrayList<int[][]> out = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            out.add(randomFrame(width, height));
        }
        return out;
    }

    public int[][] randomFrame(int width, int height) {
        int[][] frame = new int[height][width];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                frame[r][c] = (int) (Math.random() * Integer.MAX_VALUE);
            }
        }
        return frame;
    }

    public double renderTimePerPacket() {
        BufferedImage image = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_RGB);
        long start = System.currentTimeMillis();
        for (int i = 0; i < framesPerPacket; i++) {
            for (int r = 0; r < image.getHeight(); r++) {
                for (int c = 0; c < image.getWidth(); c++) {
                    image.setRGB(c, r, Integer.MAX_VALUE);
                }
            }
        }
        return System.currentTimeMillis() - start;
    }
}
