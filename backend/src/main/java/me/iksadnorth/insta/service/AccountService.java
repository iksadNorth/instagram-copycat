package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired AccountRepository repo;

    @Override
    public AccountDto loadUserByUsername(String email) throws UsernameNotFoundException {
        return repo.findByEmail(email)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - email: " + email));
    }

    public AccountDto getId(Long id) {
        return repo.findById(id).map(AccountDto::fromEntity)
                .orElseThrow(() -> new InstaApplicationException(
                        ErrorCode.INVALID_ID,
                        String.format("해당 id의 계정은 존재하지 않습니다. Id: %s", id)
                ));
    }
}
