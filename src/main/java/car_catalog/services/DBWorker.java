package car_catalog.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class DBWorker {

    private static final DBWorker INSTANCE = new DBWorker();
    private static final SessionFactory FACTORY = new Configuration()
            .configure("hibernateCarCatalog.cfg.xml")
            .buildSessionFactory();

    private DBWorker() {
    }

    public static DBWorker getInstance() {
        return INSTANCE;
    }

    public <T> T sessionFunc(final Function<Session, T> command) {
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

    public void sessionFunc(final Consumer<Session> command) {
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

    public void close() {
        FACTORY.close();
    }

}