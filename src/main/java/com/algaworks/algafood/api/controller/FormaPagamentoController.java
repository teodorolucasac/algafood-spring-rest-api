package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/formaspagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoModelDisassembler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoModelDisassembler.toDomain(formaPagamentoInput);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoModel atualizar(@PathVariable Long id, FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = cadastroFormaPagamentoService.buscarOuFalhar(id);
        formaPagamentoModelDisassembler.copyToDomain(formaPagamentoInput, formaPagamentoAtual);

        return formaPagamentoModelAssembler.toModel(formaPagamentoAtual);
    }

    @GetMapping
    public List<FormaPagamento> listar() {
        return cadastroFormaPagamentoService.listar();
    }

    @GetMapping("/{id}")
    public FormaPagamento buscar(@PathVariable Long id) {
        return cadastroFormaPagamentoService.buscarOuFalhar(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        cadastroFormaPagamentoService.excluir(id);
    }
}
