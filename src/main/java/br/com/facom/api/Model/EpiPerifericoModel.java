package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

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

}
