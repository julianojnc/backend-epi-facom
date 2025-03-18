package br.com.facom.api.Repository;

import br.com.facom.api.Model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTokenRepository extends JpaRepository<TokenModel, Long> {

    // Busca um token pelo valor
    TokenModel findByToken(String token);

    // Busca todos os tokens
    List<TokenModel> findAll();

    // Exclui um token pelo valor
    void deleteByToken(String token);
}
