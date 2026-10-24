package com.example.community.service;

import com.example.community.dto.UserDto;
import com.example.community.entity.Users;
import com.example.community.global.exception.CustomException;
import com.example.community.global.exception.ErrorCode;
import com.example.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserDto.Response signup(UserDto.SignupRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
		}
		Users user = Users.builder()
			.email(request.getEmail())
			.password(request.getPassword())
			.nickname(request.getNickname())
			.build();
		return UserDto.Response.from(userRepository.save(user));
	}

	public UserDto.Response login(UserDto.LoginRequest request) {
		Users user = userRepository.findByEmail(request.getEmail())
			.orElseThrow(() -> new CustomException(ErrorCode.INVALID_PASSWORD));
		if (!user.matchesPassword(request.getPassword())) {
			throw new CustomException(ErrorCode.INVALID_PASSWORD);
		}
		return UserDto.Response.from(user);
	}
}
