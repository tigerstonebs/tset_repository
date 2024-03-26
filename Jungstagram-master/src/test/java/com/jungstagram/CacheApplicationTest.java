package com.jungstagram;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jungstagram.service.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTest {

	@Autowired
	private PostService postService;
	
	private long startTime;
	private long endTime;
	
	private static final Logger logger = LoggerFactory.getLogger(CacheApplicationTest.class);
	
	@Before
	public void onBefore() {
		startTime = System.currentTimeMillis();
	}
	
	@After
	public void onAfter() {
		endTime = System.currentTimeMillis();
		logger.info("소요시간: " + (endTime - startTime) + " ms");
	}
	
	@Test
	public void getPostTest1() {
		postService.findPostById(10L);
	}
	
	@Test
	public void getPostTest2() {
		postService.findPostById(10L);
	}
	
	@Test
	public void getPostTest3() {
		postService.findPostById(18L);
	}
	
	@Test
	public void getPostTest4() {
		postService.findPostById(18L);
	}
}
