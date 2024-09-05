package br.com.facom.api.Repository;

import br.com.facom.api.Model.EpiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EpiRepository extends JpaRepository<EpiModel, Long> {

    boolean existsByPatrimonio( String patrimonio);
}
