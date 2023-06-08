package sii.task.conference.services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.task.conference.entities.Lecture;
import sii.task.conference.repositories.LectureRepository;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public List<Lecture> getParticipantsLectures(String login) {
        return lectureRepository.findLectureByParticipantsLogin(login);
    }
}
