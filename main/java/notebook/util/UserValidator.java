package notebook.util;

import notebook.model.User;

public class UserValidator {
    public User validate(User user) {
        String name = user.getFirstName().replaceAll(" ", "").trim();
        String lastname = user.getLastName().replaceAll(" ", "").trim();
        user.setFirstName(name);
        user.setLastName(lastname);
        return user;
    }
}