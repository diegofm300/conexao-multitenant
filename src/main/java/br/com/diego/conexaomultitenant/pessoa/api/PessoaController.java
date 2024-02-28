package br.com.diego.conexaomultitenant.pessoa.api;

import br.com.diego.conexaomultitenant.configuracao.TenantIdentifierResolver;
import br.com.diego.conexaomultitenant.pessoa.api.dto.PessoaDTO;
import br.com.diego.conexaomultitenant.pessoa.domain.Pessoa;
import br.com.diego.conexaomultitenant.pessoa.domain.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PessoaController.PATH)
public class PessoaController {
	public static final String PATH = "/pessoas";

	@Autowired
	TenantIdentifierResolver currentTenant;

	@Autowired
	TransactionTemplate txTemplate;

	@Autowired
    PessoaRepository pessoaRepository;

	@PostMapping
	public ResponseEntity persistir(@RequestBody PessoaDTO pessoaDTO) {

		currentTenant.setCurrentTenant(pessoaDTO.getBanco());

		Pessoa pessoa = txTemplate.execute(tx -> {
			Pessoa pessoaPessoa = Pessoa.of(pessoaDTO.getNome());
			return pessoaRepository.save(pessoaPessoa);
		});

		return ResponseEntity.ok(pessoa);
	}

	@GetMapping
	public ResponseEntity<List<Pessoa>> consultar(@RequestBody PessoaDTO pessoaDTO) {

		currentTenant.setCurrentTenant(pessoaDTO.getBanco());

		return ResponseEntity.ok(txTemplate.execute(tx -> pessoaRepository.findAll()));
	}

}
