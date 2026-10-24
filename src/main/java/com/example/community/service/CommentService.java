package com.example.community.service;

import com.example.community.dto.CommentDto;
import com.example.community.entity.Article;
import com.example.community.entity.Comment;
import com.example.community.entity.Users;
import com.example.community.global.exception.CustomException;
import com.example.community.global.exception.ErrorCode;
import com.example.community.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final ArticleService articleService;

	public List<CommentDto.Response> findAllByArticle(Long articleId) {
		return commentRepository.findAllByArticleIdOrderByCreatedAtAsc(articleId).stream()
			.map(CommentDto.Response::from)
			.toList();
	}

	@Transactional
	public CommentDto.Response create(Long articleId, CommentDto.CreateRequest request, Users author) {
		Article article = articleService.getArticle(articleId);
		Comment comment = Comment.builder()
			.content(request.getContent())
			.article(article)
			.author(author)
			.build();
		return CommentDto.Response.from(commentRepository.save(comment));
	}

	@Transactional
	public CommentDto.Response update(Long id, CommentDto.UpdateRequest request, Users loginUser) {
		Comment comment = getOwnedComment(id, loginUser);
		comment.update(request.getContent());
		return CommentDto.Response.from(comment);
	}

	@Transactional
	public void delete(Long id, Users loginUser) {
		commentRepository.delete(getOwnedComment(id, loginUser));
	}

	private Comment getOwnedComment(Long id, Users loginUser) {
		Comment comment = getComment(id);
		if (comment.getAuthor() == null || !comment.getAuthor().getId().equals(loginUser.getId())) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
		return comment;
	}

	public Comment getComment(Long id) {
		return commentRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
	}
}
