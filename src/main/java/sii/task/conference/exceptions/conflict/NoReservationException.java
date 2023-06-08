package sii.task.conference.exceptions.conflict;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You have no current bookings for chosen lecture")
public class NoReservationException extends Exception {
}
