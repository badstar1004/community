package com.kims.community.board.controller;

import com.kims.community.board.model.dto.BoardArticleResponse;
import com.kims.community.board.model.form.ArticleCommentsForm;
import com.kims.community.board.service.ArticleCommentsService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/{userid}/{boardArticleId}/article-comments")
@RequiredArgsConstructor
public class ArticleCommentsController {

    private final ArticleCommentsService articleCommentsService;


    /**
     * 댓글 등록
     * @param userid              유저 아이디
     * @param boardArticleId      게시글 아이디
     * @param articleCommentsForm ArticleCommentsForm
     * @return BoardArticleResponse
     */
    @PostMapping
    public ResponseEntity<BoardArticleResponse> addArticleComments(@PathVariable Long userid,
        @PathVariable Long boardArticleId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestBody @Valid ArticleCommentsForm articleCommentsForm) {
        return ResponseEntity.ok(
            articleCommentsService.articleCommentsAdd(userid, boardArticleId, page, size,
                articleCommentsForm));
    }

    /**
     * 댓글 수정
     * @param userid              유저 아이디
     * @param boardArticleId      게시판 아이디
     * @param articlecommentsId   댓글 아이디
     * @param articleCommentsForm ArticleCommentsForm
     * @return BoardArticleResponse
     */
    @PutMapping("/{articlecommentsId}")
    public ResponseEntity<BoardArticleResponse> modifyArticleComments(@PathVariable Long userid,
        @PathVariable Long boardArticleId, @PathVariable Long articlecommentsId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestBody @Valid ArticleCommentsForm articleCommentsForm) {
        return ResponseEntity.ok(
            articleCommentsService.articleCommentsModify(userid, boardArticleId, page, size,
                articlecommentsId, articleCommentsForm));
    }

    /**
     * 댓글 삭제
     * @param userid            유저 아이디
     * @param boardArticleId    게시판 아이디
     * @param articlecommentsId 댓글 아이디
     * @return String
     */
    @DeleteMapping("/{articlecommentsId}")
    public ResponseEntity<BoardArticleResponse> deleteArticleComments(@PathVariable Long userid,
        @PathVariable Long boardArticleId, @PathVariable Long articlecommentsId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(
            articleCommentsService.articleCommentsDelete(userid, boardArticleId,
                articlecommentsId, page, size));
    }

}
