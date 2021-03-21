package com.example.crud_example.entity.list_image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ListImage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer imageId;

    private Integer listId;

    private String imageName;

    public ListImage update(String imageName) {
        this.imageName = imageName;
        return this;
    }
}
