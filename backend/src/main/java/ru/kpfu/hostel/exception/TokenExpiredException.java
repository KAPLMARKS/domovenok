package ru.kpfu.hostel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Token expired")
public class TokenExpiredException extends RuntimeException {
}
