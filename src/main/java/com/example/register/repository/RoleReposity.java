package com.example.register.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.register.entity.Role;

public interface RoleReposity extends JpaRepository<Role, Long> {
	public Optional<Role> findByroleName(String rolename);
}
