package ru.javacode.student.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BookDtoUpdate {

    @NotNull
    private Long id;

    private String title;

    private Set<Long> authorsIds;

}
