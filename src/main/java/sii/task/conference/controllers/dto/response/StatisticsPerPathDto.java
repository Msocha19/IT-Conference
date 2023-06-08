package sii.task.conference.controllers.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sii.task.conference.entities.Lecture;
import sii.task.conference.entities.TopicPath;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPerPathDto {
    private TopicPath topicPath;
    private Integer takenSpots = 0;
    private Integer availableSpots = 0;
    private String takenPercentage;

    public StatisticsPerPathDto(List<Lecture> lectures, TopicPath topic) {
        this.topicPath = topic;
        lectures.forEach(lecture -> {
            this.takenSpots += lecture.getParticipants().size();
            this.availableSpots += lecture.getLectureLimit();
        });
        Double percentage = Double.valueOf(takenSpots)/Double.valueOf(availableSpots) * 100d;
        this.takenPercentage = percentage + "%";
    }
}
