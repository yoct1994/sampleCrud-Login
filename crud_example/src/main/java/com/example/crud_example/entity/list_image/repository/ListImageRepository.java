package com.example.crud_example.entity.list_image.repository;

import com.example.crud_example.entity.list_image.ListImage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListImageRepository extends CrudRepository<ListImage, Integer> {
    Optional<ListImage> findByImageName(String imageName);
    Optional<ListImage> findByListId(Integer listId);
    void deleteByListId(Integer listId);
}
