package sii.task.conference.controllers.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sii.task.conference.entities.Lecture;
import sii.task.conference.entities.TopicPath;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatisticsPerLectureDto {

    private LocalDateTime lectureStart;
    private LocalDateTime lectureEnd;
    private Integer duration;
    private TopicPath topicPath;
    private Integer taken;
    private Integer totalSpots;
    private String percentageOfTakenSpots;

    public StatisticsPerLectureDto(Lecture lecture) {
        this.lectureStart = lecture.getLectureDate();
        this.lectureEnd = lecture.getLectureDate().plusMinutes(lecture.getDurationTime());
        this.taken = lecture.getParticipants().size();
        this.topicPath = lecture.getTopicPath();
        this.totalSpots = lecture.getLectureLimit();
        Double percentage = Double.valueOf(this.taken)/Double.valueOf(this.totalSpots) * 100d;
        this.percentageOfTakenSpots = percentage + "%";
        this.duration = lecture.getDurationTime();
    }
}
