package me.iksadnorth.insta.service;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.entity.Account;
import me.iksadnorth.insta.model.entity.Follow;
import me.iksadnorth.insta.repository.AccountRepository;
import me.iksadnorth.insta.repository.ArticleRepository;
import me.iksadnorth.insta.repository.FollowRepository;
import me.iksadnorth.insta.type.RoleType;
import me.iksadnorth.insta.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class AccountService implements UserDetailsService {
    @Autowired AccountRepository repo;
    @Autowired ArticleRepository articleRepo;
    @Autowired ArticleService articleService;
    @Autowired FollowRepository followRepo;

    @Autowired JwtProperties jwtProperties;

    @Override
    public AccountDto loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email)
                .map(AccountDto::fromEntity)
                .orElseThrow(() ->
                        new InstaApplicationException(
                            ErrorCode.USER_NOT_FOUNDED,
                            String.format("로그인을 시도한 email: %s", email)));
    }

    public Account loadUserById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> {throw new InstaApplicationException(
                        ErrorCode.USER_NOT_FOUNDED, String.format("해당 연산에서 사용된 ID값 : %d", id)
                );});
    }

    public Boolean hasOwnership(Long idGonnaChange, AccountDto dtoLogin) {
        // 로그인된 유저의 권한이 ADMIN인지 확인
        for(GrantedAuthority auth : dtoLogin.getAuthorities()) {
            if(auth.getAuthority().equals(RoleType.ROLE_ADMIN.name())) {
                return true;
            }
        }
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        return dtoLogin.getId().equals(idGonnaChange);
    }

    public void assertOwnership(Long idGonnaChange, AccountDto dtoLogin) {
        if(!hasOwnership(idGonnaChange, dtoLogin)) {
            throw new InstaApplicationException(
                    ErrorCode.NOT_BELONGING_TO_YOU,
                    String.format("변경을 요구한 계정의 id값: %s", dtoLogin.getId())
            );
        }
    }

    public String login(String email, String password) {
        Optional.of(loadUserByUsername(email))
                .filter(account -> account.getPassword().equals(password))
                .orElseThrow(() -> new InstaApplicationException(
                        ErrorCode.INVALID_PASSWORD,
                        String.format("\n접속을 시도한 이메일: \n%s", email)
                ));
        return JwtTokenUtils.getAccessToken(
                email, jwtProperties.getSecretKey(), jwtProperties.getExpiredTimeMs()
        );
    }

    public void accountCreate(AccountDto dto) {
        repo.findByEmail(dto.getEmail())
                .ifPresent(account -> {throw new InstaApplicationException(
                                ErrorCode.DUPLICATED_USER,
                                String.format("다음 계정이 중복이 됩니다. \nId: %s,\nemail: %s", account.getId(), account.getEmail())
                        );}
                );
        Account entity = dto.toEntity();
        entity.setRole(RoleType.ROLE_USER);
        repo.save(entity);
    }

    public AccountDto loadById(Long id) {
        Account entity = loadUserById(id);
        // 작성자가 쓴 글 목록.
        Long articles = articleRepo.countByAccount_Id(id);
        // 작성자를 팔로우한 사람 수.
        Long followees = followRepo.countByFollowee_Id(id);
        // 작성자가 팔로우한 사람 수.
        Long followers = followRepo.countByFollower_Id(id);

        return AccountDto.fromEntity(entity, articles, followees, followers);
    }

    public AccountDto loadById(AccountDto dto) {
        Account entity = dto.toEntity();
        Long id = dto.getId();

        // 작성자가 쓴 글 목록.
        Long articles = articleRepo.countByAccount_Id(id);
        // 작성자를 팔로우한 사람 수.
        Long followees = followRepo.countByFollowee_Id(id);
        // 작성자가 팔로우한 사람 수.
        Long followers = followRepo.countByFollower_Id(id);

        return AccountDto.fromEntity(entity, articles, followees, followers);
    }

    public void accountUpdate(Long id, AccountDto dtoForUpdating, AccountDto dtoLogin) {
        // 해당 계정의 Id가 존재하는지 확인.
        AccountDto dtoQueried = AccountDto.fromEntity(loadUserById(id));
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(id, dtoLogin);
        // 수정 사항 반영.
        repo.save(
                dtoQueried.overWriteWith(dtoForUpdating).toEntity()
        );
    }

    public void accountDelete(Long id, AccountDto dtoLogin) {
        // 해당 계정의 Id가 존재하는지 확인.
        loadUserById(id);
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(id, dtoLogin);
        // 해당 계정 삭제.
        repo.deleteById(id);
    }

    public Page<AccountDto> loadByNickName(String nickName, Pageable pageable) {
        return repo.findByNickNameContainingIgnoreCase(nickName, pageable)
                .map(AccountDto::fromEntity);
    }

    public Long countFollowings(Long id) {
        return followRepo.countByFollower_Id(id);
    }

    public Long countFollowers(Long id) {
        return followRepo.countByFollowee_Id(id);
    }

    public void doFollow(AccountDto dtoLogin, Long follower_id) {
        Account follower = dtoLogin.toEntity();
        Account followee = loadUserById(follower_id);

        if(followRepo.existsByFollower_IdAndFollowee_Id(follower.getId(), followee.getId())) {
            throw new InstaApplicationException(
                    ErrorCode.DUPLICATED_FOLLOW,
                    String.format("팔로우 주체: %s\n팔로우 대상: %s", follower.getId(), followee.getId())
            );
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowee(followee);

        followRepo.save(follow);
    }

    public void undoFollow(AccountDto dtoLogin, Long follower_id) {
        Account follower = dtoLogin.toEntity();
        Account followee = loadUserById(follower_id);

        Follow entity = followRepo.findByFollower_IdAndFollowee_Id(follower.getId(), followee.getId())
                .orElseThrow(() -> {
                    throw new InstaApplicationException(
                            ErrorCode.FOLLOW_NOT_FOUNDED,
                            String.format("팔로우 주체: %s\n팔로우 대상: %s", follower.getId(), followee.getId())
                    );
                }
        );

        followRepo.delete(entity);
    }

    public Boolean isFollow(AccountDto dtoLogin, Long follower_id) {
        Account follower = dtoLogin.toEntity();
        Account followee = loadUserById(follower_id);

        return followRepo.existsByFollower_IdAndFollowee_Id(follower.getId(), followee.getId());
    }

    public Long countArticles(Long id) { return articleRepo.countByAccount_Id(id); }

    public Page<ArticleDto> loadFeedById(Long id, Pageable pageable) {
        return articleService.countsWith(
                articleRepo.findByAccount_Followees_Follower_Id(id, pageable)
                ,pageable
        );
    }

    public Page<ArticleDto> loadExploreById(Long id, Pageable pageable, AccountDto dtoLogin) {
        // 계정 접근 권한을 확인하는 단계.
        assertOwnership(id, dtoLogin);
        // 추천 알고리즘에 의해 id값이 선택되었다고 가정.
        // 실제 서비스 환경을 모사할 때 주의해야 하는 부분.
        List<Long> ids = List.of(1L, 2L, 3L);
        return articleService.countsWith(articleRepo.findRandListById(ids, pageable), pageable);
    }

    public Page<ArticleDto> loadArticlesById(Long id, Pageable pageable) {
        return articleRepo.findByAccount_Id(id, pageable).map(ArticleDto::fromEntity);
    }

    public Page<AccountDto> readFollowings(Long id, Pageable pageable) {
        return repo.findByFollowers_Follower_Id(id, pageable).map(AccountDto::fromEntity);
    }

    public Page<AccountDto> readFollowers(Long id, Pageable pageable) {
        return repo.findByFollowees_Followee_Id(id, pageable).map(AccountDto::fromEntity);
    }

    public Page<AccountDto> recommendFollowing(Long id, Pageable pageable) {
        return repo.findByRandomOrder(id, pageable).map(AccountDto::fromEntity);
    }
}
