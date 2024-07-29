package br.com.facom.api.Controller;

import br.com.facom.api.Exceptions.ForbbidenHandler;
import br.com.facom.api.Exceptions.RegistroNaoEncontradoHendler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice extends RuntimeException{


    @ExceptionHandler(RegistroNaoEncontradoHendler.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(RegistroNaoEncontradoHendler e){
        return e.getMessage();
    }

    @ExceptionHandler(ForbbidenHandler.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String ForbbidenException(ForbbidenHandler e){ return e.getMessage();}
}



