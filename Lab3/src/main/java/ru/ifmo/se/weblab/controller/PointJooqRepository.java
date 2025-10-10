package ru.ifmo.se.weblab.controller;

import org.jooq.DSLContext;
import org.jooq.Result;
import ru.ifmo.se.weblab.dto.PointResponse;
import ru.ifmo.se.weblab.jooq.DatabaseConfig;
import ru.ifmo.se.weblab.jooq.tables.Points;
import ru.ifmo.se.weblab.jooq.tables.records.PointsRecord;

import java.util.ArrayList;
import java.util.List;

public class PointJooqRepository implements PointRepository {
    private final DSLContext dsl;

    public PointJooqRepository() {
        try {
            this.dsl = DatabaseConfig.createDSLContext();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DSLContext", e);
        }
    }

    @Override
    public void save(List<PointResponse> points) {
        Points table = Points.POINTS;

        var batchRecords = points.stream()
                .map(point -> {
                    PointsRecord record = dsl.newRecord(table);
                    record.setStatus(point.getStatus());
                    record.setX(point.getX());
                    record.setY(point.getY());
                    record.setR(point.getR());
                    record.setIspointinarea(point.getIsPointInArea());
                    record.setDeltatime(point.getDeltaTime());
                    record.setTime(point.getTime());
                    return record;
                })
                .toList();

        dsl.batchStore(batchRecords).execute();
    }

    @Override
    public List<PointResponse> findAll() {
        Points table = Points.POINTS;
        Result<PointsRecord> result = dsl.selectFrom(table).fetch();

        List<PointResponse> points = new ArrayList<>();
        for (PointsRecord record : result) {
            PointResponse point = new PointResponse();
            point.setId(record.getId());
            point.setStatus(record.getStatus());
            point.setX(record.getX());
            point.setY(record.getY());
            point.setR(record.getR());
            point.setPointInArea(record.getIspointinarea());
            point.setDeltaTime(record.getDeltatime());
            point.setTime(record.getTime());
            points.add(point);
        }
        return points;
    }

    @Override
    public void close() throws Exception {

    }
}
