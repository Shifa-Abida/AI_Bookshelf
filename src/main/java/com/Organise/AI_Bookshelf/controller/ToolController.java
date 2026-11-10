package com.Organise.AI_Bookshelf.controller;

import com.Organise.AI_Bookshelf.dto.ToolRequestDto;
import com.Organise.AI_Bookshelf.dto.ToolResponseDto;
import com.Organise.AI_Bookshelf.exception.ToolNotFoundException;
import com.Organise.AI_Bookshelf.service.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ToolController {

    private final ToolService toolService;

    @ExceptionHandler(ToolNotFoundException.class)
    public ResponseEntity<String> handleToolNotFound(ToolNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    //crate a new tool
    @PostMapping("/api/tools")
    public ToolResponseDto createTool(@Valid @RequestBody ToolRequestDto toolDto) {
        return toolService.createTool(toolDto);
    }

    //get all tools
    @GetMapping("/api/tools")
    public List<ToolResponseDto> getAllTools() {
        return toolService.getAllTools();
    }

    //get a tool by id
    @GetMapping("/api/tools/{id}")
    public Optional<ToolResponseDto> getToolById(@PathVariable Long id) {
        return toolService.getToolById(id);
    }

    //update a tool by id
    @PutMapping("/api/tools/{id}")
    public ToolResponseDto updateTool(@PathVariable Long id, @RequestBody ToolRequestDto toolDto) {
        return toolService.updateTool(id, toolDto);
    }

    //delete a tool by id
    @DeleteMapping("/api/tools/{id}")
    public void deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
    }
}
