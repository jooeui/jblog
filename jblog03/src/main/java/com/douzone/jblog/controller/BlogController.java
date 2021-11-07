package com.douzone.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping({"/{id:(?!assets|images).*}"})
public class BlogController {
	private static final Log LOGGER = LogFactory.getLog(BlogController.class);
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String main(
			@PathVariable("id") String id,
			@PathVariable("categoryNo") Optional<Long> categoryNo,
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		if(categoryNo.isPresent()) {
//			System.out.println("[넘어온 categoryNo] " + categoryNo.get());
			map.put("categoryNo", categoryNo.get());
		}
		if(postNo.isPresent()) {
//			System.out.println("[넘어온 postNo] " + postNo.get());
			map.put("postNo", postNo.get());
		}
		Map<String, Object> infoMap = blogService.getBlogInfoAndPost(map);
//		System.out.println("==========controller==========");
//		System.out.println(infoMap);
//		System.out.println("[category list]\n" + infoMap.get("categoryList"));
//		BlogVo blogVo = blogService.getBlogInfo(id);
//		if(infoMap.get("blogVo") == null) {
//			return "redirect:/";
//		}
		model.addAttribute("infoMap", infoMap);
		
		return "blog/blog-main";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/basic")
	public String updateBlog(@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.getBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/admin/update", method=RequestMethod.POST)
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
		servletContext.setAttribute("blogVo", blogVo);
		return "redirect:/" + id + "/admin/basic";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/category")
	public String category(@PathVariable("id") String id, Model model) {
		List<CategoryVo> categoryVo = categoryService.getCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
		return "blog/blog-admin-category";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/category/delete/{no}")
	public String category(
			@PathVariable("id") String id,
			@PathVariable("no") String no,
			Model model) {
		List<CategoryVo> categoryVo = categoryService.getCategoryList(id);
		model.addAttribute("categoryVo", categoryVo);
		return "blog/blog-admin-category";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/admin/write")
	public String write(@PathVariable("id") String id, @ModelAttribute PostVo postVo, Model model) {
		List<CategoryVo> categoryList = categoryService.getCategoryList(id);
		model.addAttribute("categoryList", categoryList);
		return "blog/blog-admin-write";
	}

	@Auth(role="ADMIN")
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String write(@PathVariable("id") String id, @ModelAttribute @Valid PostVo postVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			model.addAttribute("categoryList", postVo.getCategoryList());
			return "blog/blog-admin-write";
		}
//		System.out.println("Controller ==============" + postVo);
		postService.writePost(postVo);
		return "redirect:/" + id;
	}
//	public String write(@PathVariable("id") String id, PostVo vo, Model model) {
//		return "redirect:/" + id;
//	}
}
