package ru.ifmo.se.api.service.utils;

import ru.ifmo.se.api.service.entities.ShotEntity;

import java.util.Comparator;

public class ShotComparator implements Comparator<ShotEntity> {
    @Override
    public int compare(ShotEntity o1, ShotEntity o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
