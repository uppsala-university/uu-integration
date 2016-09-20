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

@Configuration
public class DbConf {
	
	@Bean
	@Primary
	@Qualifier("ladok2read")
	@ConfigurationProperties(prefix="datasource.ladok2read")
	public DataSource uppdokOpenDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean
	@Qualifier("esb")
	@ConfigurationProperties(prefix="datasource.esb")
	public DataSource groupEventsDataSource() {
		return DataSourceBuilder.create().build();
	}	

	@Bean
	@Qualifier("ladok2read")
	public NamedParameterJdbcTemplate uppdokOpen(@Qualifier("ladok2read") DataSource ds) {
	    return new NamedParameterJdbcTemplate(ds);
	}

	@Bean
	@Qualifier("esb")
	public NamedParameterJdbcTemplate groupEvents(@Qualifier("esb") DataSource ds, Flyway f) {
	    return new NamedParameterJdbcTemplate(ds);
	}
	
    @Bean
    public Flyway flyway(@Qualifier("esb") DataSource ds) {
    	Flyway f = new Flyway();
    	f.setDataSource(ds);
		f.migrate();
    	return f;
    }

}
