package com.kims.community.board.repository;

import com.kims.community.board.entity.ArticleComments;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentsRepository extends JpaRepository<ArticleComments, Long> {

    List<ArticleComments> findAllByBoardArticle_Id(Long boardArticleId, Pageable page);

}
