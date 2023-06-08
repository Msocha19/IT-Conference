package sii.task.conference.exceptions.not_found;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Participant not found")
public class ParticipantNotFoundException extends Exception {
}
