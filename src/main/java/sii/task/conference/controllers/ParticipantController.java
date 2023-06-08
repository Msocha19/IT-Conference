package sii.task.conference.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sii.task.conference.controllers.dto.request.BookLectureDto;
import sii.task.conference.controllers.dto.response.ParticipantDto;
import sii.task.conference.services.ParticipantService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @PostMapping("/book-lecture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bookLecture(@Valid @RequestBody BookLectureDto bookLectureDto) throws Exception {
        participantService.bookLecture(bookLectureDto.getLogin(), bookLectureDto.getEmail(), bookLectureDto.getId());
    }

    @DeleteMapping("/{login}/cancel-lecture")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelLecture(@PathVariable("login") @NotBlank String login,
                              @PathParam("lectureId") @NotNull Long lectureId) throws Exception {
        participantService.cancelBooked(login, lectureId);
    }

    @PutMapping("/{login}/update-email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEmail(@PathVariable("login") @NotBlank String login,
                            @PathParam("newEmail") @NotBlank @Email String newEmail) throws Exception {
        participantService.updateUserEmail(login, newEmail);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipantDto> getParticipants() {
        return participantService
            .getAllParticipants()
            .stream()
            .map(
                (participant -> new ParticipantDto(
                    participant.getId(),
                    participant.getLogin(),
                    participant.getEmail()))).toList();
    }
}
