package com.example.jwt.employee.employee.repository;

import com.example.jwt.employee.employee.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
    Optional<Users> findByUsername(String username);
}
