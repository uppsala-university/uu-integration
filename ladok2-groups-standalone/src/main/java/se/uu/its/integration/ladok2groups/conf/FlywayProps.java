package se.uu.its.integration.ladok2groups.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@ConfigurationProperties(prefix="flyway")
public class FlywayProps {

	boolean baselineOnMigrate = false;
	String baselineVersion = "0";
	
	public boolean isBaselineOnMigrate() {
		return baselineOnMigrate;
	}
	public void setBaselineOnMigrate(boolean baselineOnMigrate) {
		this.baselineOnMigrate = baselineOnMigrate;
	}

	public String getBaselineVersion() {
		return baselineVersion;
	}
	public void setBaselineVersion(String baselineVersion) {
		this.baselineVersion = baselineVersion;
	}
}
