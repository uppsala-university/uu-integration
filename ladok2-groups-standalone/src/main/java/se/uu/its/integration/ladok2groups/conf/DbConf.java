package se.uu.its.integration.ladok2groups.conf;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
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
	
	@Bean
	@Primary
	@Qualifier("ladok2read")
	@ConfigurationProperties(prefix="datasource.ladok2read")
	public DataSource ladok2ReadDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("ladok2read")
	public NamedParameterJdbcTemplate ladok2Jdbc(@Qualifier("ladok2read") DataSource ladok2ReadDataSource) {
	    return new NamedParameterJdbcTemplate(ladok2ReadDataSource);
	}

	@Bean
	@Qualifier("esb")
	@ConfigurationProperties(prefix="datasource.esb")
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
	public NamedParameterJdbcTemplate esbJdbc(@Qualifier("esb") DataSource esbDataSource, Flyway flyway) {
	    return new NamedParameterJdbcTemplate(esbDataSource);
	}
	
    @Bean
    public Flyway flyway(@Qualifier("esb") DataSource esbDataSource) {
	    	Flyway f = new Flyway();
	    	f.setDataSource(esbDataSource);
	    	f.migrate();
	    	return f;
    }

}
