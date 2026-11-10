package com.Organise.AI_Bookshelf.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "tools",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_tool_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_tool_website_url", columnNames = "website_url")
        },


        indexes = {
                @Index(
                        name = "idx_tool_category",
                        columnList = "category"
                )
        }

)

@Data
public class ToolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;

    @Column(name = "website_url")
    private String websiteUrl;
    private String pricing;

    @Column(name = "logo_url")
    private String logoUrl;

}