package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean updateCategoryNo(CategoryVo vo) {
		int count = sqlSession.update("post.updateCatNo", vo);
		return count == 1;
	}

	
	
}
