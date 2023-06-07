package sii.task.conference.exceptions.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You already have a lecture booked, during this time")
public class CollidingLecturesBookingException extends Exception {
}
