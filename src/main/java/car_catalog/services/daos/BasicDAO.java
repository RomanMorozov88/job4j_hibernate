package car_catalog.services.daos;

import java.util.List;

/**
 * Интерфейс для взаимодействия с БД.
 * Для работы с разными сущностями создаётся соответствующая реализация.
 * @param <T>
 */
public interface BasicDAO<T> {

    boolean addEntity(T adding);

    T getEntityById(int id);

    List<T> getEntities();

    void deleteEntity(int id);
}
