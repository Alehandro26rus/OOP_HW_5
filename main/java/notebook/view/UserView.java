package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.model.repository.impl.UserRepository;
import notebook.util.Commands;
import notebook.util.DataEntry;



public class UserView {
    private final UserController userController;
    private final DataEntry input;

    public UserView(UserController userController) {
        this.userController = userController;
        this.input = new DataEntry();
    }

    public void run() {
        Commands com;

        while (true) {
            String command = input.prompt("Введите команду: ");
            com = Commands.valueOf(command.toUpperCase());
            if (com == Commands.EXIT)
                return;
            switch (com) {
                case DELETE:
                    String usId = input.prompt("Enter user id: ");
                    userController.deleteUser(usId);
                    break;
                case LIST:
                    System.out.println(userController.readAll());
                    break;
                case CREATE:
                    userController.createUser();
                    break;
                case READ:
                    String id = input.prompt("Идентификатор пользователя: ");
                    try {
                        User user = userController.readUser(Long.parseLong(id));
                        System.out.println(user);
                        System.out.println();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case UPDATE:
                    String userId = input.prompt("Enter user id: ");
                    userController.updateUser(userId, userController.createUser());
                    break;
            }
        }
    }
}