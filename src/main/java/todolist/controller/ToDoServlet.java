package todolist.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import todolist.persistence.model.Item;
import todolist.service.DBStore;
import todolist.service.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDoServlet extends HttpServlet {

    private final Store store = DBStore.getInstance();

    /**
     *
     * В зависимости от значения параметра flag (true/false)
     * возвращает либо список невыполненных дел либо всех дел.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean flag = Boolean.parseBoolean(req.getParameter("flag"));
        List<Item> result = new ArrayList<>();
        for (Item i : store.getToDo()) {
            if (flag) {
                result.add(i);
            } else if (!i.isDone()) {
                result.add(i);
            }
        }
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        writer.append(mapper.writeValueAsString(result));
        writer.flush();
    }

    /**
     * В зависимоси от принимемой JSON-строки
     * либо добавляет новую запись
     * либо редактирует статус выполнения у уже существующей.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String string = null;
        Item item = null;
        try (BufferedReader br = req.getReader()) {
            while ((string = br.readLine()) != null) {
                sb.append(string);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            item = objectMapper.readValue(sb.toString(), Item.class);
        }

        if (item.getId() == null) {
            item.setCreated(LocalDateTime.now());
            store.update(item);
        } else {
            Item buffer = store.findById(item.getId());
            buffer.setDone(item.isDone());
            store.update(buffer);
        }
    }

    @Override
    public void destroy() {
        store.close();
        super.destroy();
    }
}