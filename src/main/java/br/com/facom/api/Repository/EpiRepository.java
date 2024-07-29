package br.com.facom.api.Repository;

import br.com.facom.api.Model.EpiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpiRepository extends JpaRepository<EpiModel, Long> {
}
