package ru.ifmo.se.api.shotsmodule.models;

public interface ShotVisitor<R> {
    R visit(Shot shot);
    R visit(RevolverShot shot);
    R visit(ShotgunShot shot);
}
