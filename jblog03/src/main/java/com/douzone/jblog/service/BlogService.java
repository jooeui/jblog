package com.douzone.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.exception.NotFoundException;
import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public BlogVo getBlogInfo(String id) {
		return blogRepository.getBlogInfo(id);
	}
	
	public Map<String, Object> getBlogInfoAndPost(Map<String, Object> map){

		if(!map.containsKey("postNo")) {
			map.put("postNo", 0);
		}
		
		if(!map.containsKey("categoryNo")) {
			map.put("categoryNo", 0);
		}
		System.out.println(map);
		
		// 카테고리 리스트
		List<CategoryVo> categoryList = categoryRepository.findCategoryList((String)map.get("id"));
		
		// 현재 카테고리
		CategoryVo currentCategory = categoryRepository.currentCategoryInfo(map);
		
		// 포스트 리스트
		List<PostVo> postList = postRepository.findPostList(map);
		
		// 최신글
		PostVo postVo = postRepository.findPost(map);
		if(!map.get("id").equals(categoryList.get(0).getBlogId())) {
			throw new NotFoundException();
		}
		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("currentCatNo", map.get("categoryNo"));
		resultMap.put("categoryList", categoryList);
		resultMap.put("currentCategory", currentCategory);
		resultMap.put("postList", postList);
		resultMap.put("postVo", postVo);
 		
//		System.out.println(resultMap);
		return resultMap;
	}

	public void updateBlog(BlogVo blogVo) {
		blogRepository.updateBlog(blogVo);
	}

}
