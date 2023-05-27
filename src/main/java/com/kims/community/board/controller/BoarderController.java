package com.kims.community.board.controller;

import static com.kims.community.board.model.dto.BoardArticleResponse.ArticleDto;

import com.kims.community.board.model.dto.BoardArticleResponse;
import com.kims.community.board.model.dto.BoardListView;
import com.kims.community.board.model.form.BoardArticleForm;
import com.kims.community.board.service.BoarderService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoarderController {

    private final BoarderService boarderService;


    /**
     * 게시판 리스트 조회
     * @return List<BoardListView>
     */
    @GetMapping("/list")
    public ResponseEntity<Page<BoardListView>> getBoardArticle(Pageable pageable) {
        return ResponseEntity.ok(boarderService.getAllBoardArticle(pageable));
    }

    /**
     * 게시글 등록
     * @param userid           유저 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @PostMapping("/{userid}/add-boardarticle")
    public ResponseEntity<BoardArticleResponse> addBoardArticle(@PathVariable Long userid,
        @RequestBody @Valid BoardArticleForm boardArticleForm) {
        return ResponseEntity.ok(boarderService.addBoardArticle(userid, boardArticleForm));
    }

    /**
     * 게시글 상세 조회
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return ArticleDto
     */
    @GetMapping("/{userid}/list/{boardarticleid}/detailview")
    public ResponseEntity<ArticleDto> articleDetailView(@PathVariable Long userid,
        @PathVariable Long boardarticleid) {
        return ResponseEntity.ok(boarderService.articleDetailView(userid, boardarticleid));
    }

    /**
     * 게시글 수정
     * @param boardarticleid   게시글 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @PutMapping("/{userid}/{boardarticleid}/modify-boardarticle")
    public ResponseEntity<BoardArticleResponse> articleModify(@PathVariable Long userid,
        @PathVariable Long boardarticleid, @RequestBody @Valid BoardArticleForm boardArticleForm) {
        return ResponseEntity.ok(
            boarderService.articleModify(userid, boardarticleid, boardArticleForm));
    }

    /**
     * 게시글 삭제
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
    @DeleteMapping("/{userid}/{boardarticleid}/delete-boardarticle")
    public ResponseEntity<String> articleDelete(@PathVariable Long userid,
        @PathVariable Long boardarticleid) {
        return ResponseEntity.ok(boarderService.articleDelete(userid, boardarticleid));
    }

}
