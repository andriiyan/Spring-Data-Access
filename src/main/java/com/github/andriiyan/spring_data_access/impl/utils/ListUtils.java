package com.github.andriiyan.spring_data_access.impl.utils;

import java.util.ArrayList;
import java.util.List;

public final class ListUtils {

    private ListUtils() {}

    public static <T, S extends T> List<T> fromIterable(Iterable<S> iterable) {
        if (iterable == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(element);
        }
        return list;
    }

}
