package com.example.community.service;

import com.example.community.dto.ArticleDto;
import com.example.community.entity.Article;
import com.example.community.entity.Board;
import com.example.community.entity.Users;
import com.example.community.global.exception.CustomException;
import com.example.community.global.exception.ErrorCode;
import com.example.community.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final BoardService boardService;

	public Page<ArticleDto.SummaryResponse> findAllByBoard(Long boardId, Pageable pageable) {
		return articleRepository.findAllByBoardId(boardId, pageable)
			.map(ArticleDto.SummaryResponse::from);
	}

	public ArticleDto.Response findById(Long id) {
		return ArticleDto.Response.from(getArticle(id));
	}

	@Transactional
	public ArticleDto.Response create(Long boardId, ArticleDto.CreateRequest request, Users author) {
		Board board = boardService.getBoard(boardId);
		Article article = Article.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.board(board)
			.author(author)
			.build();
		return ArticleDto.Response.from(articleRepository.save(article));
	}

	@Transactional
	public ArticleDto.Response update(Long id, ArticleDto.UpdateRequest request, Users loginUser) {
		Article article = getOwnedArticle(id, loginUser);
		article.update(request.getTitle(), request.getContent());
		return ArticleDto.Response.from(article);
	}

	@Transactional
	public void delete(Long id, Users loginUser) {
		articleRepository.delete(getOwnedArticle(id, loginUser));
	}

	public Article getOwnedArticle(Long id, Users loginUser) {
		Article article = getArticle(id);
		if (article.getAuthor() == null || !article.getAuthor().getId().equals(loginUser.getId())) {
			throw new CustomException(ErrorCode.FORBIDDEN);
		}
		return article;
	}

	public Article getArticle(Long id) {
		return articleRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.ARTICLE_NOT_FOUND));
	}
}
