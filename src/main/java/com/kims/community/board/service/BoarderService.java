package com.kims.community.board.service;

import com.kims.community.board.entity.BoardArticle;
import com.kims.community.board.model.dto.BoardListView;
import com.kims.community.board.repository.BoarderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoarderService {

    private final BoarderRepository boarderRepository;

    /**
     * 게시글 조회
     * @param pageable Pageable
     * @return Page<BoardListView>
     */
    public Page<BoardListView> getAllBoardArticle(Pageable pageable) {
        Page<BoardArticle> boardArticlePage = boarderRepository.findAll(pageable);
        return boardArticlePage.map(BoardListView::of);
    }
}
