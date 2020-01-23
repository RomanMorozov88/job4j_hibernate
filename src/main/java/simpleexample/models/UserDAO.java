package simpleexample.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Класс для взаимодействия с БД через hibernate.
 */
public class UserDAO {

    private SessionFactory factory;

    public UserDAO(SessionFactory factory) {
        this.factory = factory;
    }

    public void addUser(User user) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(User user) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteUser(User user) {
        Session session = factory.openSession();
        session.beginTransaction();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    public User findUser(int id) {
        User result = null;
        List<User> buffer = null;
        Session session = factory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM User WHERE id = :parameterId");
        query.setParameter("parameterId", id);
        buffer = query.list();
        if (buffer.size() > 0) {
            result = buffer.get(0);
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<User> findAllUsers() {
        List<User> result = null;
        Session session = factory.openSession();
        session.beginTransaction();
        result = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

}