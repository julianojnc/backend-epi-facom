package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name="epi")
public class EpiModel extends RepresentationModel<EpiModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="nome", nullable = false)
    private String nome;

    @Column(name="patrimonio", nullable = false, unique = true)
    private String patrimonio;

    @Column(name = "service_tag", unique = true)
    private String serviceTag;

    @Column(name = "express_code", unique = true)
    private String expressCode;

    @Column(name="local")
    private String local;

    @Column(name="setor")
    private String setor;

    @Column(name="data_compra")
    private LocalDate dataCompra;

    @Column(name="data_garantia")
    private LocalDate dataGarantia;

    @OneToOne
    @JoinColumn(name="id_marca", referencedColumnName = "id")
    private MarcaModel idMarca;

}
