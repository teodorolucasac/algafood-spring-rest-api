package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

    List<Cozinha> findAllByNomeContainingIgnoreCase(String nome);

    Optional<Cozinha> findByNomeIgnoreCase(String nome);

    boolean existsByNome(String nome);
}
