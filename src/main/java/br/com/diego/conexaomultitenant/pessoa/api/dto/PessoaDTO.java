package br.com.diego.conexaomultitenant.pessoa.api.dto;

import lombok.*;


@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PessoaDTO {

    private String banco;
    private String nome;
}
