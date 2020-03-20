package com.cychess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cychess.model.User;

/**
 * Interface used to create a repository for user information
 * @author bbanothu
 *
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);
}




