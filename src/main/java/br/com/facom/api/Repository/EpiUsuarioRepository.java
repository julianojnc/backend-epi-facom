package br.com.facom.api.Repository;

import br.com.facom.api.Model.EpiUsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpiUsuarioRepository extends JpaRepository<EpiUsuarioModel, Long> {
}
