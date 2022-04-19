package com.github.andriiyan.spring_data_access.impl.utils;

import java.util.stream.Stream;

/**
 * Utility class that simplify usage of the [Stream] class.
 */
public final class StreamUtils {

    private StreamUtils() {}

    /**
     * Returns stream of [pageSize] elements with offset of pageNumber pages.
     *
     * @param stream stream with data
     * @param pageNumber number of page from which data will be retrieved
     * @param pageSize size of single page
     * @return paged stream
     */
    public static <T> Stream<T> paging(Stream<T> stream, int pageNumber, int pageSize) {
        return stream.skip((long) pageNumber * pageSize)
                .limit(pageSize);
    }

}
