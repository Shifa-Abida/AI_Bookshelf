package com.Organise.AI_Bookshelf.repository;


import com.Organise.AI_Bookshelf.entity.ToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolRepository extends JpaRepository<ToolEntity, Long> {

    boolean existsByName(String name);

    boolean existsByWebsiteUrl(String websiteUrl);

}
