package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Table(name="epi_periferico")
@Data
@EqualsAndHashCode(callSuper = true)
public class EpiPerifericoModel extends RepresentationModel<EpiPerifericoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_epi", referencedColumnName = "id")
    private EpiModel idEpi;

    @ManyToOne
    @JoinColumn(name = "id_periferico", referencedColumnName = "id")
    private PerifericoModel idPeriferico;

    @Column(name = "data_vinculacao", columnDefinition = "DATE", updatable = false)
    private LocalDate dataVinculacao;

    @Column(name = "data_desvinculacao", columnDefinition = "DATE")
    private LocalDate dataDesvinculacao;

    @Column(name = "registro_desvinculacao", columnDefinition = "TEXT")
    private String registroDesvinculacao;

}

