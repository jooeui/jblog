package com.douzone.jblog.exception;

public class FileUploadException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public FileUploadException(String message) {
		super(message);
	}
	
	public FileUploadException() {
		super("FileUploadService 예외 발생");
	}
}
