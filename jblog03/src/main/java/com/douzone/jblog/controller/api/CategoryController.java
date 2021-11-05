package com.douzone.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@RestController("categoryApiController")
@RequestMapping("/category/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/checkName")
	public JsonResult checkName(
			@RequestParam(value="name", required=true, defaultValue="") String name,
			@AuthUser UserVo authUser) {
		CategoryVo vo = new CategoryVo();
		vo.setName(name);
		vo.setBlogId(authUser.getId());
		
		CategoryVo categoryVo = categoryService.getCategory(vo);
		
		return JsonResult.success(categoryVo);
	}
	
	@PostMapping("/insert")
	public JsonResult checkName(
			@RequestParam(value="name", required=true, defaultValue="") String name,
			@RequestParam(value="desc", required=true, defaultValue="") String desc,
			@AuthUser UserVo authUser) {
		CategoryVo insertVo = new CategoryVo();
		insertVo.setName(name);
		insertVo.setDesc(desc);
		insertVo.setBlogId(authUser.getId());
		
		categoryService.addCategory(insertVo);
		
		return JsonResult.success(insertVo);
	}
	
	@PostMapping("/delete/{no}")
	public JsonResult delete(
			@PathVariable("no") Long no,
			@RequestParam(value="postcount", required=true, defaultValue="0") Long pc,
			@AuthUser UserVo authUser) {
		CategoryVo vo = new CategoryVo();
//		System.out.println("[전달받은 값] no: " + no + ", postcount: " + pc);
//		System.out.println("[authUser]" + authUser);
		vo.setNo(no);
		vo.setPostCount(pc);
		vo.setBlogId(authUser.getId());
		
		boolean checkDel = categoryService.delete(vo);
		
		return JsonResult.success(checkDel);
		
	}
}
