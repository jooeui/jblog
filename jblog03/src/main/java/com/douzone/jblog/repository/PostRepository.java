package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	
	// 포스트가 있는 카테고리가 삭제됐을 경우 해당 카테고리의 포스트 모두 삭제
	public boolean deleteAllPost(CategoryVo vo) {
		int count = sqlSession.delete("post.deleteAll", vo);
		return count == 1;
	}

	public List<PostVo> findPostList(Map<String, Object> map) {
		return sqlSession.selectList("post.findPostList", map);
	}

	public PostVo findPost(Map<String, Object> map) {
//		System.out.println("repository==========\n" + map);
		return sqlSession.selectOne("post.findPost", map);
	}

	public boolean write(PostVo postVo) {
		int count = sqlSession.insert("post.write", postVo);
		return count == 1;
	}

	public boolean delete(Long no) {
		int count = sqlSession.delete("post.delete", no);
		return count == 1;
	}


//	public boolean updateCategoryNo(CategoryVo vo) {
//		int count = sqlSession.update("post.updateCatNo", vo);
//		return count == 1;
//	}
	
}
