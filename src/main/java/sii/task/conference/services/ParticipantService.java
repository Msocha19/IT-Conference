package sii.task.conference.services;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.OptimisticLockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sii.task.conference.entities.Lecture;
import sii.task.conference.entities.Participant;
import sii.task.conference.exceptions.conflict.AppOptimisticLockException;
import sii.task.conference.exceptions.conflict.CollidingLecturesBookingException;
import sii.task.conference.exceptions.conflict.FullLectureException;
import sii.task.conference.exceptions.conflict.LectureAlreadyBookedException;
import sii.task.conference.exceptions.not_found.LectureNotFoundException;
import sii.task.conference.exceptions.conflict.LoginTakenException;
import sii.task.conference.repositories.LectureRepository;
import sii.task.conference.repositories.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public void bookLecture(String login, String email, Long lectureId) throws Exception {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
        if (lectureRepository.findLectureByParticipantsLoginAndDate(
            login,
            lecture.getLectureDate(),
            lecture.getLectureDate()
                .plusMinutes(
                    lecture.getDurationTime())).size() > 0) {
            throw new CollidingLecturesBookingException();
        }
        if (lecture.getParticipants().size() == 5) {
            throw new FullLectureException();
        }
        Participant participant = participantRepository.findByLogin(login).orElse(new Participant(login, email));
        if (lecture.getParticipants().contains(participant)) {
            throw new LectureAlreadyBookedException();
        }
        if (!Objects.equals(participant.getEmail(), email)) {
            throw new LoginTakenException();
        }
        lecture.getParticipants().add(participant);
        try {
            lectureRepository.save(lecture);
        } catch (OptimisticLockException ole) {
            throw new AppOptimisticLockException();
        }

        sendMail(email, "Lecture has been booked.");
    }

    @Async
    void sendMail(String email, String message) throws FileNotFoundException {
        try (FileWriter myWriter = new FileWriter("notifications.txt", true)) {
            myWriter.write("Date: " + LocalDateTime.now() + " To: " + email + " Content: " + message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
