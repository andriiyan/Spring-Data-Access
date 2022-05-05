package com.github.andriiyan.spring_data_access.impl.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ListUtils {

    private ListUtils() {}

    public static String joinToString(Iterable<String> items, char separator) {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<String> iterator = items.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next());
            if (iterator.hasNext()) {
                stringBuilder.append(separator);
            }
        }
        return stringBuilder.toString();
    }

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
