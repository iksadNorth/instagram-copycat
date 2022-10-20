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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService implements UserDetailsService {
    @Autowired AccountRepository repo;
    @Autowired ArticleRepository articleRepo;
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
        repo.save(dto.toEntity());
    }

    public AccountDto loadById(Long id) {
        Account entity = repo.findById(id)
                .orElseThrow(() -> {throw new InstaApplicationException(
                        ErrorCode.ID_NOT_FOUNDED, String.format("해당 연산에서 사용된 ID값 : %d", id)
                );});
        // 작성자가 쓴 글 목록.
        Long articles = articleRepo.countByAccount_Id(id);
        // 작성자를 팔로우한 사람 수.
        Long followees = followRepo.countByFollowee_Id(id);
        // 작성자가 팔로우한 사람 수.
        Long followers = followRepo.countByFollower_Id(id);

        return AccountDto.fromEntity(entity, articles, followees, followers);
    }

    public void accountUpdate(Long id, AccountDto dto, UserDetails principal) {
        // 해당 계정의 Id가 존재하는지 확인.
        AccountDto dtoQueried = repo.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> {
                    throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 계정 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        if(count <= 0) {
            repo.findByEmail(principal.getUsername())
                    .map(Account::getId)
                    .filter(x -> x.equals(id))
                    .orElseThrow(() ->
                            {
                                throw new InstaApplicationException(
                                        ErrorCode.OWNERSHIP_NOT_FOUNDED,
                                        String.format("변경을 요구한 계정의 id값: %s", id)
                                );
                            }
                    );
        }
        // 수정 사항 반영.
        repo.save(
                dtoQueried.overWriteWith(dto).toEntity()
        );
    }

    public void accountDelete(Long id, UserDetails principal) {
        // 해당 계정의 Id가 존재하는지 확인.
        AccountDto dtoQueried = repo.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 계정 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        if(count <= 0) {
            repo.findByEmail(principal.getUsername())
                    .map(Account::getId)
                    .filter(x -> x.equals(id))
                    .orElseThrow(() ->
                            {
                                throw new InstaApplicationException(
                                        ErrorCode.OWNERSHIP_NOT_FOUNDED,
                                        String.format("변경을 요구한 계정의 id값: %s", id)
                                );
                            }
                    );
        } //    TODO: accountUpdate와의 코드 중복 해결하기.

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

    public void doFollow(UserDetails principal, Long follower_id) {
        Account follower = loadUserByUsername(principal.getUsername()).toEntity();
        Account followee = repo.findById(follower_id)
                .orElseThrow(
                        () -> new InstaApplicationException(
                                ErrorCode.ID_NOT_FOUNDED,
                                String.format("다음 follower_id값이 존재하지 않습니다. \nId: %s", follower_id)
                        )
                );

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

    public void undoFollow(UserDetails principal, Long follower_id) {
        Account follower = loadUserByUsername(principal.getUsername()).toEntity();
        Account followee = repo.findById(follower_id)
                .orElseThrow(
                        () -> new InstaApplicationException(
                                ErrorCode.ID_NOT_FOUNDED,
                                String.format("다음 follower_id값이 존재하지 않습니다. \nId: %s", follower_id)
                        )
                );

        Follow entity = followRepo.findByFollower_IdAndFollowee_Id(follower.getId(), followee.getId()).orElseThrow(() -> {
                    throw new InstaApplicationException(
                            ErrorCode.FOLLOW_NOT_FOUNDED,
                            String.format("팔로우 주체: %s\n팔로우 대상: %s", follower.getId(), followee.getId())
                    );
                }
        );

        followRepo.delete(entity);
    }

    public Long countArticles(Long id) { return articleRepo.countByAccount_Id(id); }

    public Page<ArticleDto> loadFeedById(Long id, Pageable pageable) {
        return articleRepo.findFeedListById(id, pageable).map(ArticleDto::fromEntity);
    }

    public Page<ArticleDto> loadExploreById(Long id, Pageable pageable, UserDetails principal) {
        // 해당 계정의 Id가 존재하는지 확인.
        AccountDto dtoQueried = repo.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> {
                            throw new InstaApplicationException(
                                    ErrorCode.ID_NOT_FOUNDED,
                                    String.format("다음 Id값이 존재하지 않습니다. \nId: %s", id)
                            );
                        }
                );
        // 계정 접근 권한을 확인하는 단계.
        // 로그인된 유저의 권한이 ADMIN인지 확인
        long count = principal.getAuthorities().stream()
                .map(SimpleGrantedAuthority.class::cast)
                .map(Object::toString)
                .filter(x -> x.equals(RoleType.ROLE_ADMIN.name()))
                .count();
        // 로그인된 유저의 권한이 ADMIN이 아니라면 계정의 주인인지 확인하는 단계.
        if(count <= 0) {
            repo.findByEmail(principal.getUsername())
                    .map(Account::getId)
                    .filter(x -> x .equals(id))
                    .orElseThrow(() ->
                            {
                                throw new InstaApplicationException(
                                        ErrorCode.OWNERSHIP_NOT_FOUNDED,
                                        String.format("변경을 요구한 계정의 id값: %s", id)
                                );
                            }
                    );
        } //    TODO: accountUpdate와의 코드 중복 해결하기.
        return articleRepo.findRandListById(id, pageable).map(ArticleDto::fromEntity);
    }

    public Page<ArticleDto> loadArticlesById(Long id, Pageable pageable) {
        return articleRepo.findByAccount_Id(id, pageable).map(ArticleDto::fromEntity);
    }
}
