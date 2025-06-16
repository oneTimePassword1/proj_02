package com.example.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_service.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

