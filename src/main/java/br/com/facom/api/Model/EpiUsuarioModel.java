package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="epi_usuario")
public class EpiUsuarioModel extends RepresentationModel<EpiUsuarioModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_epi", nullable = false)
    private EpiModel idEpi;

    @ManyToOne
    @JoinColumn(name="id_usuario", nullable = false, referencedColumnName = "id")
    private UsuarioModel idUsuario;


}

