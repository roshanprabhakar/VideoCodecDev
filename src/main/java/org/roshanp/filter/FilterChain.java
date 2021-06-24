package org.roshanp.filter;

/**
 * writeen for the successive chaining of multiple filters
 */
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
