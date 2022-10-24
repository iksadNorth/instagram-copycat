package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.*;
import me.iksadnorth.insta.repository.*;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {
    @Autowired ArticleRepository repo;
    @Autowired CommentRepository commentRepo;
    @Autowired ViewRepository viewRepo;
    @Autowired LikeRepository likeRepo;
    @Autowired AccountService accountService;
    public void articleCreate(ArticleDto dto) {
        repo.save(dto.toEntity());
    }

    public ArticleDto articleRead(Long id) {
        return repo.findById(id).map(ArticleDto::fromEntity)
                .orElseThrow(() -> {throw new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED,
                        String.format("검색에서 사용되었던 id값 : %d", id)
                        );});
    }

    public ArticleDto articleReadWithInfo(Long id) {
        return repo.findById(id).map(article -> {
            Long articleId = article.getId();

            Long numComments = commentRepo.countByArticle_Id(articleId);
            Long numLikes = likeRepo.countByArticle_Id(articleId);
            Long numViews = viewRepo.countByArticle_Id(articleId);

            return ArticleDto.fromEntity(article, numComments, numLikes, numViews);
        }).orElseThrow(() -> {throw new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED,
                String.format("검색에서 사용되었던 id값 : %d", id)
        );});
    }

    public void articleUpdate(Long id, ArticleDto dto, UserDetails principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        ArticleDto dtoQueried = repo.findById(id)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 게시글 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 게시글의 주인인지 확인하는 단계.
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

    public void articleDelete(Long id, UserDetails principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        ArticleDto dtoQueried = repo.findById(id)
                .map(ArticleDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 게시글 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 게시글의 주인인지 확인하는 단계.
        if(count <= 0 && repo.existsByIdAndAccount_Email(id, principal.getUsername())) {
            throw new InstaApplicationException(
                    ErrorCode.OWNERSHIP_NOT_FOUNDED,
                    String.format("변경을 요구한 계정의 id값: %s", id)
            );
        } // TODO: articleUpdate와의 코드 중복 해결하기.

        repo.deleteById(id);    // TODO: 삭제 시, 실제로 삭제하는 것이 아닌 deletedAt만 추가하고 지우지 않기.
    }

    public Long articleViewCount(Long id) {
        return viewRepo.countByArticle_Id(id);
    }

    public void articleViewAdd(Long id, UserDetails principal) {
        if(!viewRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername())) {
            ViewDto dto = ViewDto.builder().article(articleRead(id)).build();
            viewRepo.save(dto.toEntity());
        }
    }

    public void articleViewDelete(Long id, UserDetails principal) {
        viewRepo.findByArticle_IdAndAccount_Email(id, principal.getUsername())
                .ifPresent(view -> viewRepo.delete(view));
    }

    public Long articleLikeCount(Long id) {
        return likeRepo.countByArticle_Id(id);
    }

    public void articleLikeAdd(Long id, UserDetails principal) {
        if(!likeRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername())) {
            LikeDto dto = LikeDto.builder()
                    .account(accountService.loadUserByUsername(principal.getUsername()))
                    .article(articleRead(id))
                    .build();
            likeRepo.save(dto.toEntity());
        }
    }

    public void articleLikeDelete(Long id, UserDetails principal) {
        likeRepo.findByArticle_IdAndAccount_Email(id, principal.getUsername())
                .ifPresent(like -> likeRepo.delete(like));
    }

    public Boolean articleIsLike(Long id, UserDetails principal) {
        return likeRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername());
    }
}
