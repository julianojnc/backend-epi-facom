package br.com.facom.api.Exceptions;

public class ForbbidenHandler extends RuntimeException{

    private final String mensagem="";

    public ForbbidenHandler(String mensagem){super(mensagem);}

}
