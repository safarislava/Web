package ru.ifmo.se.api.pointchecker.database;


import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import ru.ifmo.se.api.pointchecker.entity.Shot;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ShotHibernateRepository implements ShotRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(List<Shot> shots) {
        for (Shot shot : shots) {
            entityManager.persist(shot);
        }
    }

    @Override
    public List<Shot> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Shot> criteriaQuery = criteriaBuilder.createQuery(Shot.class);
        Root<Shot> circleRoot = criteriaQuery.from(Shot.class);
        criteriaQuery.select(circleRoot);
        TypedQuery<Shot> query = entityManager.createQuery(criteriaQuery);
        return new ArrayList<>(query.getResultList());
    }
}