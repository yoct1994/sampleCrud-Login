package com.example.crud_example.controller;

import com.example.crud_example.entity.list.enums.ListType;
import com.example.crud_example.payload.request.ListRequest;
import com.example.crud_example.payload.request.ListUpdateRequest;
import com.example.crud_example.payload.response.ListResponse;
import com.example.crud_example.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/list")
public class ListController {

    private final ListService listService;

    @GetMapping()
    public List<ListResponse> getList(@RequestHeader("Authorization") String token) {
        return listService.getList(ListType.PUBLIC, token);
    }

    @GetMapping(value = "/image/{imageName}",
                produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public byte[] getImage(@PathVariable String imageName) {
        return listService.getImage(imageName);
    }

    @PostMapping
    public void write(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam MultipartFile image,
            @RequestHeader("Authorization") String token) {

        ListRequest listRequest = ListRequest.builder()
                .title(title)
                .content(content)
                .image(image)
                .build();

        listService.write(listRequest, token);
    }

    @PutMapping("/{listId}")
    public void updateList(@RequestBody ListUpdateRequest listUpdateRequest,
                           @PathVariable Integer listId,
                           @RequestHeader("Authorization") String token) {
        listService.updateList(listId, listUpdateRequest, token);
    }

    @PutMapping("/image/{imageName}")
    public void updateImage(@RequestHeader("Authorization") String token,
                            @RequestParam MultipartFile image,
                            @PathVariable String imageName) {
        listService.listImageUpdate(imageName, image, token);
    }

    @DeleteMapping("/{listId}")
    public void deleteList(@PathVariable Integer listId,
                           @RequestHeader("Authorization") String token) {
        listService.deleteList(listId, token);
    }

    @DeleteMapping("/image/{imageName}")
    public void deleteListImage(@PathVariable String imageName,
                                @RequestHeader("Authorization") String token) {
        listService.listImageDelete(imageName, token);
    }
}
