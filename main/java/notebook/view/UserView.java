package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;
import notebook.util.UserValidator;
import notebook.util.mapper.impl.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static notebook.util.ApplicationRunner.HASH;


public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run(){
        Commands com;

        while (true) {
            String command = prompt("Введите команду: ").toUpperCase();
            com = Commands.valueOf(command);
            if (com == Commands.EXIT) return;
            switch (com) {
                case LIST:
                    System.out.println(userController.readAll());
                    break;
                case CREATE:
                    User u = createUser();
                    userController.saveUser(u);
                    break;
                case ADD_ALL:
                    userController.saveUsers((prepareListOfUserForRecording()));
                    break;
                case READ:
                    String id = prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
//                case READ_ALL:
//                    System.out.println(prepareUserForOutput(UserController.readAll()));
//                    break;
                case UPDATE:
                    String userId = prompt("Введите id записи: ");
                    userController.updateUser(userId, createUser());
                    break;
                case DELETE:
                    userId = prompt("Введите id записи: ");
                    userController.deleteUser(Long.valueOf(userId));
                    break;
            }
        }
    }
    private List<String> prepareUserForOutput(List<String> data) {
        List<String> result = new ArrayList<>();
        for (String datum : data) {
            String[] split = datum.split(",");
            User u = new User(Long.parseLong(split[0]), split[1], split[2], split[3]);
            result.add("\n" + u);
        }
        result.add("\n");
        return result;
    }
    private List<User> prepareListOfUserForRecording() {
        List<User> users = new ArrayList<>();
        String input = prompt("Вводите данные пользователей.\n Чтобы продолжить, введите y\n +" +
                "Для того, чтобы закончить, введите q\n");
        if (input.equalsIgnoreCase("q")) {
            return users;
        }
        while (input.equalsIgnoreCase("v")) {
            try {
                User user = createUser();
                users.add(user);
                input = prompt("Введите данные пользователей.\n Чтобы продолжить, введите y\n " +
                        "Для того, чтобы закончить, введите q\n");
            } catch (UnsupportedOperationException e) {
                System.out.println("Пользователь не добавлен, причинаЖ " + e.getMessage());
                prepareListOfUserForRecording();
            }
        }
        return users;
    }
    private String getAvailableCommands() {
        return String.format("Список доступных командЖ%n" +
                " create - создать пользователя\n" +
                " read - получить данные пользователя по id%n" +
                " read_all - получить данные всех пользователей%n" +
                " update - обновить информацию о пользователе по id%n" +
                " delete - удалить пользователя по id%n" +
                " add_all - добавить нескольких пользователей%n" +
                " list - список доступных команд%n" +
                " exit - выход из приложения с сохранением данных в файл%n");
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private User createUser() {
        UserValidator validator = new UserValidator();
        String firstName = validator.validateFirstName(prompt("Имя: "));
        String lastName = validator.validateLastName(prompt("Фамилия: "));
        String phone = validator.validatePhone(prompt("Номер телефона: "));
        return new User(firstName, lastName, phone);
    }
}