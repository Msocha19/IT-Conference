package sii.task.conference.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sii.task.conference.controllers.dto.BookLectureDto;
import sii.task.conference.services.ParticipantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/book-lecture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bookLecture(@Valid @RequestBody BookLectureDto bookLectureDto) throws Exception {
        participantService.bookLecture(bookLectureDto.getLogin(), bookLectureDto.getEmail(), bookLectureDto.getId());
    }
}
