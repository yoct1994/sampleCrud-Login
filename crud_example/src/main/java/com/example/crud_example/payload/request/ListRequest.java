package com.example.crud_example.payload.request;

import com.example.crud_example.entity.list.enums.ListType;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ListRequest {
    private String title;
    private String content;
    private MultipartFile image;
}
