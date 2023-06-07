package sii.task.conference.exceptions.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Lecture already booked")
public class LectureAlreadyBookedException extends Exception{
}
