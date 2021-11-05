package com.douzone.jblog.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.exception.FileUploadException;
import com.douzone.jblog.security.Auth;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Controller
@RequestMapping(value={"/{id:(?!assets).*}", "/{id:(?!assets).*}/admin"})
public class BlogController {
	private static final Log LOGGER = LogFactory.getLog(BlogController.class);
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("")
	public String main(@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.getBlogInfo(id);
		if(blogVo == null) {
			return "redirect:/";
		}
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-main";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/basic")
	public String updateBlog(@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.getBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateBlog(
			@PathVariable("id") String id,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			BlogVo blogVo) {
		try {
			String logo = fileUploadService.uploadFile(multipartFile);
			blogVo.setLogo(logo);
		} catch(FileUploadException e) {
			LOGGER.info("Blog Info Update:" + e);
		}
		
		blogService.updateBlog(blogVo);
		// ServletContext.setAttribute(blogVo);
		return "redirect:/" + id + "/admin/basic";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/category")
	public String category(@PathVariable("id") String id, Model model) {
		List<CategoryVo> categoryVo = categoryService.getCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
		return "blog/blog-admin-category";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/category/delete/{no}")
	public String category(
			@PathVariable("id") String id,
			@PathVariable("no") String no,
			Model model) {
		List<CategoryVo> categoryVo = categoryService.getCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
		return "blog/blog-admin-category";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/write")
	public String write(@PathVariable("id") String id, Model model) {
		return "blog/blog-admin-write";
	}
}
