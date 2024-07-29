package br.com.facom.api.Repository;

import br.com.facom.api.Model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>, PagingAndSortingRepository<UsuarioModel, Long> {
}
