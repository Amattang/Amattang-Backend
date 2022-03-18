package com.example.amattang.domain.customCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomCategoryRepository extends JpaRepository<CustomCategory, Long> {

    Optional<CustomCategory> findByIdAndCheckListId_Id(Long id, Long checkListId);
}
