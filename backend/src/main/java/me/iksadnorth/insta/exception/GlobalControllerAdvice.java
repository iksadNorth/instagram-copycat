package me.iksadnorth.insta.exception;

import lombok.extern.slf4j.Slf4j;
import me.iksadnorth.insta.model.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InstaApplicationException.class)
    public ResponseEntity<?> errorHandler(InstaApplicationException e) {
        log.error("Error occurs {}", e.toString());
        return ResponseEntity.status(e.getErrorCode().getStatus())
                .body(Response.error(e.getErrorCode()));
    }
}
