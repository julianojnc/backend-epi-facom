package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cglib.core.Local;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

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

    @Column(name = "data_inicio", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private LocalDate dataInicio;

    @Column(name = "data_fim", columnDefinition = "DATE")
    private LocalDate dataFim;

    @PrePersist
    protected void onCreate(){
        if(this.dataInicio==null){
            this.dataInicio=LocalDate.now();
        }
    }

}

