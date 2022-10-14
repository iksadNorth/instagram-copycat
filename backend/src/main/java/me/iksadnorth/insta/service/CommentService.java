package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.CommentDto;
import me.iksadnorth.insta.repository.CommentRepository;
import me.iksadnorth.insta.type.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired CommentRepository repo;

    public void commentCreate(CommentDto dto) {
        repo.save(dto.toEntity());
    }

    public Page<CommentDto> commentRead(Long id, Pageable pageable) {
        return repo.findByParent_Id(id, pageable).map(CommentDto::fromEntity);
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
                .filter(x -> x.equals(RoleType.ADMIN.name()))
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
                .filter(x -> x.equals(RoleType.ADMIN.name()))
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
}
