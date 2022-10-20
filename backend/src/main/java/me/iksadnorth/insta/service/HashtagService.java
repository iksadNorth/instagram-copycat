package me.iksadnorth.insta.service;

import me.iksadnorth.insta.exception.ErrorCode;
import me.iksadnorth.insta.exception.InstaApplicationException;
import me.iksadnorth.insta.model.dto.ArticleDto;
import me.iksadnorth.insta.model.dto.HashtagDto;
import me.iksadnorth.insta.repository.ArticleRepository;
import me.iksadnorth.insta.repository.HashtagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HashtagService {
    @Autowired HashtagRepository repo;
    @Autowired ArticleRepository articleRepo;

    public Page<HashtagDto> hashtagRead(Pageable pageable) {
        return repo.findDistinctNameBy(pageable).map(HashtagDto::fromNameMapping);
    }

    public Page<HashtagDto> hashtagReadByName(String name, Pageable pageable) {
        return repo.findDistinctNameByNameContainingIgnoreCase(name, pageable).map(HashtagDto::fromNameMapping);
    }

    public Page<ArticleDto> hashtagReadAbout(String tag, Pageable pageable) {
        return articleRepo.findByHashtags_Name(tag, pageable).map(ArticleDto::fromEntity);
    }

    public void hashtagDelete(String tag) {
        if(repo.countByName(tag) > 0) {
            repo.deleteByName(tag);
        } else {
            throw new InstaApplicationException(
                    ErrorCode.TAG_NOT_FOUNDED, String.format("해당 tag의 이름: %s", tag)
            );
        }
    }
}
