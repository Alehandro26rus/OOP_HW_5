package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;
import java.util.Objects;

import notebook.util.DataEntry;


public class UserController {
    private final GBRepository repository;
    private final DataEntry input;

    public UserController(GBRepository repository) {
        this.repository = repository;
        this.input = new DataEntry();
    }

    public void saveUser(User user) {
        repository.create(user);
    }

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public void deleteUser(String id) {
        repository.delete(Long.parseLong(id));
    }

    // public User createUser() {
    //     String firstName = input.prompt("Имя: ");
    //     String lastName = input.prompt("Фамилия: ");
    //     String phone = input.prompt("Номер телефона: ");
    //     return new User(firstName, lastName, phone);
    // }

    public User createUser() {
        String firstName = input.prompt("Имя: ");
        String lastName = input.prompt("Фамилия: ");
        String phone = input.prompt("Номер телефона: ");
        return repository.create(new User(firstName, lastName, phone));
    }

    public List<User> readAll() {
        return repository.findAll();
    }
}