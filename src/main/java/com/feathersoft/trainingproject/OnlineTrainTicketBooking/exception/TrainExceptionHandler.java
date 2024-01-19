package com.feathersoft.trainingproject.OnlineTrainTicketBooking.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class TrainExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(Exception.class)
        public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(LocalDate.now(),ex.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        @ExceptionHandler(TrainNotFoundException.class)
        public final ResponseEntity<ErrorDetails> handleTrainNotFoundException(Exception ex, WebRequest request){
        ErrorDetails errorDetails =new ErrorDetails(LocalDate.now(),ex.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
        }
       @ExceptionHandler(InsufficientSeatsException.class)
       public final ResponseEntity<ErrorDetails> handleInSufficientSeats(Exception ex, WebRequest request){
       ErrorDetails errorDetails =new ErrorDetails(LocalDate.now(),ex.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
       }

        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorDetails errorDetails =new ErrorDetails(LocalDate.now(),ex.getMessage(),request.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

       }

}
