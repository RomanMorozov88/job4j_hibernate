package todolist.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import todolist.persistence.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Работа с БД.
 */
public class DBStore implements Store {

    private final static DBStore INSTANCE = new DBStore();
    private final static SessionFactory FACTORY = new Configuration()
            .configure()
            .buildSessionFactory();

    private DBStore() {
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Item> getToDo() {
        return this.tx(
                session -> {
                    List<Item> result = session.createQuery("from Item").list();
                    return result;
                }
        );
    }

    @Override
    public Item findById(int id) {
        return this.tx(
                session -> {
                    Item result = null;
                    Query query = session.createQuery("FROM Item WHERE id = :parameterId");
                    query.setParameter("parameterId", id);
                    List<Item> buffer = query.list();
                    if (buffer.size() > 0) {
                        result = buffer.get(0);
                    }
                    return result;
                }
        );
    }

    @Override
    public void update(Item item) {
        this.tx(session -> {
            session.saveOrUpdate(item);
        });
    }

    /**
     * Закрывает sessionFactory.
     */
    @Override
    public void close() {
        FACTORY.close();
    }

    /**
     * Метод, содержащий в себе одинаковый для всех методов код.
     * @param command
     * @param <T>
     * @return
     */
    private <T> T tx(final Function<Session, T> command) {
        final Session session = FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            T result = command.apply(session);
            transaction.commit();
            return result;
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Перегруженный void метод.
     * @param command
     */
    private void tx(final Consumer<Session> command) {
        final Session session = FACTORY.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}