package com.github.andriiyan.spring_data_access.impl.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamUtilsTest {

    @Test
    public void paging_should_do_offset_and_limit() {
        final Collection<Integer> originalCollection = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Stream<Integer> stream = originalCollection.stream();

        Assert.assertEquals(
                Arrays.asList(1, 2, 3, 4, 5),
                StreamUtils.paging(stream, 0, 5).collect(Collectors.toList())
        );

       stream = originalCollection.stream();

        Assert.assertEquals(
                Arrays.asList(6, 7, 8, 9, 10),
                StreamUtils.paging(stream, 1, 5).collect(Collectors.toList())
        );
    }

}