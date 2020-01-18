package simpleexample.services;

import simpleexample.models.User;
import simpleexample.models.UserDAO;

import java.util.List;

/**
 * Простая демонстрация работы с БД через hibernate.
 */
public class UserStorage {
    public static void main(String[] args) {

        UserDAO userDAO = new UserDAO();
        User user = new User();
        User buffer = null;
        user.setId(3);
        user.setName("3_name");

        userDAO.addUser(user);
        buffer = userDAO.findUser(user.getId());
        System.out.println(buffer.getId() + " : " + buffer.getName());

        user.setName("New_3_name");
        userDAO.updateUser(user);
        buffer = userDAO.findUser(user.getId());
        System.out.println(buffer.getId() + " : " + buffer.getName());

        userDAO.deleteUser(user);
        buffer = userDAO.findUser(user.getId());
        if (buffer == null) {
            System.out.println("Deleted.");
        } else {
            System.out.println("Error.");
        }

        List<User> list = userDAO.findAllUsers();
        for (User u : list) {
            System.out.println(" * * *");
            System.out.println(u.getId() + " : " + u.getName());
        }

    }
}
