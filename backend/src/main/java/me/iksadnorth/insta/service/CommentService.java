package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.model.dto.LikeDto;
import me.iksadnorth.insta.model.entity.Likes;
import me.iksadnorth.insta.repository.CommentRepository;
import me.iksadnorth.insta.repository.LikeRepository;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
                    ErrorCode.ID_NOT_FOUNDED,
                    String.format("해당 Id값: %d", id)
            );}
        );
    }

    public CommentDto commentReadByIdWithArticle(Long id) {
        return repo.findByIdWithArticle(id).map(CommentDto::fromEntity).orElseThrow(() -> {
            throw new InstaApplicationException(
                    ErrorCode.ID_NOT_FOUNDED,
                    String.format("해당 Id값: %d", id)
            );}
        );
    }

    public Page<CommentDto> commentRead(Long id, Pageable pageable) {
        return repo.findByArticle_IdAndParentIsNull(id, pageable).map(comment -> {
            Long numLikes = likeRepo.countByComment_Id(comment.getId());
            Long numChildren = repo.countByParent_Id(comment.getId());

            return CommentDto
                    .fromEntity(comment)
                    .withNum(numLikes, numChildren);
        });
    }

    public Page<CommentDto> childrenRead(Long id, Pageable pageable) {
        return repo.findByParent_Id(id, pageable).map(comment -> {
            Long numLikes = likeRepo.countByComment_Id(comment.getId());
            Long numChildren = repo.countByParent_Id(comment.getId());

            return CommentDto.fromEntity(comment).withNum(numLikes, numChildren);
        });
    }

    public void commentUpdate(Long id, CommentDto dto, UserDetails principal) {
        // 해당 댓글의 Id가 존재하는지 확인.
        CommentDto dtoQueried = repo.findById(id)
                .map(CommentDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 댓글 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 댓글의 주인인지 확인하는 단계.
        if(count <= 0 && repo.existsByIdAndAccount_Email(id, principal.getUsername())) {
            throw new InstaApplicationException(
                    ErrorCode.OWNERSHIP_NOT_FOUNDED,
                    String.format("변경을 요구한 계정의 id값: %s", id)
            );
        }

        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void commentDelete(Long id, UserDetails principal) {
        // 해당 댓글의 Id가 존재하는지 확인.
        CommentDto dtoQueried = repo.findById(id)
                .map(CommentDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 댓글 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 댓글의 주인인지 확인하는 단계.
        if(count <= 0 && repo.existsByIdAndAccount_Email(id, principal.getUsername())) {
            throw new InstaApplicationException(
                    ErrorCode.OWNERSHIP_NOT_FOUNDED,
                    String.format("변경을 요구한 계정의 id값: %s", id)
            );
        } // TODO: articleCommentUpdate와의 코드 중복 해결하기.

        CommentDto dto = CommentDto.builder()
                .deletedAt(LocalDateTime.now())
                .build();

        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void commentCreateLikes(AccountDto dto, Long id) {
        CommentDto commentDto = commentReadById(id);
        LikeDto likeDto = LikeDto.of(dto, commentDto);
        likeRepo.save(likeDto.toEntity());
    }

    public void commentDeleteLikes(AccountDto dto, Long id) {
        Likes likeEntity = likeRepo.findByAccount_IdAndComment_Id(dto.getId(), id)
                .orElseThrow(() -> new InstaApplicationException(
                        ErrorCode.ID_NOT_FOUNDED,
                        String.format("해당 유저 Id와 댓글 Id: %s, %s", dto.getId(), id)
                ));
        likeRepo.delete(likeEntity);
    }

    public Boolean commentIsLikes(AccountDto dto, Long id) {
        return likeRepo.existsByAccount_IdAndComment_Id(dto.getId(), id);
    }
}
