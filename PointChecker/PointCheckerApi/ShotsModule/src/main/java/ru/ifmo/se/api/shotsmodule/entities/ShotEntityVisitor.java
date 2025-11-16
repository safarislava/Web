package ru.ifmo.se.api.shotsmodule.entities;

public interface ShotEntityVisitor<R> {
    R visit(ShotEntity shotEntity);
    R visit(RevolverShotEntity shotEntity);
    R visit(ShotgunShotEntity shotgunEntity);
}
