package com.kims.community.board.controller;

import com.kims.community.board.model.dto.BoardListView;
import com.kims.community.board.service.BoarderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoarderController {

    private final BoarderService boarderService;

    /**
     * 게시판 리스트 조회
     *
     * @return List<BoardListView>
     */
    @GetMapping("/list")
    public ResponseEntity<Page<BoardListView>> getBoardArticle(Pageable pageable) {
        return ResponseEntity.ok(boarderService.getAllBoardArticle(pageable));
    }

}
