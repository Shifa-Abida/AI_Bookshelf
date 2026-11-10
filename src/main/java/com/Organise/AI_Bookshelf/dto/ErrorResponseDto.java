package com.Organise.AI_Bookshelf.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponseDto {
    private Object message;
    private int status;
    private LocalDateTime timestamp;
    private String error;

}
