package car_catalog.services.daos;

import car_catalog.models.Engine;
import car_catalog.services.DBWorker;

import java.util.List;

public class EngineDAO implements BasicDAO<Engine> {

    private final DBWorker worker = DBWorker.getInstance();

    @Override
    public boolean addEntity(Engine adding) {
        return this.worker.sessionFunc(session -> {
            session.saveOrUpdate(adding);
            return true;
        });
    }

    @Override
    public Engine getEntityById(int id) {
        return this.worker.sessionFunc(session -> {
            Engine result = session.get(Engine.class, id);
            return result;
        });
    }

    @Override
    public List<Engine> getEntities() {
        return this.worker.sessionFunc(session -> {
            List<Engine> result = session.createQuery("from Engine ").list();
            return result;
        });
    }

    @Override
    public void deleteEntity(int id) {
        this.worker.sessionFunc(session -> {
            session.delete(new Engine(id));
        });
    }
}
