package car_catalog.services.daos;

import car_catalog.models.Owner;
import car_catalog.services.DBWorker;
import org.hibernate.Hibernate;

import java.util.Iterator;
import java.util.List;

public class OwnerDAO implements BasicDAO<Owner> {

    private final DBWorker worker = DBWorker.getInstance();

    @Override
    public boolean addEntity(Owner adding) {
        return this.worker.sessionFunc(session -> {
            session.saveOrUpdate(adding);
            return true;
        });
    }

    @Override
    public Owner getEntityById(int id) {
        return this.worker.sessionFunc(session -> {
            Owner result = session.get(Owner.class, id);
            Hibernate.initialize(result.getCars());
            return result;
        });
    }

    @Override
    public List<Owner> getEntities() {
        return this.worker.sessionFunc(session -> {
            List<Owner> result = session.createQuery("from Owner").list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Owner buffer = (Owner) it.next();
                Hibernate.initialize(buffer.getCars());
            }
            return result;
        });
    }

    @Override
    public void deleteEntity(int id) {
        this.worker.sessionFunc(session -> {
            session.delete(new Owner(id));
        });
    }
}