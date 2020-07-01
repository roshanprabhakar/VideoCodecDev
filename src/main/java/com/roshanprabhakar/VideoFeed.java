package com.roshanprabhakar;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class VideoFeed extends Feed {

    private VideoCapture camera;
    private String filepath;

    public VideoFeed(VideoCapture camera, String filepath, Channel channel, int framesPerPacket) {
        super(channel,
                framesPerPacket,
                (int) (camera.get(Videoio.CAP_PROP_FRAME_COUNT) / framesPerPacket));

        this.camera = new VideoCapture();
        this.filepath = filepath;
        camera.open(filepath);
    }

    public void run() {

        camera = new VideoCapture();
        camera.open(filepath);

        Mat frame = new Mat();

        ArrayList<int[][]> packet = new ArrayList<>();
        while (camera.read(frame)) {

            packet.add(imageToIntArray(matToBufferedImage(frame)));

            if (packet.size() >= framesPerPacket) {
                channel.send(packet);
                packet = new ArrayList<>();
            }
        }
    }

    public int[][] imageToIntArray(BufferedImage image) {
        int[][] out = new int[image.getHeight()][image.getWidth()];
        for (int r = 0; r < image.getHeight(); r++) {
            for (int c = 0; c < image.getWidth(); c++) {
                out[r][c] = image.getRGB(c, r);
            }
        }
        return out;
    }

    public static int countFrames(String video) {
        VideoCapture camera = new VideoCapture();
        camera.open(video);

        int numFrames = 0;
        Mat mat = new Mat();

        while (camera.read(mat)) {
            numFrames++;
        }

        return numFrames;
    }

    private static BufferedImage matToBufferedImage(Mat frame) {
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }
}
