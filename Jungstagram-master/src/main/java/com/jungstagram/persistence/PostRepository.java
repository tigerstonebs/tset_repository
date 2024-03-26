package com.jungstagram.persistence;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jungstagram.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserIdOrderByIdDesc(Long userId);
	
	List<Post> findAllByOrderByIdDesc();
	
	Page<Post> findAll(Pageable paging);
}
