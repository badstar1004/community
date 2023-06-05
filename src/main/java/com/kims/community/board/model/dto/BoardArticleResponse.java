package com.kims.community.board.model.dto;

import com.kims.community.board.entity.BoardArticle;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardArticleResponse {

    private String message;
    private ArticleDto articleDto;


    @Getter
    @Setter
    @Builder
    public static class ArticleDto {

        /**
         * 게시판 아이디
         */
        private Long boardArticleId;

        /**
         * 제목
         */
        private String title;

        /**
         * 닉네임
         */
        private String userNickName;

        /**
         * 내용
         */
        private String contents;

        /**
         * 작성일
         */
        private String registDate;

        /**
         * 수정일
         */
        private String modifyDate;

        /**
         * 좋아요 수
         */
        private int likeCount;

        /**
         * 댓글
         */
        private List<ArticleCommentDto> articleComments;


        /**
         * BoardArticle -> ArticleDto
         * @param boardArticle 게시글
         * @return ArticleDto
         */
        public static ArticleDto of(BoardArticle boardArticle, List<ArticleCommentDto> commentsList) {
            return ArticleDto.builder()
                .boardArticleId(boardArticle.getId())
                .title(boardArticle.getTitle())
                .userNickName(boardArticle.getUserNickName())
                .contents(boardArticle.getContents())
                .registDate(boardArticle.getRegistAt().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifyDate(boardArticle.getModifyAt()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .likeCount(boardArticle.getLikeCount())
                .articleComments(commentsList)
                .build();
        }
    }

}
