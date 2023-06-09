package ru.kpfu.hostel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ACCEPTED, reason = "Email already confirmed")
public class EmailAlreadyConfirmedException extends RuntimeException {
}
