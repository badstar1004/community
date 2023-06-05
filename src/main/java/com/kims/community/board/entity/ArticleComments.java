package com.kims.community.board.entity;

import com.kims.community.baseEntity.BaseEntity;
import com.kims.community.board.model.form.ArticleCommentsForm;
import com.kims.community.users.entity.Users;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ArticleComments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleComments extends BaseEntity {

    /**
     * 댓글 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articlecomments_id")
    private Long id;

    /**
     * Users nickName
     */
    @Column(length = 50)
    private String userNickName;

    /**
     * 댓글 내용
     */
    @Column(length = 2000)
    private String contents;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardarticle_id")
    private BoardArticle boardArticle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;


    /**
     * ArticleCommentsForm -> ArticleComments
     * @param users            Users
     * @param boardArticleForm ArticleCommentsForm
     * @return ArticleComments
     */
    public static ArticleComments of(Users users, ArticleCommentsForm boardArticleForm) {
        return ArticleComments.builder()
            .userNickName(users.getNickName())
            .contents(boardArticleForm.getContents())
            .build();
    }
}
