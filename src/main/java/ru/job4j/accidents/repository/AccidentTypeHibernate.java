package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;

@AllArgsConstructor
public class AccidentTypeHibernate implements AccidentTypeRepository {

    private final SessionFactory sf;

    @Override
    public AccidentType getAccidentType(int id) {
        try (Session session = sf.openSession()) {
            return session.get(AccidentType.class, id);
        }
    }

    @Override
    public void addAccidentType(AccidentType accidentType) {
        try (Session session = sf.openSession()) {
            Transaction tr = session.beginTransaction();
            session.persist(accidentType);
            tr.commit();
        }
    }

    @Override
    public Collection<AccidentType> getAllAccidentTypes() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM AccidentType", AccidentType.class)
                    .list();
        }
    }
}
