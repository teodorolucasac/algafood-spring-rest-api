package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.api.model.input.UsuarioSemSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioSenhaInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomain(UsuarioInput usuarioInput) {
        return modelMapper.map(usuarioInput, Usuario.class);
    }

    public void copyToDomain(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }

    public void copyToDomain(UsuarioSemSenhaInput usuarioSemSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioSemSenhaInput, usuario);
    }

    public void copyToDomain(UsuarioSenhaInput usuarioSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioSenhaInput, usuario);
    }
}
