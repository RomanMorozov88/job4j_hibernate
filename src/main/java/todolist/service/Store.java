package todolist.service;

import todolist.persistence.model.Item;

import java.util.List;

/**
 * Простой интерфейс для работы с хранилищем данных.
 */
public interface Store {
    /**
     * Возвращает список дел-
     */
    List<Item> getToDo();

    /**
     * Возвращает позицию по id.
     *
     * @param id
     * @return
     */
    Item findById(int id);

    /**
     * Добавляет новую\изменяет существующую запись.
     *
     * @param item
     */
    void update(Item item);

    void close();

}