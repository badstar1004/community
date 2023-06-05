package com.kims.community.ArticleLikes.repository;

import com.kims.community.ArticleLikes.entity.ArticleLikes;
import com.kims.community.board.entity.BoardArticle;
import com.kims.community.users.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleLikesRepository extends JpaRepository<ArticleLikes, Long> {

    Optional<ArticleLikes> findByBoardArticleIdAndUserId(BoardArticle boardArticle, Users users);
}
