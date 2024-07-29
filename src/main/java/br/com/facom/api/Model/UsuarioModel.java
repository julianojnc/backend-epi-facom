package br.com.facom.api.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="usuario")
public class UsuarioModel extends RepresentationModel<UsuarioModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tel_contato")
    private String telContato;

    @Column(name = "email")
    private String email;

    @Column(name = "is_vinculado")
    private int isVinculado;
}
