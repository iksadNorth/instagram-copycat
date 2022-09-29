package me.iksadnorth.insta.service;

import me.iksadnorth.insta.config.properies.JwtProperties;
import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.repository.AccountRepository;
import me.iksadnorth.insta.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    @Autowired AccountRepository repo;

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

    public AccountDto getId(Long id) {
        return repo.findById(id).map(AccountDto::fromEntity)
                .orElseThrow(() ->
                        new InstaApplicationException(
                            ErrorCode.INVALID_ID,
                            String.format("해당 id의 계정은 존재하지 않습니다. Id: %s", id)
                ));
    }

    public String login(String email, String password) {
        Optional.of(loadUserByUsername(email))
                .filter(account -> account.getPassword() == password)
                .orElseThrow(() -> new InstaApplicationException(ErrorCode.INVALID_PASSWORD));
        return JwtTokenUtils.getAccessToken(
                email, jwtProperties.getSecretKey(), jwtProperties.getExpiredTimeMs()
        );
    }
}
