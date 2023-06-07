package sii.task.conference.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookLectureDto {

    @NotNull
    @Positive
    private Long id;

    @NotBlank
    private String login;

    @NotBlank
    private String email;
}
