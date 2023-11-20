package br.com.blog.authentication.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class ExHandler {


    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity handleDuplicateError(UnexpectedRollbackException exception){
        return ResponseEntity.badRequest().body("Error: your email or password might be incorrect" );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleDataIntegrity(DataIntegrityViolationException exception){
        log.error("Tried to create or change an user with incorrect data");
        return ResponseEntity.badRequest().body("Error: your email or password might be incorrect ");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleEE(ValidationException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleSQLError(SQLException exception){
        return ResponseEntity.badRequest().body("This email is already used"  + exception);
    }

}
