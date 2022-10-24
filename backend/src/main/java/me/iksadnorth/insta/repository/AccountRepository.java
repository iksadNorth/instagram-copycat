package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Page<Account> findByNickNameContainingIgnoreCase(String nickName, Pageable pageable);

    Page<Account> findByFollowers_Follower_Id(Long id, Pageable pageable);

    Page<Account> findByFollowees_Followee_Id(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM account ORDER BY RAND()", nativeQuery = true)
    Page<Account> findByRandomOrder(Long id, Pageable pageable);
}
