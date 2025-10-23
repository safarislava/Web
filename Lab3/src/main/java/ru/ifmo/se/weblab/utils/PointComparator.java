package ru.ifmo.se.weblab.utils;

import ru.ifmo.se.weblab.entity.Point;

import java.util.Comparator;

public class PointComparator implements Comparator<Point> {
    @Override
    public int compare(Point o1, Point o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
