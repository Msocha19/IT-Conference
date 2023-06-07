package sii.task.conference.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sii.task.conference.entities.Lecture;
import sii.task.conference.entities.TopicPath;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query(
        value = "SELECT l FROM Lecture l JOIN l.participants p WHERE p.login = ?1")
    List<Lecture> findLectureByParticipantsLogin(String login);

    @Query(
        value = "SELECT l FROM Lecture l JOIN l.participants p WHERE p.login = ?1 AND l.lectureDate >= ?2 AND l.lectureDate <= ?3")
    List<Lecture> findLectureByParticipantsLoginAndDate(String login, LocalDateTime start, LocalDateTime end);

    List<Lecture> findLectureByTopicPath(TopicPath topicPath);
}
