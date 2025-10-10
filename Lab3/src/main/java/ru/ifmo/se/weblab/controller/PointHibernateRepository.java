package ru.ifmo.se.weblab.controller;


import ru.ifmo.se.weblab.dto.PointResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PointHibernateRepository implements PointRepository {
    private final EntityManagerFactory emf;

    public PointHibernateRepository() {
        this.emf = Persistence.createEntityManagerFactory("weblab");
    }

    @Override
    public void save(List<PointResponse> points) {
        try {
            EntityManager entityManager = emf.createEntityManager();
            entityManager.getTransaction().begin();
            for (PointResponse point : points) {
                entityManager.persist(point);
            }
            entityManager.getTransaction().commit();
        }
        finally {
            emf.close();
        }
    }

    @Override
    public List<PointResponse> findAll() {
        try {
            EntityManager em = emf.createEntityManager();
            return em.createQuery(
                    "SELECT p FROM PointResponse p ORDER BY p.id DESC", PointResponse.class).getResultList();
        }
        finally {
            emf.close();
        }
    }

    @Override
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}