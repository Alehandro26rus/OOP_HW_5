package notebook.model.repository.impl;

import notebook.model.dao.impl.FileOperation;
import notebook.util.mapper.impl.UserMapper;
import notebook.model.User;
import notebook.model.repository.GBRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final FileOperation operation;


    public UserRepository(FileOperation operation) {
        this.mapper = new UserMapper();
        this.operation = operation;
    }


    @Override
    public List<User> findAll() {
        List<String> lines = operation.readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
        List<User> users = findAll();
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id){
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }
    public void addMultipleUsers(List<User> users) {
        List<User> data = findAll();
        long max = 0L;
        for (User u : data) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        if (!users.isEmpty()) {
            for (User user : users) {
                user.setId(next);
                next++;
                data.add(user);
            }
            write(data);
            //следующему сообщению место не здесь а во View
            System.out.println("Новые пользователи успешно добавлены в файл");
        }
    }

    @Override
    public void deleteUser(long userId) {
        List<User> users = findAll();
        User editUser = users.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst().orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        users.remove(editUser);
        write(users);
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> update(Long userId, User update) {
        List<User> users = findAll();
        User editUser = users.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst().orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        editUser.setFirstName(update.getFirstName());
        editUser.setLastName(update.getLastName());
        editUser.setPhone(update.getPhone());
        write(users);
        return Optional.of(update);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u: users) {
            lines.add(mapper.toInput(u));
        }
        operation.saveAll(lines);
    }
}