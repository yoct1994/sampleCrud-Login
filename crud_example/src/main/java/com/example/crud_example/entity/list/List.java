package com.example.crud_example.entity.list;

import com.example.crud_example.entity.list.enums.ListType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer listId;

    private Integer userId;

    private String title;

    private String content;

    private LocalDate writeAt;

    private ListType listType;
}
