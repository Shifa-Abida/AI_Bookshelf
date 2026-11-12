package com.Organise.AI_Bookshelf.repository;


import com.Organise.AI_Bookshelf.entity.ToolEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {

    boolean existsByName(String name);
    boolean existsByWebsiteUrl(String websiteUrl);


    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsByWebsiteUrlAndIdNot(String websiteUrl, Long id);

    Page<ToolEntity> findAll(Pageable pageable);

    Page<ToolEntity> findByCategory_Name(
            String categoryName,
            Pageable pageable
    );

    Page<ToolEntity> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
            String name,
            String description,
            String category,
            Pageable pageable
    );

}
