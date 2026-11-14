package com.Organise.AI_Bookshelf.service;

import com.Organise.AI_Bookshelf.dto.ToolRequestDto;
import com.Organise.AI_Bookshelf.dto.ToolResponseDto;
import com.Organise.AI_Bookshelf.dto.ToolSummaryDto;
import com.Organise.AI_Bookshelf.entity.CategoryEntity;
import com.Organise.AI_Bookshelf.entity.ToolEntity;
import com.Organise.AI_Bookshelf.exception.ToolAlreadyExistsException;
import com.Organise.AI_Bookshelf.exception.ToolNotFoundException;
import com.Organise.AI_Bookshelf.projection.ToolSummaryProjection;
import com.Organise.AI_Bookshelf.repository.ToolRepository;
import com.Organise.AI_Bookshelf.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    private CategoryEntity getOrCreateCategory(String categoryName) {

        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    CategoryEntity newCategory = new CategoryEntity();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
    }

    private ToolResponseDto mapToResponse(ToolEntity toolEntity) {

        ToolResponseDto response = new ToolResponseDto();

        response.setId(toolEntity.getId());
        response.setName(toolEntity.getName());
        response.setDescription(toolEntity.getDescription());
        response.setCategory(toolEntity.getCategory().getName());
        response.setWebsiteUrl(toolEntity.getWebsiteUrl());
        response.setPricing(toolEntity.getPricing());

        return response;
    }


    // Save a new tool
    public ToolResponseDto saveTool(ToolEntity toolEntity) {
        return mapToResponse(toolRepository.save(toolEntity));
    }

    // Get all tools
    public Page<ToolResponseDto> getAllTools(Pageable pageable) {
        Page<ToolEntity> toolEntities = toolRepository.findAll(pageable);
        return toolEntities.map(this::mapToResponse);

    }

    // Get lightweight tool summaries using interface projection
    public List<ToolSummaryProjection> getAllToolSummaries() {

        return toolRepository.findAllToolSummaries();

    }

    // Get tool summaries using DTO projection
    public List<ToolSummaryDto> getAllToolSummariesUsingDto() {

        return toolRepository.findAllToolSummariesUsingDto();

    }

    // Search tools by name using dynamic projection
    public <T> List<T> searchToolSummaries(String keyword, Class<T> projectionType) {
        return toolRepository.findByNameContainingIgnoreCase(
                keyword,
                projectionType
        );
    }


    // Search tools by name, description, or category
    public Page<ToolResponseDto> searchTools(
            String keyword, Pageable pageable) {

        Page<ToolEntity> toolPage =
                toolRepository
                        .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategory_NameContainingIgnoreCase(
                                keyword,
                                keyword,
                                keyword,
                                pageable
                        );

        return toolPage.map(this::mapToResponse);
    }


    // Get a tool by ID
    public ResponseEntity<ToolResponseDto> getToolById(Long id) {
        ToolEntity toolEntity = toolRepository.findById(id).orElseThrow(() -> new ToolNotFoundException("Tool not found with id: " + id));
        return ResponseEntity.ok(mapToResponse(toolEntity));
    }

   // Create a new tool
    public ToolResponseDto createTool(ToolRequestDto toolDto) {

        if (toolRepository.existsByName(toolDto.getName())) {
            throw new ToolAlreadyExistsException(
                    "Tool with this name already exists."
            );
        }

        // Check duplicate website URL
        if (toolRepository.existsByWebsiteUrl(toolDto.getWebsiteUrl())) {
            throw new ToolAlreadyExistsException(
                    "Tool with this website URL already exists."
            );
        }

        ToolEntity toolEntity = new ToolEntity();

        toolEntity.setName(toolDto.getName());
        toolEntity.setDescription(toolDto.getDescription());
        toolEntity.setWebsiteUrl(toolDto.getWebsiteUrl());
        toolEntity.setPricing(toolDto.getPricing());

        CategoryEntity categoryEntity = getOrCreateCategory(toolDto.getCategory());
        toolEntity.setCategory(categoryEntity);
        ToolEntity savedToolEntity = toolRepository.save(toolEntity);

        return mapToResponse(savedToolEntity);
    }

    // Update an existing tool
    public ToolResponseDto updateTool(Long id, ToolRequestDto toolDto) {

        ToolEntity existingTool = toolRepository.findById(id)
                .orElseThrow(() -> new ToolNotFoundException("Tool not found with id: " + id));

        // Check duplicate tool name
        if (toolRepository.existsByNameAndIdNot(toolDto.getName(), id)) {
            throw new ToolAlreadyExistsException(
                    "Another tool with this name already exists.");
        }

        // Check duplicate website URL
        if (toolRepository.existsByWebsiteUrlAndIdNot(
                toolDto.getWebsiteUrl(), id)) {
            throw new ToolAlreadyExistsException(
                    "Another tool with this website URL already exists.");
        }

        existingTool.setName(toolDto.getName());
        existingTool.setDescription(toolDto.getDescription());
        existingTool.setWebsiteUrl(toolDto.getWebsiteUrl());
        existingTool.setPricing(toolDto.getPricing());

        CategoryEntity categoryEntity = getOrCreateCategory(toolDto.getCategory());
        existingTool.setCategory(categoryEntity);

        ToolEntity updatedTool = toolRepository.save(existingTool);

        return mapToResponse(updatedTool);
    }

    // Delete a tool by ID
    public void deleteTool(Long id) {
        ToolEntity existingTool = toolRepository.findById(id)
                .orElseThrow(() -> new ToolNotFoundException("Tool not found with id: " + id));

        toolRepository.delete(existingTool);
    }

}
