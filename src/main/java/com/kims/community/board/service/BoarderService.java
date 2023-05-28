package com.kims.community.board.service;

import static com.kims.community.exception.business.ErrorCode.ARTICLE_SAME_TITLE;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_BOARD_ARTICLE;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_USER;
import static com.kims.community.exception.business.ErrorCode.TITLE_ALREADY_EXISTS;

import com.kims.community.board.entity.BoardArticle;
import com.kims.community.board.model.dto.BoardArticleResponse;
import com.kims.community.board.model.dto.BoardArticleResponse.ArticleDto;
import com.kims.community.board.model.dto.BoardListView;
import com.kims.community.board.model.form.BoardArticleForm;
import com.kims.community.board.repository.BoarderRepository;
import com.kims.community.exception.business.BusinessException;
import com.kims.community.users.entity.Users;
import com.kims.community.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoarderService {

    private final BoarderRepository boarderRepository;
    private final UsersRepository usersRepository;


    /**
     * 게시글 조회
     * @param pageable Pageable
     * @return Page<BoardListView>
     */
    public Page<BoardListView> getAllBoardArticle(Pageable pageable) {
        Page<BoardArticle> boardArticlePage = boarderRepository.findAll(pageable);
        return boardArticlePage.map(BoardListView::from);
    }

    /**
     * 게시글 저장
     * @param userid           유저 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse addBoardArticle(Long userid, BoardArticleForm boardArticleForm) {
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // 같은 제목이 있는지 확인
        if (user.getBoardArticles().stream()
            .anyMatch(
                boardArticle -> boardArticle.getTitle().equals(boardArticleForm.getTitle()))) {
            throw new BusinessException(TITLE_ALREADY_EXISTS);
        }

        // DB 같은 제목 있는지 확인
        if(!boarderRepository.existsByTitle(boardArticleForm.getTitle())){
            throw new BusinessException(TITLE_ALREADY_EXISTS);
        }

        // 저장
        BoardArticle boardArticle = boarderRepository.save(BoardArticle.of(user, boardArticleForm));
        user.getBoardArticles().add(boardArticle);

        return BoardArticleResponse.builder()
            .message("게시글이 등록되었습니다.")
            .articleDto(ArticleDto.from(boardArticle))
            .build();
    }

    /**
     * 게시글 상세 조회
     * @param boardarticleid 게시글 아이디
     * @return ArticleDto
     */
    public ArticleDto articleDetailView(Long userid, Long boardarticleid) {
        if (!usersRepository.existsById(userid)) {
            throw new BusinessException(NOT_FOUND_USER);
        }

        BoardArticle article = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        return ArticleDto.from(article);
    }

    /**
     * 게시글 수정
     * @param userid           유저 아이디
     * @param boardarticleid   게시글 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse articleModify(Long userid, Long boardarticleid,
        BoardArticleForm boardArticleForm) {
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // 같은 제목인지 확인
        if (boardArticle.getTitle().equals(boardArticleForm.getTitle())) {
            throw new BusinessException(ARTICLE_SAME_TITLE);
        }

        // 수정
        boardArticle.setTitle(boardArticleForm.getTitle());
        boardArticle.setContents(boardArticleForm.getContents());
        user.getBoardArticles().add(BoardArticle.of(user, boardArticleForm));

        return BoardArticleResponse.builder()
            .message("게시글이 수정되었습니다.")
            .articleDto(ArticleDto.from(boardArticle))
            .build();
    }

    /**
     * 게시글 삭제
     * @param userid         유저 아이디
     * @param boardarticleid 게시글 아이디
     * @return String
     */
    public String articleDelete(Long userid, Long boardarticleid) {
        if (!usersRepository.existsById(userid)) {
            throw new BusinessException(NOT_FOUND_USER);
        }

        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        boarderRepository.delete(boardArticle);

        return "게시글이 삭제되었습니다.";
    }

}
