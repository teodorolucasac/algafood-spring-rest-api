package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.OffsetDateTime;

@Getter
@Setter
public class UsuarioModel {

    private Long id;
    private String nome;
    private String email;
}
