package com.studentmanagement.service;

import com.studentmanagement.exception.AuthenticationException;
import com.studentmanagement.exception.InvalidDataException;
import com.studentmanagement.model.User;
import com.studentmanagement.repository.DataStore;

import java.util.Optional;

/**
 * Service for handling User Authentication & Change Password (Video #2, #3).
 */
public class AuthService {

    private final DataStore dataStore;
    private User currentUser;

    public AuthService() {
        this.dataStore = DataStore.getInstance();
    }

    public User login(String username, String password) throws AuthenticationException {
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Tên đăng nhập và mật khẩu không được để trống!");
        }

        Optional<User> optUser = dataStore.getUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username.trim()))
                .findFirst();

        if (!optUser.isPresent() || !optUser.get().getPassword().equals(password)) {
            throw new AuthenticationException("Tên đăng nhập hoặc mật khẩu không chính xác!");
        }

        this.currentUser = optUser.get();
        return currentUser;
    }

    public void changePassword(String oldPassword, String newPassword, String confirmPassword) throws InvalidDataException, AuthenticationException {
        if (currentUser == null) {
            throw new AuthenticationException("Bạn chưa đăng nhập vào hệ thống!");
        }

        if (!currentUser.getPassword().equals(oldPassword)) {
            throw new InvalidDataException("Mật khẩu hiện tại không chính xác!");
        }

        if (newPassword == null || newPassword.trim().length() < 4) {
            throw new InvalidDataException("Mật khẩu mới phải có ít nhất 4 ký tự!");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidDataException("Xác nhận mật khẩu mới không trùng khớp!");
        }

        currentUser.setPassword(newPassword);
        dataStore.saveUsers();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }
}
