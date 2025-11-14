package ru.ifmo.se.api.entities;

public interface ShotEntityVisitor<R> {
    R visit(ShotEntity shotEntity);
    R visit(RevolverShotEntity shotEntity);
    R visit(ShotgunShotEntity shotgunEntity);
}
