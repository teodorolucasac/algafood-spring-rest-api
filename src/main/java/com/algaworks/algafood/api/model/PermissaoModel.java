package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "permissoes")
@Getter
@Setter
public class PermissaoModel extends RepresentationModel<PermissaoModel> {

    private Long id;
    private String nome;
    private String descricao;
}
