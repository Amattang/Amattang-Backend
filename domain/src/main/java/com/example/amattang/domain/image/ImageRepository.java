package com.example.amattang.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByCheckListId_Id(Long id);

    Optional<Image> findByCheckListId_IdAndId(Long checkList, Long id);

    @Override
    List<Image> findAllById(Iterable<Long> longs);

}
