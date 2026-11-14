package com.Organise.AI_Bookshelf.repository;


import com.Organise.AI_Bookshelf.dto.ToolSummaryDto;
import com.Organise.AI_Bookshelf.entity.ToolEntity;
import com.Organise.AI_Bookshelf.projection.ToolSummaryProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

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

    @Query("""
    SELECT
        t.name AS name,
        t.websiteUrl AS websiteUrl,
        c.name AS categoryName
    FROM ToolEntity t
    JOIN t.category c
    
""")
    List<ToolSummaryProjection> findAllToolSummaries();

    @Query("""
    SELECT new com.Organise.AI_Bookshelf.dto.ToolSummaryDto(
        t.name,
        t.websiteUrl,
        c.name
    )
    FROM ToolEntity t
    JOIN t.category c
""")
    List<ToolSummaryDto> findAllToolSummariesUsingDto();

    <T> List<T> findByNameContainingIgnoreCase(
            String name,
            Class<T> type
    );

}
