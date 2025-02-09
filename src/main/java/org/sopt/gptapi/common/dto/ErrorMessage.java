package org.sopt.gptapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS.value(), "Requests are too frequent. Please try again later."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST.value(),"Bad request. Please check the endpoint."),
    GENERAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred. Please try again.")

    ;
    private final int status;

    private final String message;
}
