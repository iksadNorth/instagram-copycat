package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.LikeDto;
import me.iksadnorth.insta.model.entity.Article;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

@Service
@Transactional
public class ArticleService {
    @Autowired ArticleRepository repo;
    @Autowired CommentRepository commentRepo;
    @Autowired LikeRepository likeRepo;

    public void articleCreate(ArticleDto dto, AccountDto principal) {
        ArticleDto dtoSaving = ArticleDto.builder()
                .account(principal)
                .build();
        repo.save(dtoSaving.overWriteWith(dto).toEntity());
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

    public Boolean hasOwnership(Article trgEntity, AccountDto principal) {
        // 로그인된 유저의 권한이 ADMIN인지 확인
        for(GrantedAuthority auth : principal.getAuthorities()) {
            if(auth.getAuthority().equals(RoleType.ROLE_ADMIN.name())) {
                return true;
            }
        }
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        return trgEntity.getAccount().getId().equals(principal.getId());
    }

    public void assertOwnership(Article trgEntity, AccountDto principal) {
        if(!hasOwnership(trgEntity, principal)) {
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
        }).orElseThrow(() -> {throw new InstaApplicationException(ErrorCode.ARTICLE_NOT_FOUNDED,
                String.format("검색에서 사용되었던 id값 : %d", id)
        );});
    }

    public void articleUpdate(Long id, ArticleDto dto, AccountDto principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        Article entity = loadById(id);
        ArticleDto dtoQueried = ArticleDto.fromEntity(entity);
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(entity, principal);

        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void articleDelete(Long id, AccountDto principal) {
        // 해당 게시글의 Id가 존재하는지 확인.
        Article trgEntity = loadById(id);
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(trgEntity, principal);

        repo.deleteById(id);
    }

    public Long articleLikeCount(Long id) {
        return likeRepo.countByArticle_Id(id);
    }

    public void articleLikeAdd(Long id, AccountDto principal) {
        if(!likeRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername())) {
            LikeDto dto = LikeDto.builder()
                    .account(principal)
                    .article(loadDtoById(id))
                    .build();
            likeRepo.save(dto.toEntity());
        }
    }

    public void articleLikeDelete(Long id, AccountDto principal) {
        likeRepo.findByArticle_IdAndAccount_Email(id, principal.getUsername())
                .ifPresent(like -> likeRepo.delete(like));
    }

    public Boolean articleIsLike(Long id, AccountDto principal) {
        return likeRepo.existsByArticle_IdAndAccount_Email(id, principal.getUsername());
    }

    public Page<ArticleDto> countsWith(Stream<Article> entities, Pageable pageable) {
        Map<Long, ArticleDto> dtoTable = entities.map(ArticleDto::fromEntity)
                .collect(Collectors.toMap(ArticleDto::getId, identity()));

        commentRepo.countInBatchByArticle_Id(dtoTable.keySet()).forEach(tuple -> {
            Long idx = (Long) tuple.get(0);
            Long count = (Long) tuple.get(1);
            ArticleDto dto = dtoTable.get(idx);

            dtoTable.put(idx, dto.withNumComments(count));
        });
        likeRepo.countInBatchByArticle_Id(dtoTable.keySet()).forEach(tuple -> {
            Long idx = (Long) tuple.get(0);
            Long count = (Long) tuple.get(1);
            ArticleDto dto = dtoTable.get(idx);

            dtoTable.put(idx, dto.withNumLikes(count));
        });

        return new PageImpl<>(dtoTable.values().stream().toList(), pageable, 0);
    }

    public Page<ArticleDto> countsWith(Streamable<Article> entities, Pageable pageable) {
        return countsWith(entities.stream(), pageable);
    }

    public Page<ArticleDto> countsWith(Collection<Article> entities, Pageable pageable) {
        return countsWith(entities.stream(), pageable);
    }
}
