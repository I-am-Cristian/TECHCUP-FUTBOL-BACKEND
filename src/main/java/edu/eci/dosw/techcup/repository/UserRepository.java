package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findById(String name);
}
