package com.kims.community.ArticleLikes.controller;

import com.kims.community.ArticleLikes.service.ArticleLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board/{userid}/list/{boardarticleid}/detailview/likes")
@RequiredArgsConstructor
public class ArticleLikesController {

    private final ArticleLikesService articleLikesService;


    /**
     * 좋아요
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
    @PostMapping
    public ResponseEntity<String> addArticleLikes(@PathVariable Long userid,
        @PathVariable Long boardarticleid) {
        return ResponseEntity.ok(articleLikesService.articleLikesAdd(userid, boardarticleid));
    }

    /**
     * 좋아요 취소
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
//    @DeleteMapping
//    public ResponseEntity<String> deleteArticleLikes(@PathVariable Long userid,
//        @PathVariable Long boardarticleid) {
//        return ResponseEntity.ok(articleLikesService.articleLikesDelete(userid, boardarticleid));
//    }

}
