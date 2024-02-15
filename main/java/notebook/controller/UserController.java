package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;
import java.util.Objects;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {
        this.repository = repository;
    }

    public void saveUser(User user) {
        repository.create(user);
    }
    public void saveUsers(List<User> users) {repository.addMultipleUsers(users);}

    public User readUser(Long userId) throws Exception {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("Запись не найдена");
    }
//    public List<String> readAll() {
//        return repository.readAll();
//    }

    public void updateUser(String userId, User update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public void deleteUser(Long userId) {
        repository.deleteUser(Long.parseLong(String.valueOf(userId)));
    }

    public List<User> readAll() {
        return repository.findAll();
    }
}