package br.com.facom.api.Repository;

import br.com.facom.api.Model.MarcaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<MarcaModel, Long>{
}
