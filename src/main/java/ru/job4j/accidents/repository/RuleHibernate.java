package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@AllArgsConstructor
public class RuleHibernate implements RuleRepository {

    private final SessionFactory sf;

    @Override
    public Rule getRule(int id) {
        try (Session session = sf.openSession()) {
            return session.get(Rule.class, id);
        }
    }

    @Override
    public void addRule(Rule rule) {
        try (Session session = sf.openSession()) {
            Transaction tr = session.beginTransaction();
            session.persist(rule);
            tr.commit();
        }
    }

    @Override
    public Collection<Rule> getAllRules() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM Rule", Rule.class)
                    .list();
        }
    }

    @Override
    public Set<Rule> getRulesByIds(String[] ids) {
        try (Session session = sf.openSession()) {
            return new HashSet<>(session.createQuery("FROM Rule WHERE id IN :ids", Rule.class)
                    .setParameter("ids", Arrays.stream(ids).map(Integer::parseInt).toList())
                    .list());
        }
    }
}
