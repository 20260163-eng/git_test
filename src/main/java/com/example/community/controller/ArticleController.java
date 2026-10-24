package com.example.community.controller;

import com.example.community.dto.ApiResponse;
import com.example.community.dto.ArticleDto;
import com.example.community.service.ArticleService;
import com.example.community.service.SessionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class ArticleController {

	private final ArticleService articleService;
	private final SessionService sessionService;

	@GetMapping("/boards/{boardId}/articles")
	public ResponseEntity<ApiResponse<Page<ArticleDto.SummaryResponse>>> findAllByBoard(
		@PathVariable Long boardId,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok(ApiResponse.success(articleService.findAllByBoard(boardId, pageable)));
	}

	@GetMapping("/articles/{id}")
	public ResponseEntity<ApiResponse<ArticleDto.Response>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success(articleService.findById(id)));
	}

	@PostMapping("/boards/{boardId}/articles")
	public ResponseEntity<ApiResponse<ArticleDto.Response>> create(
		@PathVariable Long boardId,
		@Valid @RequestBody ArticleDto.CreateRequest request,
		HttpSession session) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(articleService.create(boardId, request, sessionService.getLoginUser(session))));
	}

	@PutMapping("/articles/{id}")
	public ResponseEntity<ApiResponse<ArticleDto.Response>> update(
		@PathVariable Long id,
		@Valid @RequestBody ArticleDto.UpdateRequest request,
		HttpSession session) {
		return ResponseEntity.ok(ApiResponse.success(articleService.update(id, request, sessionService.getLoginUser(session))));
	}

	@DeleteMapping("/articles/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, HttpSession session) {
		articleService.delete(id, sessionService.getLoginUser(session));
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
