package br.com.facom.api.Repository;

import br.com.facom.api.Model.EpiPerifericoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Repository;

@Repository
public interface EpiPerifericoRepository extends JpaRepository<EpiPerifericoModel, Long> {
}
