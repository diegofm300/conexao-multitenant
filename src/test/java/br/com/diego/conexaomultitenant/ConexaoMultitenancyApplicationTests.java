package br.com.diego.conexaomultitenant;

import br.com.diego.conexaomultitenant.configuracao.TenantIdentifierResolver;
import br.com.diego.conexaomultitenant.pessoa.domain.Pessoa;
import br.com.diego.conexaomultitenant.pessoa.domain.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ConexaoMultitenancyApplicationTests {

	@Autowired
	PessoaRepository pessoaRepository;

	@Autowired
	TransactionTemplate txTemplate;

	@Autowired
	TenantIdentifierResolver currentTenant;

	@Test
	void contextLoads() {
	}

	private Pessoa criarObjeto(String schema, String name) {

		currentTenant.setCurrentTenant(schema);

		Pessoa pessoa = txTemplate.execute(tx -> {
			Pessoa pessoaPessoa = Pessoa.of(name);
			return pessoaRepository.save(pessoaPessoa);
		});

		assertThat(pessoa.getId()).isNotNull();
		return pessoa;
	}

	@Test
	void devePersistirObjetosEmBancosDiferentes() {
		criarObjeto("banco1", "Joao do banco 1");
		criarObjeto("banco2", "Joao do banco 2");

		currentTenant.setCurrentTenant("banco1");
		assertThat(pessoaRepository.findAll()).extracting(Pessoa::getNome).contains("Joao do banco 1");

		currentTenant.setCurrentTenant("banco2");
		assertThat(pessoaRepository.findAll()).extracting(Pessoa::getNome).contains("Joao do banco 2");
	}

}
