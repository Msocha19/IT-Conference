package sii.task.conference.exceptions.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Full lecture")
public class FullLectureException extends Exception {
}
