package com.example.community.service;

import com.example.community.entity.Users;
import com.example.community.global.exception.CustomException;
import com.example.community.global.exception.ErrorCode;
import com.example.community.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionService {

	public static final String SESSION_USER_KEY = "LOGIN_USER";

	private final UserRepository userRepository;

	public Users getLoginUser(HttpSession session) {
		Long userId = (Long) session.getAttribute(SESSION_USER_KEY);
		if (userId == null) {
			throw new CustomException(ErrorCode.UNAUTHORIZED);
		}
		return userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.UNAUTHORIZED));
	}

	public void login(HttpSession session, Long userId) {
		session.setAttribute(SESSION_USER_KEY, userId);
	}

	public void logout(HttpSession session) {
		session.invalidate();
	}
}
