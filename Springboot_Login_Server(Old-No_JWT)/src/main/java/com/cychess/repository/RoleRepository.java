package com.cychess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cychess.model.Role;

/**
 * Interface used to create a repository for user roles and roles
 * @author bbanothu
 *
 */
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

}
