package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(String id) {
		int count = sqlSession.insert("blog.insert", id);
		return count == 1;
	}

	public BlogVo getBlogInfo(String id) {
		return sqlSession.selectOne("blog.getBlogInfo", id);
	}

	public boolean updateBlog(BlogVo blogVo) {
		int count = sqlSession.update("blog.update", blogVo);
		return count == 1;
	}
	
}
