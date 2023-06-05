package com.kims.community.board.service;

import static com.kims.community.exception.business.ErrorCode.ARTICLE_SAME_TITLE;
import static com.kims.community.exception.business.ErrorCode.NOT_AUTHOR_ARTICLE;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_BOARD_ARTICLE;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_USER;
import static com.kims.community.exception.business.ErrorCode.TITLE_ALREADY_EXISTS;

import com.kims.community.board.entity.ArticleComments;
import com.kims.community.board.entity.BoardArticle;
import com.kims.community.board.model.dto.ArticleCommentDto;
import com.kims.community.board.model.dto.BoardArticleResponse;
import com.kims.community.board.model.dto.BoardArticleResponse.ArticleDto;
import com.kims.community.board.model.dto.BoardListView;
import com.kims.community.board.model.form.BoardArticleForm;
import com.kims.community.board.repository.ArticleCommentsRepository;
import com.kims.community.board.repository.BoarderRepository;
import com.kims.community.exception.business.BusinessException;
import com.kims.community.users.entity.Users;
import com.kims.community.users.repository.UsersRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoarderService {

    private final BoarderRepository boarderRepository;
    private final UsersRepository usersRepository;
    private final ArticleCommentsRepository articleCommentsRepository;


    /**
     * 게시글 조회
     * @param pageable Pageable
     * @return Page<BoardListView>
     */
    public List<BoardListView> getAllBoardArticle(Pageable pageable) {
        Page<BoardArticle> boardArticlePage = boarderRepository.findAll(pageable);
        return boardArticlePage.map(BoardListView::from).toList();
    }

    /**
     * 게시글 저장
     * @param userid           유저 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse addBoardArticle(Long userid, int page, int size,
        BoardArticleForm boardArticleForm) {
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // DB 같은 제목 있는지 확인
        if(boarderRepository.existsByTitle(boardArticleForm.getTitle())){
            throw new BusinessException(TITLE_ALREADY_EXISTS);
        }

        // 같은 제목이 있는지 확인
        if (user.getBoardArticleList().stream()
            .anyMatch(
                boardArticle -> boardArticle.getTitle().equals(boardArticleForm.getTitle()))) {
            throw new BusinessException(TITLE_ALREADY_EXISTS);
        }

        // 저장
        BoardArticle boardArticle = boarderRepository.save(BoardArticle.of(user, boardArticleForm));
        user.getBoardArticleList().add(boardArticle);

        return BoardArticleResponse.builder()
            .message("게시글이 등록되었습니다.")
            .articleDto(ArticleDto.of(boardArticle, searchComments(boardArticle.getId(), page, size)))
            .build();
    }

    /**
     * 게시글 상세 조회
     * @param boardarticleid 게시글 아이디
     * @return ArticleDto
     */
    public ArticleDto articleDetailView(Long userid, Long boardarticleid, int page, int size) {
        if (!usersRepository.existsById(userid)) {
            throw new BusinessException(NOT_FOUND_USER);
        }

        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        return ArticleDto.of(boardArticle, searchComments(boardArticle.getId(), page, size));
    }

    /**
     * 댓글 조회 (페이징)
     * @param boardArticleId 게시판 아이디
     * @param page           page
     * @param size           size
     * @return List<ArticleCommentDto>
     */
    private List<ArticleCommentDto> searchComments(Long boardArticleId, int page, int size) {
        Pageable pageable =
            PageRequest.of(page, size, Sort.by("registAt").descending());

        List<ArticleComments> commentsList =
            articleCommentsRepository.findAllByBoardArticle_Id(boardArticleId, pageable);

        return commentsList.stream().map(ArticleCommentDto::from).collect(Collectors.toList());
    }

    /**
     * 게시글 수정
     * @param userid           유저 아이디
     * @param boardarticleid   게시글 아이디
     * @param boardArticleForm BoardArticleForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse articleModify(Long userid, Long boardarticleid, int page, int size,
        BoardArticleForm boardArticleForm) {
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        BoardArticle boardArticle = boarderRepository.findById(boardarticleid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // 글 쓴 사람이 맞는지 확인
        if(!boardArticle.getUserNickName().equals(user.getNickName())){
            throw new BusinessException(NOT_AUTHOR_ARTICLE);
        }

        // 같은 제목인지 확인
        if (boardArticle.getTitle().equals(boardArticleForm.getTitle())) {
            throw new BusinessException(ARTICLE_SAME_TITLE);
        }

        // 수정
        boardArticle.setTitle(boardArticleForm.getTitle());
        boardArticle.setContents(boardArticleForm.getContents());

        return BoardArticleResponse.builder()
            .message("게시글이 수정되었습니다.")
            .articleDto(ArticleDto.of(boardArticle, searchComments(boardArticle.getId(), page, size)))
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
