package com.example.community.controller;

import com.example.community.dto.ApiResponse;
import com.example.community.dto.UserDto;
import com.example.community.service.SessionService;
import com.example.community.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final SessionService sessionService;

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserDto.Response>> signup(
		@Valid @RequestBody UserDto.SignupRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ApiResponse.success(userService.signup(request)));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<UserDto.Response>> login(
		@Valid @RequestBody UserDto.LoginRequest request,
		HttpSession session) {
		UserDto.Response response = userService.login(request);
		sessionService.login(session, response.getId());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		sessionService.logout(session);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<UserDto.Response>> me(HttpSession session) {
		var user = sessionService.getLoginUser(session);
		return ResponseEntity.ok(ApiResponse.success(UserDto.Response.from(user)));
	}
}
