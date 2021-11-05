package com.douzone.jblog.vo;

import javax.validation.constraints.NotEmpty;

public class CategoryVo {
	private Long no;
	
	@NotEmpty
	private String name;
	
	private Long postCount;
	private String desc;
	private String blogId;
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPostCount() {
		return postCount;
	}
	public void setPostCount(Long postCount) {
		this.postCount = postCount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}

	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", postCount=" + postCount + ", desc=" + desc + ", blogId="
				+ blogId + "]";
	}
}
