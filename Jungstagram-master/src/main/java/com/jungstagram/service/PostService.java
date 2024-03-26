package com.jungstagram.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jungstagram.domain.Follow;
import com.jungstagram.domain.Post;
import com.jungstagram.dto.PostDto;
import com.jungstagram.persistence.FollowRepository;
import com.jungstagram.persistence.PostRepository;
import com.jungstagram.persistence.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

	@Autowired
	private PostRepository postRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private FollowRepository followRepo;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Transactional
	public Post savePost(PostDto dto, Long userId) {
		return postRepo.save(Post.builder().title(dto.getTitle()).content(dto.getContent())
				.user(userRepo.findById(userId).get()).build());
	}

	@Transactional
	@Cacheable(value = "post", key = "#postId")
	public Post findPostById(Long postId) {
		Long count = 0L;
		ValueOperations<String, String> vop = redisTemplate.opsForValue();
		try {
			vop.increment("postView:"+postId.toString(), 1);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return postRepo.findById(postId).get();
	}

	public Long findPostViewCount(Long postId) {
		Long count = 0L;
		ValueOperations<String, String> vop = redisTemplate.opsForValue();
		try {
			if (vop.get("postView:"+postId.toString()) == null) count = 0L;
			else count = Long.valueOf(vop.get("postView:"+postId.toString()));
		} catch (Exception e) {
			System.out.println(e.toString());
		}	
		return count;
	}

	public Page<Post> findPostList(int page) {
		Pageable paging = PageRequest.of(page, 5, Sort.Direction.DESC, "id");
		Page<Post> pageInfo = postRepo.findAll(paging);
		return pageInfo;
	}

	@Transactional
	public List<Post> findPostListByUserId(Long userId) {
		return postRepo.findByUserIdOrderByIdDesc(userId);
	}

	@Transactional
	public Post updatePost(PostDto postDto, Long userId) {
		Post post = postRepo.findById(postDto.getId()).get();
		System.out.println("--->" + postDto.getTitle() + postDto.getContent());
		if (postDto.getTitle() != "") {
			post.setTitle(postDto.getTitle());
		}
		if (postDto.getContent() != "") {
			post.setContent(postDto.getContent());
		}
		return postRepo.save(post);
	}

	@Transactional
	@CacheEvict(value = "post", key = "#postId")
	public void deletePostById(Long postId) {
		postRepo.deleteById(postId);
	}

	@Transactional
	public List<Post> findFolloweePostListByUserId(Long userId) {
		List<Post> postList = new ArrayList<Post>();
		List<Follow> followList = followRepo.findByFollowerId(userId);

		for (Follow follow : followList) {
			postList.addAll(follow.getFollowee().getPostList());
		}
		postList.addAll(userRepo.findById(userId).get().getPostList());
		Collections.sort(postList);
		return postList;
	}
}
