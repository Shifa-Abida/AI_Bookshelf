package com.Organise.AI_Bookshelf.service;

import com.Organise.AI_Bookshelf.dto.ToolRequestDto;
import com.Organise.AI_Bookshelf.dto.ToolResponseDto;
import com.Organise.AI_Bookshelf.entity.ToolEntity;
import com.Organise.AI_Bookshelf.exception.ToolNotFoundException;
import com.Organise.AI_Bookshelf.repository.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final ModelMapper modelMapper;


    // Save a new tool
    public ToolResponseDto saveTool(ToolEntity toolEntity) {
        return modelMapper.map(toolRepository.save(toolEntity), ToolResponseDto.class);
    }

    // Get all tools
    public List<ToolResponseDto> getAllTools() {
        return toolRepository.findAll().stream()
                .map(toolEntity -> modelMapper.map(toolEntity, ToolResponseDto.class))
                .toList();
    }

    // Get a tool by ID
    public Optional<ToolResponseDto> getToolById(Long id) {
        ToolEntity toolEntity = toolRepository.findById(id).orElseThrow(() -> new ToolNotFoundException("Tool not found with id: " + id));
        return Optional.of(modelMapper.map(toolEntity, ToolResponseDto.class));
    }

   // Create a new tool
    public ToolResponseDto createTool(ToolRequestDto toolDto) {
        ToolEntity toolEntity = modelMapper.map(toolDto, ToolEntity.class);
        ToolEntity savedToolEntity = toolRepository.save(toolEntity);
        return modelMapper.map(savedToolEntity, ToolResponseDto.class);
    }

    // Update an existing tool
    public ToolResponseDto updateTool(Long id, ToolRequestDto toolDto) {

        ToolEntity existingTool = toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));

        modelMapper.map(toolDto, existingTool);

        ToolEntity updatedTool = toolRepository.save(existingTool);

        return modelMapper.map(updatedTool, ToolResponseDto.class);
    }

    // Delete a tool by ID
    public void deleteTool(Long id) {
        ToolEntity existingTool = toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with id: " + id));

        toolRepository.delete(existingTool);
    }

}
