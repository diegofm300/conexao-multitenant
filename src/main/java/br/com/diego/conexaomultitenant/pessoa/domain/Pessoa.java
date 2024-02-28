package br.com.diego.conexaomultitenant.pessoa.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pessoa {

	@Id
	@SequenceGenerator(name = "sequencia_id_pessoa", sequenceName = "pessoa_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencia_id_pessoa")
	private Long id;

	private String nome;

	public static Pessoa of(String nome) {
		return new Pessoa(null, nome);
	}
}
