package com.Organise.AI_Bookshelf.controller;

import com.Organise.AI_Bookshelf.dto.ToolRequestDto;
import com.Organise.AI_Bookshelf.dto.ToolResponseDto;
import com.Organise.AI_Bookshelf.dto.ToolSummaryDto;
import com.Organise.AI_Bookshelf.exception.ToolNotFoundException;
import com.Organise.AI_Bookshelf.projection.ToolSummaryProjection;
import com.Organise.AI_Bookshelf.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    // Search tools by keyword
    @GetMapping("/api/tools/search")
    public ResponseEntity<?> searchTools(
            @RequestParam String keyword,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            Pageable pageable) {

        if (keyword.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Search keyword cannot be blank.");
        }

        if (page != null && page < 0) {
            return ResponseEntity.badRequest()
                    .body("Page number cannot be negative.");
        }

        if (size != null && size <= 0) {
            return ResponseEntity.badRequest()
                    .body("Page size must be greater than zero.");
        }

        return ResponseEntity.ok(
                toolService.searchTools(keyword.trim(), pageable)
        );
    }



    //crate a new tool
    @PostMapping("/api/tools")
    public ToolResponseDto createTool(@Valid @RequestBody ToolRequestDto toolDto) {
        return toolService.createTool(toolDto);
    }

    // Get lightweight tool summaries using projection
    @GetMapping("/api/tools/summaries")
    public ResponseEntity<List<ToolSummaryProjection>> getAllToolSummaries() {

        return ResponseEntity.ok(
                toolService.getAllToolSummaries()
        );
    }

    // Get tool summaries using DTO projection
    @GetMapping("/api/tools/summaries/dto")
    public ResponseEntity<List<ToolSummaryDto>> getAllToolSummariesUsingDto() {

        return ResponseEntity.ok(
                toolService.getAllToolSummariesUsingDto()
        );
    }

    @GetMapping("/api/tools/dynamic-summary")
    public ResponseEntity<List<ToolSummaryDto>> searchDynamicSummary(
            @RequestParam String keyword
    ) {
        return ResponseEntity.ok(
                toolService.searchToolSummaries(
                        keyword,
                        ToolSummaryDto.class
                )
        );
    }

    //get all tools
    @GetMapping("/api/tools")
    public ResponseEntity<?> getAllTools(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String category,
            Pageable pageable) {

        if (page != null && page < 0) {
            return ResponseEntity.badRequest()
                    .body("Page number cannot be negative.");
        }

        if (size != null && size <= 0) {
            return ResponseEntity.badRequest()
                    .body("Page size must be greater than zero.");
        }

        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(
                    toolService.searchTools(category, pageable));
        }

        return ResponseEntity.ok(toolService.getAllTools(pageable));
    }

    //get a tool by id
    @GetMapping("/api/tools/{id}")
    public ResponseEntity<ToolResponseDto> getToolById(@PathVariable Long id) {
        return toolService.getToolById(id);
    }

    //update a tool by id
    @PutMapping("/api/tools/{id}")
    public ToolResponseDto updateTool(@PathVariable Long id, @RequestBody ToolRequestDto toolDto) {
        return toolService.updateTool(id, toolDto);
    }

    //delete a tool by id
    @DeleteMapping("/api/tools/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.ok("Tool deleted successfully");
    }
}
