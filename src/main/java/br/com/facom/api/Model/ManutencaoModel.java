package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="manutencao")
public class ManutencaoModel extends RepresentationModel<ManutencaoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="descricao", nullable = false)
    private String descricao;

    @Column(name="valor")
    private float valor;

    @Column(name="data_inicio_manutencao")
    private LocalDate dataIniManutencao;

    @Column(name="data_retorno_manutencao")
    private LocalDate dataRetManutencao;

    @ManyToOne
    @JoinColumn(name="id_epi", referencedColumnName = "id")
    private EpiModel idEpi;

    @ManyToOne
    @JoinColumn(name="id_periferico", referencedColumnName = "id")
    private PerifericoModel idPeriferico;

}
