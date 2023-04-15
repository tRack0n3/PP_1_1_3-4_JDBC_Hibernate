package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        // Создание таблицы User(ов)
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор.
        userService.saveUser("James", "Gordon", (byte) 23);
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Bob", "Brown", (byte) 25);
        userService.saveUser("John", "Snow", (byte) 35);

        //Получение всех User из базы и вывод в консоль
        userService.getAllUsers();

        //Очистка таблицы User(ов)
        userService.cleanUsersTable();

        //Удаление таблицы
        userService.dropUsersTable();

    }
}
