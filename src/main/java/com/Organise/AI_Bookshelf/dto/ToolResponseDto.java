package com.Organise.AI_Bookshelf.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolResponseDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String websiteUrl;
    private String pricing;

}
