package com.kims.community.board.model.dto;

import com.kims.community.board.entity.BoardArticle;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BoardListView {

    /**
     * 게시판 아이디
     */
    private Long boardArticleId;

    /**
     * 닉네임
     */
    private String userNickName;

    /**
     * 제목
     */
    private String title;

    /**
     * 좋아요 수
     */
    private int likeCount;


    /**
     * BoardArticle -> BoardArticleView
     * @param boardArticle 게시글
     * @return BoardArticleView
     */
    public static BoardListView of(BoardArticle boardArticle) {
        return BoardListView.builder()
            .boardArticleId(boardArticle.getId())
            .userNickName(boardArticle.getUserNickName())
            .title(boardArticle.getTitle())
            .likeCount(boardArticle.getLikeCount())
            .build();
    }

}
