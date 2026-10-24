package com.example.community.dto;

import com.example.community.entity.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

public class BoardDto {

	public static class CreateRequest {

		@NotBlank(message = "게시판 이름은 필수입니다.")
		private String name;

		private String description;

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
	}

	public static class UpdateRequest {

		@NotBlank(message = "게시판 이름은 필수입니다.")
		private String name;

		private String description;

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
	}

	@Getter
	public static class Response {

		private final Long id;
		private final String name;
		private final String description;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		private final LocalDateTime createdAt;

		public Response(Board board) {
			this.id = board.getId();
			this.name = board.getName();
			this.description = board.getDescription();
			this.createdAt = board.getCreatedAt();
		}

		public static Response from(Board board) {
			return new Response(board);
		}
	}
}
