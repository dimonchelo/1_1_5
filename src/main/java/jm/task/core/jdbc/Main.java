package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();

        userService.saveUser("name1", "lastName1", (byte) 1);
        userService.saveUser("name2", "lastName2", (byte) 2);
        userService.saveUser("name3", "lastName3", (byte) 3);
        userService.saveUser("name4", "lastName4", (byte) 4);

        userService.removeUserById(3);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
