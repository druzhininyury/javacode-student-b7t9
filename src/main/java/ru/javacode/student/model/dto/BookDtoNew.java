package ru.javacode.student.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDtoNew {

    private String title;

    private Set<Long> authorsIds;

}
