package com.Organise.AI_Bookshelf.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_category_name",
                        columnNames = "name"
                )
        }
)
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<ToolEntity> tools = new ArrayList<>();

}