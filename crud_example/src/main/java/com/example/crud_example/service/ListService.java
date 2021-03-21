package com.example.crud_example.service;

import com.example.crud_example.entity.list.enums.ListType;
import com.example.crud_example.payload.request.ListRequest;
import com.example.crud_example.payload.request.ListUpdateRequest;
import com.example.crud_example.payload.response.ListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ListService {
    void write(ListRequest listRequest, String token);
    void updateList(Integer listId, ListUpdateRequest listUpdateRequest, String token);
    void deleteList(Integer listId, String token);
    List<ListResponse> getList(ListType listType, String token);
    byte[] getImage(String imageName);
    void listImageUpdate(String imageName, MultipartFile listImage, String token);
    void listImageDelete(String imageName, String token);
}
