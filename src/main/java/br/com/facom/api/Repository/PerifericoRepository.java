package br.com.facom.api.Repository;

import br.com.facom.api.Model.PerifericoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerifericoRepository extends JpaRepository<PerifericoModel, Long> {
}
