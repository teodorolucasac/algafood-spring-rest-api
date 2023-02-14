package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PermissaoController;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoModelAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoModel> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public PermissaoModelAssembler() {
        super(PermissaoController.class, PermissaoModel.class);
    }

    public PermissaoModel toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoModel.class);
    }

    public CollectionModel<PermissaoModel> toCollectionModel(Iterable<? extends Permissao> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToPermissoes());
    }
}
