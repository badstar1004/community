package com.kims.community.board.model.dto;

import com.kims.community.board.entity.BoardArticle;
import java.time.format.DateTimeFormatter;
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
     * 제목
     */
    private String title;

    /**
     * 닉네임
     */
    private String userNickName;

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
     * BoardArticle -> BoardArticleView
     * @param boardArticle 게시글
     * @return BoardArticleView
     */
    public static BoardListView from(BoardArticle boardArticle) {
        return BoardListView.builder()
            .boardArticleId(boardArticle.getId())
            .title(boardArticle.getTitle())
            .userNickName(boardArticle.getUserNickName())
            .registDate(boardArticle.getRegistAt().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .modifyDate(boardArticle.getModifyAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .likeCount(boardArticle.getLikeCount())
            .build();
    }

}
