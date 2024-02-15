package notebook.util;

import notebook.model.User;

public class UserValidator {
    public User validate(User user) {
// A N n a - допустим кто-то ввел такое имя. как превратить
// его в нормальное?
        String name = user.getFirstName().replaceAll(" ", "").trim();
        String lname = user.getLastName().replaceAll(" ", "").trim();
        user.setFirstName(name);
        user.setLastName(lname);
        return user;
    }
    public String validateFirstName(String firstName) {
        if (firstName.isEmpty())
            throw new UnsupportedOperationException("Поле имени должно быть заполнено");
        return firstName.trim().replace(" ", "");
    }
    public String validateLastName(String lastName) {
        if (lastName.isEmpty())
            throw new UnsupportedOperationException("Поле фамилии должно быть заполнено");
        return lastName.trim().replace(" ", "");
    }

    public String validatePhone(String phone) {
        if (!phone.matches("[\\d]+"))
            throw new UnsupportedOperationException("Номер телефона должен содержать только цифры");
        return phone.trim().replace(" ", "");
    }
}