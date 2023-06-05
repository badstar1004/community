package com.kims.community.board.model.dto;

import com.kims.community.board.entity.ArticleComments;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArticleCommentDto {

    /**
     * 댓글 아이디
     */
    private Long articlecomments_id;

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
     * articleComments -> ArticleCommentDto
     * @param articleComments ArticleComments
     * @return ArticleCommentDto
     */
    public static ArticleCommentDto from(ArticleComments articleComments) {
        return ArticleCommentDto.builder()
            .articlecomments_id(articleComments.getId())
            .userNickName(articleComments.getUserNickName())
            .contents(articleComments.getContents())
            .registDate(articleComments.getRegistAt().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .modifyDate(articleComments.getModifyAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .build();
    }

}