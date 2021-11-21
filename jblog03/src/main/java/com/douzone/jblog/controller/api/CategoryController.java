package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.CategoryVo;

@RestController("categoryApiController")
@RequestMapping("/category/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/list/{id}")
	public JsonResult list(@PathVariable("id") String id) {
		List<CategoryVo> list = categoryService.getCategoryList(id);
		
		return JsonResult.success(list);
	}
	
	@PostMapping("/checkName")
	public JsonResult checkName(@RequestBody CategoryVo vo) {
		CategoryVo categoryVo = categoryService.getCategory(vo);
		return JsonResult.success(categoryVo);
	}
	
	@PostMapping("/insert")
	public JsonResult insert(@RequestBody CategoryVo vo) {
		categoryService.addCategory(vo);
		// System.out.println(vo);
		return JsonResult.success(vo);
	}
	
	@PostMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") Long no, @RequestBody CategoryVo vo) {
		vo.setNo(no);
		
		categoryService.delete(vo);
		
		return JsonResult.success(no);
	}
}
