package com.roshanprabhakar;

import java.util.ArrayList;

public abstract class Codec {

    public abstract int[] encode(ArrayList<int[][]> frames);
    public abstract ArrayList<int[][]> decode(int[] frames);
}
