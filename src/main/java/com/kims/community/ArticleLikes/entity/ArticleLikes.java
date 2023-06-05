package com.kims.community.ArticleLikes.entity;

import com.kims.community.baseEntity.BaseEntity;
import com.kims.community.board.entity.BoardArticle;
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
@Table(name = "ArticleLikes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLikes extends BaseEntity {

    /**
     * 좋아요 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articlelikes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "boardarticle_id")
    private BoardArticle boardArticle;


    public static ArticleLikes of(Users user, BoardArticle boardArticle) {
        return ArticleLikes.builder()
            .user(user)
            .boardArticle(boardArticle)
            .build();
    }
}
