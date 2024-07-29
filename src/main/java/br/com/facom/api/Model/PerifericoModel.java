package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="periferico")
public class PerifericoModel extends RepresentationModel<PerifericoModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "express_code")
    private String expressCode;

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "data_vinculacao", nullable = false)
    private LocalDate dataVinculacao;

    @Column(name = "is_vinculado", columnDefinition = "INTEGER DEFAULT 0")
    private int isVinculado = 0;

    @Column(name = "tempo_vinculado")
    private int tempoVinculado;

    @Column(name = "data_desvinculacao")
    private LocalDate dataDesvinculacao;

    @Column(name = "registro_desvinculacao")
    private String registroDesvinculacao;

    @ManyToOne
    @JoinColumn(name = "id_marca", referencedColumnName = "id")
    private MarcaModel idMarca;

}