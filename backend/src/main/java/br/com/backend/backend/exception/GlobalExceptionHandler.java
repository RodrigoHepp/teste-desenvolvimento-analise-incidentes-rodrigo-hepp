package br.com.backend.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<StandardErrorResponse> handleRecursoNaoEncontrado(
            RecursoNaoEncontradoException ex,
            WebRequest request) {

        logger.warn("Recurso não encontrado: {}", ex.getMessage());

        StandardErrorResponse errorResponse = new StandardErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        logger.warn("Erro de validação: {}", ex.getMessage());

        List<String> erros = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                erros.add(error.getField() + ": " + error.getDefaultMessage())
        );

        StandardErrorResponse errorResponse = new StandardErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                request.getDescription(false).replace("uri=", ""),
                erros
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponse> handleGenericException(
            Exception ex,
            WebRequest request) {

        logger.error("Erro interno: ", ex);

        StandardErrorResponse errorResponse = new StandardErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno do servidor",
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
