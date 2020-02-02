package car_catalog.services.daos;

import car_catalog.models.Car;
import car_catalog.services.DBWorker;
import org.hibernate.Hibernate;

import java.util.Iterator;
import java.util.List;

/**
 * Простая модель. Маппинг в Car.hbm.xml
 * Т.к. по умолчанию инициализация стоит lazy=true
 * то для избежания ошибки lazyinitializationexception
 * в методах .getEntityById(int id) и .getEntities()
 * оборачиваем "ленивые" поля(!) в Hibernate.initialize()
 * для их подгрузки по мере необходимости.
 * То же самое касается и класса Owner.
 * В классах Engine, CarBody, Transmission в этом нет
 * необходимости- это простые классы без полей-сложных-объектов.
 */
public class CarDAO implements BasicDAO<Car> {

    private final DBWorker worker = DBWorker.getInstance();

    @Override
    public boolean addEntity(Car adding) {
        return this.worker.sessionFunc(session -> {
            session.saveOrUpdate(adding);
            return true;
        });
    }

    @Override
    public Car getEntityById(int id) {
        return this.worker.sessionFunc(session -> {
            Car result = session.get(Car.class, id);
            Hibernate.initialize(result.getEngine());
            Hibernate.initialize(result.getCarBody());
            Hibernate.initialize(result.getTransmission());
            Hibernate.initialize(result.getOwners());
            return result;
        });
    }

    @Override
    public List<Car> getEntities() {
        return this.worker.sessionFunc(session -> {
            List<Car> result = session.createQuery("from Car").list();
            Iterator it = result.iterator();
            while (it.hasNext()) {
                Car buffer = (Car) it.next();
                Hibernate.initialize(buffer.getOwners());
                Hibernate.initialize(buffer.getEngine());
                Hibernate.initialize(buffer.getCarBody());
                Hibernate.initialize(buffer.getTransmission());
            }
            return result;
        });
    }

    @Override
    public void deleteEntity(int id) {
        this.worker.sessionFunc(session -> {
            session.delete(new Car(id));
        });
    }
}