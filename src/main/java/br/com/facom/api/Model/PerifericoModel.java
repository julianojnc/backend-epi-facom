package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "periferico")
public class PerifericoModel extends RepresentationModel<PerifericoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "patrimonio", unique = true)
    private String patrimonio;

    @Column(name = "service_tag", unique = true)
    private String serviceTag;

    @Column(name = "express_code",unique = true)
    private String expressCode;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "data_garantia")
    private LocalDate dataGarantia;

    @Column(name = "is_vinculado", columnDefinition = "INTEGER DEFAULT 0")
    private int isVinculado = 0;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private MarcaModel idMarca;

}
