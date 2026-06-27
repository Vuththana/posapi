package org.goros.posapi.repository;

import org.goros.posapi.model.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, UUID> {
    @Query("SELECT r.roleId from AppRole r WHERE r.roleName = 'OWNER' ")
    UUID getRoleOwnerID();
}
