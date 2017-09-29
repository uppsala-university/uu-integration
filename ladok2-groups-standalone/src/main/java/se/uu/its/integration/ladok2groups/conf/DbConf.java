package se.uu.its.integration.ladok2groups.conf;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DbConf {

	@Autowired
	FlywayProps flywayProps;

	@Bean
	@Qualifier("ladok2read")
	@ConfigurationProperties(prefix = "datasource.ladok2read")
	public DataSource ladok2ReadDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("ladok2read")
	public NamedParameterJdbcTemplate ladok2Jdbc(@Qualifier("ladok2read") DataSource ladok2ReadDataSource) {
		return new NamedParameterJdbcTemplate(ladok2ReadDataSource);
	}

	@Bean
	@Qualifier("sp")
	@ConfigurationProperties(prefix = "datasource.sp")
	public DataSource spDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("sp")
	public NamedParameterJdbcTemplate spJdbc(@Qualifier("sp") DataSource spDataSource) {
		return new NamedParameterJdbcTemplate(spDataSource);
	}

	@Bean
	@Primary
	@Qualifier("esb")
	@ConfigurationProperties(prefix = "datasource.esb")
	public DataSource esbDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("esb")
	public DataSourceTransactionManager transactionManager(@Qualifier("esb") DataSource esbDataSource) {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(esbDataSource);
		return txManager;
	}

	@Bean
	@Qualifier("esb")
	public NamedParameterJdbcTemplate esbJdbc(@Qualifier("esb") DataSource esbDataSource) {
		return new NamedParameterJdbcTemplate(esbDataSource);
	}

	@Bean
	public Flyway flyway(@Qualifier("esb") DataSource esbDataSource) {
		Flyway f = new Flyway();
		f.setDataSource(esbDataSource);
		if (flywayProps.isBaselineOnMigrate()) {
			f.setBaselineOnMigrate(flywayProps.isBaselineOnMigrate());
			f.setBaselineVersionAsString(flywayProps.getBaselineVersion());
		}
		f.migrate();
		return f;
	}

}
