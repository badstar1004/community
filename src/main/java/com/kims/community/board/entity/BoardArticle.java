package com.kims.community.board.entity;

import com.kims.community.baseEntity.BaseEntity;
import com.kims.community.users.entity.Users;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardArticle extends BaseEntity {

    /**
     * 게시판 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardarticleId")
    private Long id;

    /**
     * Users 와 조인
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    /**
     * Users nickName
     */
    @Column(length = 50)
    private String userNickName;

    @PostLoad
    private void loadUserNickName() {
        if (users != null) {
            userNickName = users.getNickName();
        }
    }

    /**
     * 제목
     */
    @Column(length = 50)
    private String title;

    /**
     * 내용
     */
    @Column(length = 3000)
    private String contents;

    /**
     * 좋아요 수
     */
    private int likeCount;


}
