package br.com.facom.api.Exceptions;

public class RegistroNaoEncontradoHendler extends RuntimeException{

    private static final Long id = null;

    public RegistroNaoEncontradoHendler(Long id) {
        super("O registro de ID "+id+" não encontrado.");
    }
}
