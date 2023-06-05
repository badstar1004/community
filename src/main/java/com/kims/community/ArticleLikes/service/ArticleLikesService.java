package com.kims.community.ArticleLikes.service;

import static com.kims.community.exception.business.ErrorCode.*;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_BOARD_ARTICLE;

import com.kims.community.ArticleLikes.entity.ArticleLikes;
import com.kims.community.ArticleLikes.repository.ArticleLikesRepository;
import com.kims.community.board.entity.BoardArticle;
import com.kims.community.board.repository.BoarderRepository;
import com.kims.community.exception.business.BusinessException;
import com.kims.community.exception.business.ErrorCode;
import com.kims.community.users.entity.Users;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikesService {

    private final ArticleLikesRepository articleLikesRepository;
    private final UsersRepository usersRepository;
    private final BoarderRepository boarderRepository;
    private final RedisTemplate<Long, Long> redisTemplate;


    /**
     * 좋아요
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
    @Transactional
    public String articleLikesAdd(Long userid, Long boardarticleid) {
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // 저장
        articleLikesRepository.save(ArticleLikes.of(user, boardArticle));

        // 게시글의 좋아요 수 +
        boardArticle.setLikeCount(boardArticle.getLikeCount() + 1);

        // redis
        redisTemplate.opsForList().leftPush(boardArticle.getId(), user.getId());

        return "좋아요~♥";
    }

    /**
     * 좋아요 취소
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
//    @Transactional
//    public String articleLikesDelete(Long userid, Long boardarticleid) {
//        Users user = usersRepository.findById(userid)
//            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));
//
//        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
//            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));
//
//        ArticleLikes articleLikes =
//            articleLikesRepository.findByBoardArticleIdAndUserId(boardArticle, user)
//                .orElseThrow(() -> new BusinessException(ALREADY_ARTICLE_LIKES_CANCEL));
//
//        // 삭제
//        articleLikesRepository.delete(articleLikes);
//
//        // 게시글의 좋아요 수 -
//        boardArticle.setLikeCount(boardArticle.getLikeCount() - 1);
//
//        // redis
//        redisTemplate.opsForList().remove(boardArticle.getId(), 0, user.getId());
//
//        return "좋아요 취소~♡";
//    }

}
