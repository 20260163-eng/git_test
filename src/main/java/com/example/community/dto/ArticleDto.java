package com.example.community.dto;

import com.example.community.entity.Article;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;

public class ArticleDto {

	public static class CreateRequest {

		@NotBlank(message = "제목은 필수입니다.")
		private String title;

		@NotBlank(message = "내용은 필수입니다.")
		private String content;

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}
	}

	public static class UpdateRequest {

		@NotBlank(message = "제목은 필수입니다.")
		private String title;

		@NotBlank(message = "내용은 필수입니다.")
		private String content;

		public String getTitle() {
			return title;
		}

		public String getContent() {
			return content;
		}
	}

	@Getter
	public static class SummaryResponse {

		private final Long id;
		private final String title;
		private final String authorNickname;
		private final int commentCount;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		private final LocalDateTime createdAt;

		public SummaryResponse(Article article) {
			this.id = article.getId();
			this.title = article.getTitle();
			this.authorNickname =
				article.getAuthor() != null ? article.getAuthor().getNickname() : null;
			this.commentCount = article.getComments().size();
			this.createdAt = article.getCreatedAt();
		}

		public static SummaryResponse from(Article article) {
			return new SummaryResponse(article);
		}
	}

	@Getter
	public static class Response {

		private final Long id;
		private final String title;
		private final String content;
		private final Long boardId;
		private final String authorNickname;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		private final LocalDateTime createdAt;

		@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
		private final LocalDateTime updatedAt;

		public Response(Article article) {
			this.id = article.getId();
			this.title = article.getTitle();
			this.content = article.getContent();
			this.boardId = article.getBoard().getId();
			this.authorNickname =
				article.getAuthor() != null ? article.getAuthor().getNickname() : null;
			this.createdAt = article.getCreatedAt();
			this.updatedAt = article.getUpdatedAt();
		}

		public static Response from(Article article) {
			return new Response(article);
		}
	}
}
