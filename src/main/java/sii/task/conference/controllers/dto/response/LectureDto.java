package sii.task.conference.controllers.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sii.task.conference.entities.TopicPath;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LectureDto {
    private LocalDateTime lectureStart;
    private LocalDateTime lectureEnd;
    private TopicPath topicPath;
}
