package br.com.diego.conexaomultitenant.pessoa.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
