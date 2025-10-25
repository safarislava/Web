package ru.ifmo.se.api.pointchecker.utils;

import ru.ifmo.se.api.pointchecker.entity.Shot;

import java.util.Comparator;

public class ShotComparator implements Comparator<Shot> {
    @Override
    public int compare(Shot o1, Shot o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
