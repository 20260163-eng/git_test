package com.example.community.global.exception;

import com.example.community.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException e) {
		log.error("CustomException: {}", e.getMessage());
		return ResponseEntity.status(e.getErrorCode().getStatus())
			.body(ApiResponse.error(e.getErrorCode()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		return ResponseEntity.badRequest()
			.body(ApiResponse.error(ErrorCode.INVALID_INPUT_VALUE, message));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
		log.error("Unexpected exception", e);
		return ResponseEntity.internalServerError()
			.body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR));
	}
}
