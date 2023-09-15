package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate implements AccidentRepository {

    private final SessionFactory sf;

    @Override
    public Collection<Accident> getAllAccidents() {
        try (Session session = sf.openSession()) {
            return session
                    .createQuery("FROM Accident ORDER BY id", Accident.class)
                    .list();
        }
    }

    @Override
    public Accident addAccident(Accident accident) {
        try (Session session = sf.openSession()) {
            Transaction tr = session.beginTransaction();
            session.persist(accident);
            tr.commit();
            return accident;
        }
    }

    @Override
    public boolean editAccident(Accident accident) {
        try (Session session = sf.openSession()) {
            Transaction tr = session.beginTransaction();
            session.merge(accident);
            tr.commit();
            return true;
        }
    }

    @Override
    public Optional<Accident> getAccident(int id) {
        try (Session session = sf.openSession()) {
            return session.createQuery(
                    "FROM Accident a JOIN FETCH a.rules WHERE a.id = :id",
                    Accident.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        }
    }
}
