package com.kims.community.board.repository;

import com.kims.community.board.entity.BoardArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoarderRepository extends JpaRepository<BoardArticle, Long> {

    /**
     * 조회 (boardarticleId 기준)
     * @param id must not be {@literal null}.
     * @return boolean
     */
    boolean existsById(Long id);

    /**
     * 조회 (title 기준)
     * @param title 제목
     * @return boolean
     */
    boolean existsByTitle(String title);

}
