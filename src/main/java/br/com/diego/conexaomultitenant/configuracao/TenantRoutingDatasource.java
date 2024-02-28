package br.com.diego.conexaomultitenant.configuracao;

import br.com.diego.conexaomultitenant.configuracao.exception.NenhumBancoConfiguradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;

@Component
public class TenantRoutingDatasource extends AbstractRoutingDataSource {

	@Autowired
	private TenantIdentifierResolver tenantIdentifierResolver;

	public TenantRoutingDatasource() {
		HashMap<Object, Object> targetDataSources = new HashMap<>();
		Arrays.stream(DataBases.values())
			  .forEach(banco -> targetDataSources.put(banco.name(), createDataSource(banco.name())));

		setTargetDataSources(targetDataSources);
	}

	@Override
	protected Object determineCurrentLookupKey() {
		if (Arrays.stream(DataBases.values()).toList().isEmpty())
			throw new NenhumBancoConfiguradoException();

		if (tenantIdentifierResolver.resolveCurrentTenantIdentifier().equals("unknown"))
			return Arrays.stream(DataBases.values()).iterator().next().name();

		return tenantIdentifierResolver.resolveCurrentTenantIdentifier();
	}

	private DataSource createDataSource(String nomeBanco) {
		StringBuilder url = new StringBuilder("jdbc:postgresql://localhost:5433/");
		url.append(nomeBanco);

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(url.toString());
		dataSource.setCatalog("org.hibernate.dialect.PostgreSQLDialect");
		dataSource.setUsername("postgres");
		dataSource.setPassword("admin");
		return dataSource;
	}

}
