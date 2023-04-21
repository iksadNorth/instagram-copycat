package me.iksadnorth.insta.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecSystem {
    List<Long> recommend(Long id, Pageable pageable);
}
