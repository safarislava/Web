package ru.ifmo.se.weblab.controller;


import ru.ifmo.se.weblab.dto.PointResponse;
import ru.ifmo.se.weblab.dto.ShapablePointResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PointHibernateRepository implements PointRepository {
    private final EntityManagerFactory emf;

    public PointHibernateRepository() {
        this.emf = Persistence.createEntityManagerFactory("weblab");
    }

    @Override
    public void save(List<ShapablePointResponse> points) {
        try {
            EntityManager entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            for (PointResponse point : points) {
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        }
        catch (Exception e) {
            close();
        }
    }

    @Override
    public List<ShapablePointResponse> findAll() {
        try {
            EntityManager entityManager = emf.createEntityManager();
            TypedQuery<ShapablePointResponse> query = entityManager.createQuery(
                    "SELECT s FROM ShapablePointResponse s", ShapablePointResponse.class);

            List<ShapablePointResponse> points = query.getResultList();
            entityManager.close();
            return points;
        }
        catch (Exception e) {
            e.printStackTrace();
            close();
            return new ArrayList<>();
        }
    }

    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}