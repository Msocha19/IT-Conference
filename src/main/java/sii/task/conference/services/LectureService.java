package sii.task.conference.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.task.conference.entities.Lecture;
import sii.task.conference.exceptions.not_found.LectureNotFoundException;
import sii.task.conference.repositories.LectureRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LectureService {

    private LectureRepository lectureRepository;

    public List<Lecture> getLectures() {
        return lectureRepository.findAll();
    }

    public List<Lecture> getParticipantLectures(String login) throws LectureNotFoundException {
        return lectureRepository.findLectureByParticipantsLogin(login);
    }
}
