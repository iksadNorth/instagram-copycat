package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.dto.LikeDto;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.model.entity.Comment;
import me.iksadnorth.insta.model.entity.Likes;
import me.iksadnorth.insta.repository.ArticleRepository;
import me.iksadnorth.insta.repository.CommentRepository;
import me.iksadnorth.insta.repository.LikeRepository;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

@Service
@Transactional
public class CommentService {
    @Autowired ArticleService articleService;
    @Autowired CommentRepository repo;
    @Autowired LikeRepository likeRepo;

    public void commentCreateToArticle(String content, AccountDto accountDto, Long pid) {
        ArticleDto articleDto = articleService.loadDtoById(pid);
        CommentDto commentDto = CommentDto.of(content, accountDto, articleDto);
        repo.save(commentDto.toEntity());
    }

    public void commentCreateToComment(String content, AccountDto accountDto, Long cid) {
        CommentDto parentDto = commentReadByIdWithArticle(cid);
        ArticleDto articleDto = parentDto.getArticle();
        CommentDto commentDto = CommentDto.of(content, accountDto, articleDto, parentDto);
        repo.save(commentDto.toEntity());
    }

    public CommentDto commentReadById(Long id) {
        return repo.findById(id).map(CommentDto::fromEntity).orElseThrow(() -> {
            throw new InstaApplicationException(
                    ErrorCode.COMMENT_NOT_FOUNDED,
                    String.format("해당 Id값: %d", id)
            );}
        );
    }

    public CommentDto commentReadByIdWithArticle(Long id) {
        return repo.findWithArticleById(id).map(CommentDto::fromEntity).orElseThrow(() -> {
            throw new InstaApplicationException(
                    ErrorCode.COMMENT_NOT_FOUNDED,
                    String.format("해당 Id값: %d", id)
            );}
        );
    }

    public Page<CommentDto> commentRead(Long id, Pageable pageable) {
        return countsWith(repo.findByArticle_IdAndParentIsNull(id, pageable), pageable);
    }

    public Page<CommentDto> childrenRead(Long id, Pageable pageable) {
        return countsWith(repo.findByParent_Id(id, pageable), pageable);
    }

    public void commentUpdate(Long id, CommentDto dto, AccountDto principal) {
        // 해당 댓글의 Id가 존재하는지 확인.
        Comment comment = loadCommentWithAccountById(id);
        CommentDto dtoQueried = CommentDto.fromEntity(comment);
        // 댓글 접근 권한을 확인하는 단계.
        assertOwnership(comment, principal);

        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void commentDelete(Long id, AccountDto principal) {
        // 해당 댓글의 Id가 존재하는지 확인.
        Comment comment = loadCommentWithAccountById(id);
        // 댓글 접근 권한을 확인하는 단계.
        assertOwnership(comment, principal);

        repo.delete(comment);
    }

    public void commentCreateLikes(AccountDto dto, Long id) {
        CommentDto commentDto = commentReadById(id);
        LikeDto likeDto = LikeDto.of(dto, commentDto);
        likeRepo.save(likeDto.toEntity());
    }

    public void commentDeleteLikes(AccountDto dto, Long id) {
        Likes likeEntity = likeRepo.findByAccount_IdAndComment_Id(dto.getId(), id)
                .orElseThrow(() -> new InstaApplicationException(
                        ErrorCode.COMMENT_NOT_FOUNDED,
                        String.format("해당 유저 Id와 댓글 Id: %s, %s", dto.getId(), id)
                ));
        likeRepo.delete(likeEntity);
    }

    public Boolean commentIsLikes(AccountDto dto, Long id) {
        return likeRepo.existsByAccount_IdAndComment_Id(dto.getId(), id);
    }
    public Page<CommentDto> countsWith(Stream<Comment> entities, Pageable pageable) {
        Map<Long, CommentDto> dtoTable = entities.map(CommentDto::fromEntity)
                .collect(Collectors.toMap(CommentDto::getId, identity()));

        repo.countInBatchByParent_Id(dtoTable.keySet()).forEach(tuple -> {
            Long idx = (Long) tuple.get(0);
            Long count = (Long) tuple.get(1);
            CommentDto dto = dtoTable.get(idx);

            dtoTable.put(idx, dto.withNumChildren(count));
        });
        likeRepo.countInBatchByComment_Id(dtoTable.keySet()).forEach(tuple -> {
            Long idx = (Long) tuple.get(0);
            Long count = (Long) tuple.get(1);
            CommentDto dto = dtoTable.get(idx);

            dtoTable.put(idx, dto.withNumLikes(count));
        });

        return new PageImpl<>(dtoTable.values().stream().toList(), pageable, 0);
    }

    public Page<CommentDto> countsWith(Streamable<Comment> entities, Pageable pageable) {
        return countsWith(entities.stream(), pageable);
    }

    public Page<CommentDto> countsWith(Collection<Comment> entities, Pageable pageable) {
        return countsWith(entities.stream(), pageable);
    }

    public Comment loadCommentWithAccountById(Long id) {
        return repo.findWithAccountById(id).orElseThrow(() -> {
                    throw new InstaApplicationException(
                            ErrorCode.COMMENT_NOT_FOUNDED,
                            String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                    );
                });
    }

    public Boolean hasOwnership(Comment trgEntity, AccountDto principal) {
        // 로그인된 유저의 권한이 ADMIN인지 확인
        for(GrantedAuthority auth : principal.getAuthorities()) {
            if(auth.getAuthority().equals(RoleType.ROLE_ADMIN.name())) {
                return true;
            }
        }
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        return trgEntity.getAccount().getId().equals(principal.getId());
    }

    public void assertOwnership(Comment trgEntity, AccountDto principal) {
        if(!hasOwnership(trgEntity, principal)) {
            throw new InstaApplicationException(
                    ErrorCode.NOT_BELONGING_TO_YOU,
                    String.format("변경을 요구한 계정의 id값: %s", principal.getId())
            );
        }
    }
}
