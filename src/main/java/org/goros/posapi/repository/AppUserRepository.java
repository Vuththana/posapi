package org.goros.posapi.repository;

import org.goros.posapi.model.entity.AppUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    @Query("SELECT u FROM AppUser u WHERE u.email = :identifier OR u.username = :identifier")
    AppUser findByEmailOrUsername(String identifier);
}
