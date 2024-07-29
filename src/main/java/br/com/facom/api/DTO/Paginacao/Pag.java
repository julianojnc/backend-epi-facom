package br.com.facom.api.DTO.Paginacao;

import br.com.facom.api.DTO.EpiDTO;

import java.util.List;

/*
* Classe genérica para paginação de todas as tabelas do banco
*
* */
public record Pag<T>(List<T> lista, Long totalRegistros, int totalPaginas) {
}
