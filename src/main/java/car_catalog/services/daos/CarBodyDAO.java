package car_catalog.services.daos;

import car_catalog.models.CarBody;
import car_catalog.services.DBWorker;

import java.util.List;

public class CarBodyDAO implements BasicDAO<CarBody> {

    private final DBWorker worker = DBWorker.getInstance();

    @Override
    public boolean addEntity(CarBody adding) {
        return this.worker.sessionFunc(session -> {
            session.saveOrUpdate(adding);
            return true;
        });
    }

    @Override
    public CarBody getEntityById(int id) {
        return this.worker.sessionFunc(session -> {
            CarBody result = session.get(CarBody.class, id);
            return result;
        });
    }

    @Override
    public List<CarBody> getEntities() {
        return this.worker.sessionFunc(session -> {
            List<CarBody> result = session.createQuery("from CarBody").list();
            return result;
        });
    }

    @Override
    public void deleteEntity(int id) {
        this.worker.sessionFunc(session -> {
            session.delete(new CarBody(id));
        });
    }

}