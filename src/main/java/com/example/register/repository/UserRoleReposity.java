package com.example.register.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.register.entity.UserRole;

public interface UserRoleReposity extends JpaRepository<UserRole, Long>{
	List<UserRole> findByUserId(Long userId);
}
