package ru.ifmo.se.weblab.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import ru.ifmo.se.weblab.dto.PointResponse;

import java.util.List;

public class PointRepository {
    private final EntityManagerFactory emf;

    public PointRepository() {
        this.emf = Persistence.createEntityManagerFactory("weblab");
    }

    public void save(List<PointResponse> points) {
        try (EntityManager entityManager = emf.createEntityManager();) {
            entityManager.getTransaction().begin();
            for (PointResponse point : points) {
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
        }
    }

    public List<PointResponse> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                    "SELECT p FROM PointResponse p ORDER BY p.id DESC",
                    PointResponse.class
            ).getResultList();
        }
    }

    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}