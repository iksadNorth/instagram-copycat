package me.iksadnorth.insta.repository;

import me.iksadnorth.insta.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
