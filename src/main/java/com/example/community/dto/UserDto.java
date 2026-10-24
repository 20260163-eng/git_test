package com.example.community.dto;

import com.example.community.entity.Users;
import jakarta.validation.constraints.Email;import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class UserDto {

	public static class SignupRequest {

		@NotBlank(message = "이메일은 필수입니다.")
		@Email(message = "올바른 이메일 형식이 아닙니다.")
		private String email;

		@NotBlank(message = "비밀번호는 필수입니다.")
		private String password;

		@NotBlank(message = "닉네임은 필수입니다.")
		private String nickname;

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}

		public String getNickname() {
			return nickname;
		}
	}

	public static class LoginRequest {

		@NotBlank(message = "이메일은 필수입니다.")
		private String email;

		@NotBlank(message = "비밀번호는 필수입니다.")
		private String password;

		public String getEmail() {
			return email;
		}

		public String getPassword() {
			return password;
		}
	}

	@Getter
	public static class Response {

		private final Long id;
		private final String email;
		private final String nickname;

		public Response(Users user) {
			this.id = user.getId();
			this.email = user.getEmail();
			this.nickname = user.getNickname();
		}

		public static Response from(Users user) {
			return new Response(user);
		}
	}
}
