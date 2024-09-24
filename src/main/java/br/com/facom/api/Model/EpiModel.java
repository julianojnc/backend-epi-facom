package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "epi")
public class EpiModel extends RepresentationModel<EpiModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "patrimonio", nullable = false)
    private String patrimonio;

    @Column(name = "service_tag")
    private String serviceTag;

    @Column(name = "express_code")
    private String expressCode;

    @Column(name = "local")
    private String local;

    @Column(name = "setor")
    private String setor;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "data_garantia")
    private LocalDate dataGarantia;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @OneToOne
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private MarcaModel idMarca;

}
