package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.LikeDto;
import me.iksadnorth.insta.model.entity.Article;
import me.iksadnorth.insta.repository.ArticleRepository;
import me.iksadnorth.insta.repository.CommentRepository;
import me.iksadnorth.insta.repository.LikeRepository;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
    @Autowired ArticleRepository repo;
    @Autowired CommentRepository commentRepo;
    @Autowired LikeRepository likeRepo;
    @Autowired AccountService accountService;
    public void articleCreate(ArticleDto dto) {
        repo.save(dto.toEntity());
    }

    public ArticleDto loadDtoById(Long id) {
        return ArticleDto.fromEntity(loadById(id));
    }

    public Article loadById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> {throw new InstaApplicationException(ErrorCode.ARTICLE_NOT_FOUNDED,
                        String.format("검색에서 사용되었던 id값 : %d", id)
                );});
    }

    public Boolean hasOwnership(Long idGonnaChange, UserDetails principal) {
        // 로그인된 유저의 권한이 ADMIN인지 확인
        for(GrantedAuthority auth : principal.getAuthorities()) {
            if(auth.getAuthority().equals(RoleType.ROLE_ADMIN.name())) {
                return true;
            }
        }
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        return repo.existsByIdAndAccount_Email(idGonnaChange, principal.getUsername());
    }

    public void assertOwnership(Long idGonnaChange, UserDetails principal) {
        if(!hasOwnership(idGonnaChange, principal)) {
            throw new InstaApplicationException(
                    ErrorCode.NOT_BELONGING_TO_YOU,
                    String.format("변경을 요구한 계정의 id값: %s", principal.getId())
            );
        }
    }

    public ArticleDto articleReadWithInfo(Long id) {
        return repo.findById(id).map(article -> {
            Long articleId = article.getId();

            Long numComments = commentRepo.countByArticle_Id(articleId);
            Long numLikes = likeRepo.countByArticle_Id(articleId);

            return ArticleDto.fromEntity(article, numComments, numLikes);
        }).orElseThrow(() -> {throw new InstaApplicationException(ErrorCode.ID_NOT_FOUNDED,
                String.format("검색에서 사용되었던 id값 : %d", id)
        );});
    }

    public void articleUpdate(Long id, ArticleDto dto, UserDetails principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        ArticleDto dtoQueried = loadDtoById(id);
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(id, principal);

        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void articleDelete(Long id, UserDetails principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        loadById(id);
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(id, principal);

        repo.deleteById(id);
    }

    public Long articleLikeCount(Long id) {
        return likeRepo.countByArticle_Id(id);
    }

    public void articleLikeAdd(Long id, UserDetails principal) {
        if(!likeRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername())) {
            LikeDto dto = LikeDto.builder()
                    .account(accountService.loadUserByUsername(principal.getUsername()))
                    .article(loadDtoById(id))
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
