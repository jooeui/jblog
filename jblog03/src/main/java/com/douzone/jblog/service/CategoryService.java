package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;

	public List<CategoryVo> getCategoryList(String id) {
		List<CategoryVo> list = categoryRepository.findCategoryList(id);
		return list;
	}

	public CategoryVo getCategory(CategoryVo vo) {
		return categoryRepository.findCategory(vo);
	}

	public void addCategory(CategoryVo vo) {
		categoryRepository.addCategory(vo);
	}

	public boolean delete(CategoryVo vo) {
		if(vo.getPostCount() > 0) {
			postRepository.updateCategoryNo(vo);
		}
		return categoryRepository.deleteCategory(vo);
	}
}
