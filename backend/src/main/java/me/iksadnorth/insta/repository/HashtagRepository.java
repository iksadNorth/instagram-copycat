package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.dto.HashtagDto;
import me.iksadnorth.insta.model.entity.Hashtag;
import me.iksadnorth.insta.model.entity.View;
import me.iksadnorth.insta.model.mappinginterface.HashtagNameMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Page<HashtagNameMapping> findDistinctNameBy(Pageable pageable);

    Long countByName(String tag);

    Long deleteByName(String tag);
}
