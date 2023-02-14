package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Getter
@Setter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;
}
