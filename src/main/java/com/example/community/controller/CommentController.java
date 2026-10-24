package com.example.community.controller;

import com.example.community.dto.ApiResponse;
import com.example.community.dto.CommentDto;
import com.example.community.service.CommentService;
import com.example.community.service.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;
	private final SessionService sessionService;

	@GetMapping("/articles/{articleId}/comments")
	public ResponseEntity<ApiResponse<List<CommentDto.Response>>> findAllByArticle(
		@PathVariable Long articleId) {
		return ResponseEntity.ok(ApiResponse.success(commentService.findAllByArticle(articleId)));
	}

	@PostMapping("/articles/{articleId}/comments")
	public ResponseEntity<ApiResponse<CommentDto.Response>> create(
		@PathVariable Long articleId,
		@Valid @RequestBody CommentDto.CreateRequest request,
		HttpSession session) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(commentService.create(articleId, request, sessionService.getLoginUser(session))));
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<ApiResponse<CommentDto.Response>> update(
		@PathVariable Long id,
		@Valid @RequestBody CommentDto.UpdateRequest request,
		HttpSession session) {
		return ResponseEntity.ok(ApiResponse.success(commentService.update(id, request, sessionService.getLoginUser(session))));
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, HttpSession session) {
		commentService.delete(id, sessionService.getLoginUser(session));
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
