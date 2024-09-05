package br.com.facom.api.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@ControllerAdvice
public class AppControllerAdvice{

    @ExceptionHandler(RegistroNaoEncontradoHendler.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(RegistroNaoEncontradoHendler e){
        return e.getMessage();
    }

    @ExceptionHandler(ForbbidenHandler.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String ForbbidenException(ForbbidenHandler e){ return e.getMessage();}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<CustomErrorHandler> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors(); // cria uma lista dos campos inválidos
        List<CustomErrorHandler> errorHandlerList = new ArrayList<>();
        fieldErrors.forEach(error -> errorHandlerList.add(new CustomErrorHandler(error.getField(), error.getDefaultMessage())));
        return errorHandlerList;
    }
}
