package ru.ifmo.se.weblab.utils;

import ru.ifmo.se.weblab.dto.PointResponse;

import java.util.Comparator;

public class PointComparator implements Comparator<PointResponse> {
    @Override
    public int compare(PointResponse o1, PointResponse o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
