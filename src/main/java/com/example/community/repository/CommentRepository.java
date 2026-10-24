package com.example.community.repository;

import com.example.community.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByArticleIdOrderByCreatedAtAsc(Long articleId);
}
