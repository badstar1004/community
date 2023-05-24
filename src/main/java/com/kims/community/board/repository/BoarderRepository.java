package com.kims.community.board.repository;

import com.kims.community.board.entity.BoardArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoarderRepository extends JpaRepository<BoardArticle, Long> {


}
