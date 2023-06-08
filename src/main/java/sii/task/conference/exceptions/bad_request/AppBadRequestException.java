package sii.task.conference.exceptions.bad_request;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Error")
public class AppBadRequestException extends Exception {
}
