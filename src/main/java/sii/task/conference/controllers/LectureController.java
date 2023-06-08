package sii.task.conference.controllers;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sii.task.conference.controllers.dto.response.LectureDto;
import sii.task.conference.controllers.dto.response.StatisticsPerLectureDto;
import sii.task.conference.services.LectureService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
public class LectureController {

    private final LectureService lectureService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LectureDto> getConferenceSchedule() {
        return lectureService.getAllLectures()
            .stream().map(LectureDto::new).toList();
    }

    @GetMapping("/participant/{login}")
    @ResponseStatus(HttpStatus.OK)
    public List<LectureDto> getParticipantsLectures(@PathVariable("login") @NotBlank String login) {
        return lectureService.getParticipantsLectures(login)
            .stream().map(LectureDto::new).toList();
    }

    @GetMapping("/statistics")
    @ResponseStatus(HttpStatus.OK)
    public List<StatisticsPerLectureDto> getLectureStatistics() {
        return lectureService.getAllLectures()
            .stream().map(StatisticsPerLectureDto::new).toList();
    }
}
