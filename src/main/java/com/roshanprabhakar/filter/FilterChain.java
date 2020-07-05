package com.roshanprabhakar.filter;

public class FilterChain extends Filter {

    private Filter[] filters;

    public FilterChain(Filter[] filters) {
        this.filters = filters;
    }

    @Override
    public int[][] filter(int[][] frame) {
        for (int i = 0; i < filters.length; i++) {
            frame = filters[i].filter(frame);
        }
        return frame;
    }
}
