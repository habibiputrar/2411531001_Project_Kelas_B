package Util;

import DAO.UserRepo;
import Error.ValidationException;
import model.User;

public class ValidationUtilUser {

    public static void validate(User user) throws ValidationException, NullPointerException {

        if (user == null) {
            throw new NullPointerException("User object is null");
        }


        if (user.getNama() == null) {
            throw new NullPointerException("Name is null");
        } else if (user.getNama().isBlank()) {
            throw new ValidationException("Name is blank");
        }

        if (user.getUsername() == null) {
            throw new NullPointerException("Username is null");
        } else if (user.getUsername().isBlank()) {
            throw new ValidationException("Username is blank");
        }

        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        } else if (user.getPassword().isBlank()) {
            throw new ValidationException("Password is blank");
        }

        // Cek duplikasi username & password
        UserRepo repo = new UserRepo();

        if (repo.isUsernameExist(user.getUsername())) {
            throw new ValidationException("Username already exists in database");
        }

        if (repo.isPasswordExist(user.getPassword())) {
            throw new ValidationException("Password already exists in database");
        }
    }
    public static void validateForUpdate(User user) throws ValidationException, NullPointerException {
        if (user == null) {
            throw new NullPointerException("User object is null");
        }

        if (user.getNama() == null) {
            throw new NullPointerException("Name is null");
        } else if (user.getNama().isBlank()) {
            throw new ValidationException("Name is blank");
        }

        if (user.getUsername() == null) {
            throw new NullPointerException("Username is null");
        } else if (user.getUsername().isBlank()) {
            throw new ValidationException("Username is blank");
        }

        if (user.getPassword() == null) {
            throw new NullPointerException("Password is null");
        } else if (user.getPassword().isBlank()) {
            throw new ValidationException("Password is blank");
        }

        UserRepo repo = new UserRepo();

        if (repo.isUsernameExist(user.getUsername(), user.getId())) {
            throw new ValidationException("Username already exists in another user");
        }

        if (repo.isPasswordExist(user.getPassword(), user.getId())) {
            throw new ValidationException("Password already exists in another user");
        }
    }

}