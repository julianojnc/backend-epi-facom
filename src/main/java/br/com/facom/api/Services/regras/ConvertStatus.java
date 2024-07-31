package br.com.facom.api.Services.regras;

import br.com.facom.api.Model.PerifericoModel;

public class ConvertStatus {

    private void converteStatusPeriferico(Long id) {
        PerifericoModel p = new PerifericoModel();
        if (p.getIsVinculado() == 0) {
            p.setIsVinculado(1);
        }
        String sql = "update periferico set is_vinculado = 1 where id=" + id + ";";
    }

}
