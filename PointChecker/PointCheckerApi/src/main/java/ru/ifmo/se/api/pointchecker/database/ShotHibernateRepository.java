package ru.ifmo.se.api.pointchecker.database;


import jakarta.ejb.Stateless;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import ru.ifmo.se.api.pointchecker.entity.Shot;
import ru.ifmo.se.api.pointchecker.utils.ShotComparator;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ShotHibernateRepository implements ShotRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(List<Shot> shots) {
        try {
            for (Shot shot : shots) {
                entityManager.persist(shot);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Shot> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        try {
            CriteriaQuery<Shot> criteriaQuery = criteriaBuilder.createQuery(Shot.class);
            Root<Shot> circleRoot = criteriaQuery.from(Shot.class);
            criteriaQuery.select(circleRoot);
            TypedQuery<Shot> query = entityManager.createQuery(criteriaQuery);
            List<Shot> shots = new ArrayList<>(query.getResultList());
            shots.sort(new ShotComparator());
            return shots;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }
}