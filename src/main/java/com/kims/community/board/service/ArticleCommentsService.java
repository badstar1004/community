package com.kims.community.board.service;

import static com.kims.community.exception.business.ErrorCode.AUTHOR_DIFFERENT_COMMENT;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_ARTICLE_COMMENTS;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_BOARD_ARTICLE;
import static com.kims.community.exception.business.ErrorCode.NOT_FOUND_USER;

import com.kims.community.board.entity.ArticleComments;
import com.kims.community.board.entity.BoardArticle;
import com.kims.community.board.model.dto.ArticleCommentDto;
import com.kims.community.board.model.dto.BoardArticleResponse;
import com.kims.community.board.model.dto.BoardArticleResponse.ArticleDto;
import com.kims.community.board.model.form.ArticleCommentsForm;
import com.kims.community.board.repository.ArticleCommentsRepository;
import com.kims.community.board.repository.BoarderRepository;
import com.kims.community.exception.business.BusinessException;
import com.kims.community.users.entity.Users;
import com.kims.community.users.repository.UsersRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleCommentsService {

    private final ArticleCommentsRepository articleCommentsRepository;
    private final UsersRepository usersRepository;
    private final BoarderRepository boarderRepository;


    /**
     * 댓글 저장
     * @param userid              유저 아이디
     * @param boardArticleId      게시글 아이디
     * @param articleCommentsForm ArticleCommentsForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse articleCommentsAdd(Long userid, Long boardArticleId, int page,
        int size, ArticleCommentsForm articleCommentsForm) {

        // user
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // board
        BoardArticle boardArticle = boarderRepository.findById(boardArticleId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // 저장
        ArticleComments articleComments = articleCommentsRepository.save(
            ArticleComments.of(user, articleCommentsForm));

        boardArticle.getArticleCommentsList().add(articleComments);
        user.getArticleCommentsList().add(articleComments);

        return BoardArticleResponse.builder()
            .message("댓글이 등록되었습니다.")
            // 댓글 페이지 형식으로 반환
            .articleDto(ArticleDto.of(boardArticle, searchComments(boardArticleId, page, size)))
            .build();
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
     * 댓글 수정
     * @param userid              유저 아이디
     * @param boardArticleId      게시글 아이디
     * @param articlecommentsId   댓글 아이디
     * @param articleCommentsForm ArticleCommentsForm
     * @return BoardArticleResponse
     */
    @Transactional
    public BoardArticleResponse articleCommentsModify(Long userid, Long boardArticleId, int page,
        int size, Long articlecommentsId, ArticleCommentsForm articleCommentsForm) {

        // user
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // board
        BoardArticle boardArticle = boarderRepository.findById(boardArticleId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // comments
        ArticleComments articleComments = articleCommentsRepository.findById(articlecommentsId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE_COMMENTS));

        // 댓글이 같은 작성자인지 확인
        if (!user.getNickName().equals(articleComments.getUserNickName())) {
            throw new BusinessException(AUTHOR_DIFFERENT_COMMENT);
        }

        // 수정
        articleComments.setContents(articleCommentsForm.getContents());

        return BoardArticleResponse.builder()
            .message("댓글이 수정되었습니다.")
            // 댓글 페이지 형식으로 반환
            .articleDto(ArticleDto.of(boardArticle, searchComments(boardArticleId, page, size)))
            .build();
    }

    /**
     * 댓글 삭제
     * @param userid            유저 아이디
     * @param boardArticleId    게시판 아이디
     * @param articlecommentsId 댓글 아이디
     * @return String
     */
    @Transactional
    public BoardArticleResponse articleCommentsDelete(Long userid, Long boardArticleId, Long articlecommentsId,
        int page, int size) {

        // user
        Users user = usersRepository.findById(userid)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_USER));

        // board
        BoardArticle boardArticle = boarderRepository.findById(boardArticleId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_BOARD_ARTICLE));

        // comments
        ArticleComments articleComments = articleCommentsRepository.findById(articlecommentsId)
            .orElseThrow(() -> new BusinessException(NOT_FOUND_ARTICLE_COMMENTS));

        // 댓글이 같은 작성자인지 확인
        if (!user.getNickName().equals(articleComments.getUserNickName())) {
            throw new BusinessException(AUTHOR_DIFFERENT_COMMENT);
        }

        articleCommentsRepository.delete(articleComments);

        return BoardArticleResponse.builder()
            .message("댓글이 삭제되었습니다.")
            // 댓글 페이지 형식으로 반환
            .articleDto(ArticleDto.of(boardArticle, searchComments(boardArticleId, page, size)))
            .build();
    }
}
