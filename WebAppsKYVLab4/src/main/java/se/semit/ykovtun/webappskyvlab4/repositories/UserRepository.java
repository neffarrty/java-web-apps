package se.semit.ykovtun.webappskyvlab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.semit.ykovtun.webappskyvlab4.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
