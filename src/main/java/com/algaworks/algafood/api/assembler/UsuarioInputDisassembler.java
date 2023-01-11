package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomain(UsuarioComSenhaInput usuarioComSenhaInput) {
        return modelMapper.map(usuarioComSenhaInput, Usuario.class);
    }

    public void copyToDomain(UsuarioComSenhaInput usuarioComSenhaInput, Usuario usuario) {
        modelMapper.map(usuarioComSenhaInput, usuario);
    }

    public void copyToDomain(UsuarioInput usuarioInput, Usuario usuario) {
        modelMapper.map(usuarioInput, usuario);
    }

    public void copyToDomain(SenhaInput senhaInput, Usuario usuario) {
        modelMapper.map(senhaInput, usuario);
    }
}
