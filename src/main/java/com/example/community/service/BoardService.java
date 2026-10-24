package com.example.community.service;

import com.example.community.dto.BoardDto;
import com.example.community.entity.Board;
import com.example.community.global.exception.CustomException;
import com.example.community.global.exception.ErrorCode;
import com.example.community.repository.BoardRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

	private final BoardRepository boardRepository;

	public List<BoardDto.Response> findAll() {
		return boardRepository.findAll().stream()
			.map(BoardDto.Response::from)
			.toList();
	}

	public BoardDto.Response findById(Long id) {
		return BoardDto.Response.from(getBoard(id));
	}

	@Transactional
	public BoardDto.Response create(BoardDto.CreateRequest request) {
		if (boardRepository.existsByName(request.getName())) {
			throw new CustomException(ErrorCode.BOARD_DUPLICATED);
		}

		Board board = Board.builder()
			.name(request.getName())
			.description(request.getDescription())
			.build();

		return BoardDto.Response.from(boardRepository.save(board));
	}

	@Transactional
	public BoardDto.Response update(Long id, BoardDto.UpdateRequest request) {
		Board board = getBoard(id);
		board.update(request.getName(), request.getDescription());
		return BoardDto.Response.from(board);
	}

	@Transactional
	public void delete(Long id) {
		boardRepository.delete(getBoard(id));
	}

	public Board getBoard(Long id) {
		return boardRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
	}
}
