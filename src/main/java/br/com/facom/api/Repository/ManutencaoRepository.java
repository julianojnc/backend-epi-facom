package br.com.facom.api.Repository;

import br.com.facom.api.Model.ManutencaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManutencaoRepository extends JpaRepository<ManutencaoModel, Long> {
}
