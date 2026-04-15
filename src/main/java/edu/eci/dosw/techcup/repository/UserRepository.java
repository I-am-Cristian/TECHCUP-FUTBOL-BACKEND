package edu.eci.dosw.techcup.repository;

import edu.eci.dosw.techcup.entity.User;

public interface UserRepository {
    User findByEmail(String email);
}
