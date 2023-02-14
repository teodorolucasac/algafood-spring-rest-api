package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
                .toCollectionModel(restaurante.getResponsaveis())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(restauranteId))
                .add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

        usuariosModel.getContent().stream().forEach(usuarioModel -> {
            usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
                    restauranteId, usuarioModel.getId(), "desassociar"));
        });

        return usuariosModel;
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        cadastroRestaurante.associarResponsavel(restauranteId, responsavelId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long responsavelId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, responsavelId);

        return ResponseEntity.noContent().build();
    }

}
