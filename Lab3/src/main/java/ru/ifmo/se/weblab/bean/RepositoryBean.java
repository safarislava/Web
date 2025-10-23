package ru.ifmo.se.weblab.bean;

import ru.ifmo.se.weblab.controller.PointHibernateRepository;
import ru.ifmo.se.weblab.controller.PointRepository;
import ru.ifmo.se.weblab.entity.Point;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.List;

@ManagedBean(name="repositoryBean", eager = true)
@SessionScoped
public class RepositoryBean {
    private final PointRepository pointRepository = new PointHibernateRepository();

    public void save(List<Point> points) {
        pointRepository.save(points);
    }

    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }
}
