package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;
	
	// 회원가입 시 미분류 카테고리 추가
	public boolean addCategory(String id) {
		int count = sqlSession.insert("category.insertCategory", id);
		return count == 1;
	}
	
	// 블로그 사용자(관리자)가 카테고리 추가
	public boolean addCategory(CategoryVo vo) {
		int count = sqlSession.insert("category.addCategory", vo);
		return count == 1;
	}
	
	public List<CategoryVo> findCategoryList(String id) {
		return sqlSession.selectList("category.findCategoryList", id);
	}

	public CategoryVo findCategory(CategoryVo vo) {
		return sqlSession.selectOne("category.findCategory", vo);
	}
	
	public CategoryVo currentCategoryInfo(Map<String, Object> map) {
		return sqlSession.selectOne("category.currentCategoryInfo", map);
	}

	public boolean deleteCategory(CategoryVo vo) {
		int count = sqlSession.delete("category.delete", vo);
		return count == 1;
	}
	
}
