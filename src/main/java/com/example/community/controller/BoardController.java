package com.example.community.controller;

import com.example.community.dto.ApiResponse;
import com.example.community.dto.BoardDto;
import com.example.community.service.BoardService;
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
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<BoardDto.Response>>> findAll() {
		return ResponseEntity.ok(ApiResponse.success(boardService.findAll()));
		// http 상태코드 200(ok)
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BoardDto.Response>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.success(boardService.findById(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<BoardDto.Response>> create(@Valid @RequestBody BoardDto.CreateRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(boardService.create(request)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<BoardDto.Response>> update(
		@PathVariable Long id,
		@Valid @RequestBody BoardDto.UpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(boardService.update(id, request)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		boardService.delete(id);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
