package com.kollaboralabs.auth.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kollaboralabs.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByUuid(UUID uuid);
    @Override
    public Page<User> findAll(Pageable pageable);
    public User findUserByLogin(String login);
    public User findUserByEmailIgnoreCase(String emailAddress);
}