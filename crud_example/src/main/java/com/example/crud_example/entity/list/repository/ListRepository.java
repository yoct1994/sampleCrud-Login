package com.example.crud_example.entity.list.repository;

import com.example.crud_example.entity.list.List;
import com.example.crud_example.entity.list.enums.ListType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Integer> {
    java.util.List<List> findAllByListType(ListType listType);
    Optional<List> findByListId(Integer listId);
    void deleteByListId(Integer listId);
}
