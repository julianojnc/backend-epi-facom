package br.com.facom.api.Repository;

import br.com.facom.api.Model.PerifericoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerifericoRepository extends JpaRepository<PerifericoModel, Long> {

    List<PerifericoModel> findByDataGarantia(LocalDate dataGarantia);
}
