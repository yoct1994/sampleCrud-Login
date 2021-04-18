package com.example.crud_example.service;

import com.example.crud_example.entity.list.List;
import com.example.crud_example.entity.list.enums.ListType;
import com.example.crud_example.entity.list.repository.ListRepository;
import com.example.crud_example.entity.list_image.ListImage;
import com.example.crud_example.entity.list_image.repository.ListImageRepository;
import com.example.crud_example.entity.user.User;
import com.example.crud_example.entity.user.repository.UserRepository;
import com.example.crud_example.error.exceptions.ListImageNotFoundException;
import com.example.crud_example.error.exceptions.ListNotFoundException;
import com.example.crud_example.error.exceptions.UserNotFoundException;
import com.example.crud_example.jwts.JwtProvider;
import com.example.crud_example.payload.request.ListRequest;
import com.example.crud_example.payload.request.ListUpdateRequest;
import com.example.crud_example.payload.response.ListResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ListServiceImpl implements ListService{

    private final UserRepository userRepository;
    private final ListImageRepository listImageRepository;
    private final ListRepository listRepository;

    private final JwtProvider jwtProvider;

    @Value("${image.upload.dir}")
    String imageDir;

    private <T> void setIfNotNull(Consumer<T> setter, T value) {
        if(value != null) {
            setter.accept(value);
        }
    }

    @Override
    public java.util.List<ListResponse> getList(ListType listType, String token) {
        userRepository.findByUserId(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        java.util.List<ListResponse> response = new ArrayList<>();
        java.util.List<List> allOfList = listRepository.findAllByListType(listType);

        if(allOfList.isEmpty())
            throw new ListNotFoundException();

        for(List list : allOfList) {
            ListImage listImage = listImageRepository.findByListId(list.getListId())
                    .orElseThrow(ListImageNotFoundException::new);

            User user = userRepository.findByUserId(list.getUserId())
                    .orElseThrow(UserNotFoundException::new);

            ListResponse listResponse = ListResponse.builder()
                    .name(user.getName())
                    .listId(list.getListId())
                    .title(list.getTitle())
                    .content(list.getContent())
                    .imageName(listImage.getImageName())
                    .writeAt(list.getWriteAt())
                    .build();

            response.add(listResponse);
        }

        return response;
    }

    @SneakyThrows
    @Override
    public byte[] getImage(String imageName) {
        listImageRepository.findByImageName(imageName)
                .orElseThrow(ListImageNotFoundException::new);

        File file = new File(imageDir, imageName);
        if (!file.exists()) throw new ListImageNotFoundException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

    @Override
    public void updateList(Integer listId, ListUpdateRequest listUpdateRequest, String token) {
        userRepository.findByUserId(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        List list = listRepository.findByListId(listId)
                .orElseThrow(ListNotFoundException::new);

        setIfNotNull(list::setTitle, listUpdateRequest.getTitle());
        setIfNotNull(list::setContent, listUpdateRequest.getContent());

        listRepository.save(list);
    }

    @SneakyThrows
    @Override
    public void write(ListRequest listRequest, String token) {
        User user = userRepository.findByUserId(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        List list = List.builder()
                .listType(ListType.PUBLIC)
                .title(listRequest.getTitle())
                .content(listRequest.getContent())
                .writeAt(LocalDate.now())
                .userId(user.getUserId())
                .build();

        listRepository.save(list);

        if(listRequest.getImage().isEmpty()) {
            ListImage listImage = ListImage.builder()
                    .listId(list.getListId())
                    .imageName("null")
                    .build();

            listImageRepository.save(listImage);
        }else {
            String imageName = UUID.randomUUID().toString();

            ListImage listImage = ListImage.builder()
                    .listId(list.getListId())
                    .imageName(imageName)
                    .build();

            listRequest.getImage().transferTo(new File(imageDir, imageName));

            listImageRepository.save(listImage);
        }
    }

    @SneakyThrows
    @Override
    public void listImageUpdate(String imageName, MultipartFile listImage, String token) {
        userRepository.findById(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        ListImage image = listImageRepository.findByImageName(imageName)
                .orElseThrow(ListImageNotFoundException::new);

        File imageFile = new File(imageDir, image.getImageName());

        if(!imageFile.exists()) imageFile.delete();

        if(!listImage.isEmpty()) {
            String newImageName = UUID.randomUUID().toString();

            image.update(newImageName);

            listImage.transferTo(new File(imageDir, newImageName));
        }else {
            image.update("null");
        }

        listImageRepository.save(image);
    }

    @SneakyThrows
    @Override
    @Transactional
    public void deleteList(Integer listId, String token) {
        userRepository.findByUserId(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        List list = listRepository.findByListId(listId)
                .orElseThrow(ListNotFoundException::new);

        ListImage listImage = listImageRepository.findByListId(list.getListId())
                .orElseThrow(ListImageNotFoundException::new);

        if (!listImage.getImageName().equals("null") || !listImage.getImageName().equals("deleted")) {
            File file = new File(imageDir, listImage.getImageName());
            file.delete();
        }

        listRepository.deleteByListId(list.getListId());
        listRepository.deleteByListId(list.getListId());
    }

    @SneakyThrows
    @Override
    @Transactional
    public void listImageDelete(String imageName, String token) {
        userRepository.findByUserId(Integer.parseInt(jwtProvider.getUserId(token)))
                .orElseThrow(UserNotFoundException::new);

        ListImage listImage = listImageRepository.findByImageName(imageName)
                .orElseThrow(ListImageNotFoundException::new);

        File file = new File(imageDir, listImage.getImageName());
        if(!file.exists()) file.delete();

        listImage.update("deleted");
        listImageRepository.save(listImage);
    }
}
