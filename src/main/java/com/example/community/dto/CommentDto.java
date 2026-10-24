package com.example.community.dto;

import com.example.community.entity.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

public class CommentDto {

	public static class CreateRequest {

		@NotBlank(message = "댓글 내용은 필수입니다.")
		private String content;

		public String getContent() {
			return content;
		}
	}

	public static class UpdateRequest {

		@NotBlank(message = "댓글 내용은 필수입니다.")
		private String content;

		public String getContent() {
			return content;
		}
	}

	@Getter
	public static class Response {

		private final Long id;
		private final String content;
		private final Long articleId;
		private final String authorNickname;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		private final LocalDateTime createdAt;

		public Response(Comment comment) {
			this.id = comment.getId();
			this.content = comment.getContent();
			this.articleId = comment.getArticle().getId();
			this.authorNickname =
				comment.getAuthor() != null ? comment.getAuthor().getNickname() : null;
			this.createdAt = comment.getCreatedAt();
		}

		public static Response from(Comment comment) {
			return new Response(comment);
		}
	}
}
