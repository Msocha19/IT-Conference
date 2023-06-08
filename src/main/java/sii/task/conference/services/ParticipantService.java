package sii.task.conference.services;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.script.ScriptException;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sii.task.conference.entities.Lecture;
import sii.task.conference.entities.Participant;
import sii.task.conference.exceptions.bad_request.AppBadRequestException;
import sii.task.conference.exceptions.conflict.AppOptimisticLockException;
import sii.task.conference.exceptions.conflict.CollidingLecturesBookingException;
import sii.task.conference.exceptions.conflict.EmailInUseException;
import sii.task.conference.exceptions.conflict.FullLectureException;
import sii.task.conference.exceptions.conflict.LectureAlreadyBookedException;
import sii.task.conference.exceptions.conflict.NoReservationException;
import sii.task.conference.exceptions.not_found.LectureNotFoundException;
import sii.task.conference.exceptions.conflict.LoginTakenException;
import sii.task.conference.exceptions.not_found.ParticipantNotFoundException;
import sii.task.conference.repositories.LectureRepository;
import sii.task.conference.repositories.ParticipantRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {

    private final LectureRepository lectureRepository;

    private final ParticipantRepository participantRepository;

    public void bookLecture(String login, String email, Long lectureId) throws Exception {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
        Participant participant = participantRepository.findByLogin(login).orElse(new Participant(login, email));
        if (!participant.getEmail().equals(email)) {
            throw new LoginTakenException();
        }
        if (lecture.getParticipants().contains(participant)) {
            throw new LectureAlreadyBookedException();
        }
        if (lecture.getParticipants().size() == 5) {
            throw new FullLectureException();
        }
        if (lectureRepository.findLectureByParticipantsLoginAndDate(
            login,
            lecture.getLectureDate(),
            lecture.getLectureDate()
                .plusMinutes(
                    lecture.getDurationTime())).size() > 0) {
            throw new CollidingLecturesBookingException();
        }
        lecture.getParticipants().add(participant);
        try {
            lectureRepository.save(lecture);
        } catch (OptimisticLockException ole) {
            throw new AppOptimisticLockException();
        } catch (Exception se) {
            if (se.getCause().getMessage().contains("participant_email_key")) {
                throw new EmailInUseException();
            }
            if (se.getCause().getMessage().contains("participant_login_key")) {
                throw new LoginTakenException();
            }
            throw new AppBadRequestException();
        }

        sendMail(email, "Lecture has been booked.");
    }

    public void cancelBooked(String login, Long lectureId) throws Exception {
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
        List<Participant> participants = lecture.getParticipants().stream().filter((p) -> p.getLogin().equals(login)).toList();
        if (participants.size() == 0) {
            throw new NoReservationException();
        }
        Participant participant = participants.get(0);
        try {
            lecture.getParticipants().remove(participant);
            lectureRepository.save(lecture);
        } catch (OptimisticLockException ole) {
            throw new AppOptimisticLockException();
        }
        sendMail(participant.getEmail(), "Your booking has been canceled");
    }

    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    public void updateUserEmail(String login, String email) throws Exception {
        Participant participant = participantRepository.findByLogin(login).orElseThrow(ParticipantNotFoundException::new);
        participant.setEmail(email);
        try {
            participantRepository.save(participant);
        } catch (OptimisticLockException e) {
            throw new AppOptimisticLockException();
        } catch (Exception e) {
            if (e.getCause().getMessage().contains("participant_email_key")) {
                throw new EmailInUseException();
            }
            throw new AppBadRequestException();
        }
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
