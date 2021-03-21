package com.example.crud_example.payload.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ListResponse {
    private Integer listId;
    private String name;
    private String title;
    private String content;
    private LocalDate writeAt;
    private String imageName;
}
