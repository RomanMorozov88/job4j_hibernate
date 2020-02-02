package car_catalog.services.daos;

import car_catalog.models.Transmission;
import car_catalog.services.DBWorker;

import java.util.List;

public class TransmissionDAO implements BasicDAO<Transmission> {

    private final DBWorker worker = DBWorker.getInstance();

    @Override
    public boolean addEntity(Transmission adding) {
        return this.worker.sessionFunc(session -> {
            session.saveOrUpdate(adding);
            return true;
        });
    }

    @Override
    public Transmission getEntityById(int id) {
        return this.worker.sessionFunc(session -> {
            Transmission result = session.get(Transmission.class, id);
            return result;
        });
    }

    @Override
    public List<Transmission> getEntities() {
        return this.worker.sessionFunc(session -> {
            List<Transmission> result = session.createQuery("from Transmission").list();
            return result;
        });
    }

    @Override
    public void deleteEntity(int id) {
        this.worker.sessionFunc(session -> {
            session.delete(new Transmission(id));
        });
    }
}
