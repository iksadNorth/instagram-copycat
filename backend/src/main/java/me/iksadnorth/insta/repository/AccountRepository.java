package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.dto.AccountDto;
import me.iksadnorth.insta.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Page<Account> findByNickNameContainingIgnoreCase(String nickName, Pageable pageable);
}
