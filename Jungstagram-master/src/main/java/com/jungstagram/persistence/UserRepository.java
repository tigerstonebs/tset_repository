package com.jungstagram.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jungstagram.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsernameAndPassword(String username, String password);

}
