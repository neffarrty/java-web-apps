package se.semit.ykovtun.webappskyvlab4.services.user;

import se.semit.ykovtun.webappskyvlab4.entities.User;

public interface UserService {
    void registerUser(User user);
    User findByUsername(String username);
}
