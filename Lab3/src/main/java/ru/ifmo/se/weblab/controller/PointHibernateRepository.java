package ru.ifmo.se.weblab.controller;


import ru.ifmo.se.weblab.dto.PointResponse;
import ru.ifmo.se.weblab.utils.PointComparator;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PointHibernateRepository implements PointRepository {
    private EntityManagerFactory emf;
    private EntityManager entityManager;

    public void guarantyConnection() {
        if (emf == null || !emf.isOpen()) emf = Persistence.createEntityManagerFactory("weblab");
        if (entityManager == null || !entityManager.isOpen()) entityManager = emf.createEntityManager();
    }

    @Override
    public void save(List<PointResponse> points) {
        guarantyConnection();

        try {
            entityManager.getTransaction().begin();
            for (PointResponse point : points) {
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
        }
        catch (Exception e) {
            close();
        }
    }

    @Override
    public List<PointResponse> findAll() {
        guarantyConnection();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        try {
            CriteriaQuery<PointResponse> criteriaQuery = criteriaBuilder.createQuery(PointResponse.class);
            Root<PointResponse> circleRoot = criteriaQuery.from(PointResponse.class);
            criteriaQuery.select(circleRoot);
            TypedQuery<PointResponse> query = entityManager.createQuery(criteriaQuery);
            List<PointResponse> points = new ArrayList<>(query.getResultList());
            points.sort(new PointComparator());
            return points;
        }
        catch (Exception e) {
            close();
            return new ArrayList<>();
        }
    }

    @Override
    public void close() {
        if (entityManager != null && !entityManager.isOpen()) {
            entityManager.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}