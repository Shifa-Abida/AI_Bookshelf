package com.Organise.AI_Bookshelf.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ToolRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100,
            message = "Tool name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500,
            message = "Description must be between 10 and 500 characters")
    private String description;


    @NotBlank(message = "Category is required")
    @Size(max = 50,
            message = "Category cannot exceed 50 characters")
    private String category;

    @NotBlank(message = "Website URL is required")
    @Pattern(
            regexp = "^https?://.+$",
            message = "Please provide a valid HTTP or HTTPS website URL"
    )
    private String websiteUrl;

    @Size(max = 30,
            message = "Pricing cannot exceed 30 characters")
    private String pricing;

}
