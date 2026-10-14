package ru.nsu.mkorovkina.rubics_cube.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class Handler {
    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatus(ResponseStatusException e) {
        ProblemDetail problem = ProblemDetail.forStatus(e.getStatusCode());
        problem.setDetail(e.getReason());
        return problem;
    }
}
