package com.example.community.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류가 발생했습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 입력값입니다."),

	BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "B001", "존재하지 않는 게시판입니다."),
	BOARD_DUPLICATED(HttpStatus.CONFLICT, "B002", "이미 존재하는 게시판 이름입니다."),

	ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "A001", "존재하지 않는 게시글입니다."),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 댓글입니다."),

	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "U001", "이미 가입된 이메일입니다."),
	INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U002", "비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "U003", "로그인이 필요합니다."),
	FORBIDDEN(HttpStatus.FORBIDDEN, "U004", "본인만 수정/삭제할 수 있습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(HttpStatus status, String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
