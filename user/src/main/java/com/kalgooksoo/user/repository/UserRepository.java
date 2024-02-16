package com.kalgooksoo.user.repository;

import com.kalgooksoo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
