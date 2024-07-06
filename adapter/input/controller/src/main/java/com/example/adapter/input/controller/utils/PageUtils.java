package com.example.adapter.input.controller.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtils {

    private PageUtils() {}

    public static <T, D> Page<D> toPage(List<T> items,
                                        Pageable pageable,
                                        long totalElements,
                                        Function<T, D> dtoClass,
                                        String orderBy,
                                        Class<D> dtoClassType) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<D> itemList;

        if (items.size() < startItem) {
            itemList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, items.size());
            List<T> subList = items.subList(startItem, toIndex);

            itemList = subList.stream().map(dtoClass).collect(Collectors.toList());

            Sort sort = pageable.getSort();
            if (sort.isSorted()) {
                boolean isDescending = sort.get().anyMatch(order -> order.getDirection() == Sort.Direction.DESC);
                Comparator<D> comparator = createComparator(dtoClassType, orderBy, isDescending);
                itemList.sort(comparator);
            }
        }

        return new PageImpl<>(itemList, pageable, totalElements);
    }

    @SuppressWarnings("unchecked")
    private static <D> Comparator<D> createComparator(Class<D> dtoClass, String orderBy, boolean isDescending) {
        Comparator<D> comparator;
        try {
            Method getterMethod = dtoClass.getMethod(getGetterMethodName(orderBy));
            comparator = (o1, o2) -> {
                try {
                    Comparable<Object> value1 = (Comparable<Object>) getterMethod.invoke(o1);
                    Comparable<Object> value2 = (Comparable<Object>) getterMethod.invoke(o2);
                    return value1.compareTo(value2);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException("Failed to invoke getter method", e);
                }
            };
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Invalid orderBy value: " + orderBy, e);
        }

        if (isDescending) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private static String getGetterMethodName(String property) {
        return "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }
}


