package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Hashtag;
import me.iksadnorth.insta.model.mappinginterface.HashtagNameMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Page<HashtagNameMapping> findDistinctNameBy(Pageable pageable);
    Page<HashtagNameMapping> findDistinctNameByNameContainingIgnoreCase(String name, Pageable pageable);

    Long countByName(String tag);

    void deleteByName(String tag);
}
