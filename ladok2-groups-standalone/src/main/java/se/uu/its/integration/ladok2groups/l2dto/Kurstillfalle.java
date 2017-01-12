package se.uu.its.integration.ladok2groups.l2dto;

public class Kurstillfalle {
	
	String starttermin;
	String anmkod;
	String kurskod;
	
	public String getStarttermin() {
		return starttermin;
	}
	public void setStarttermin(String starttermin) {
		this.starttermin = starttermin;
	}
	public String getAnmkod() {
		return anmkod;
	}
	public void setAnmkod(String anmkod) {
		this.anmkod = anmkod;
	}
	public String getKurskod() {
		return kurskod;
	}
	public void setKurskod(String kurskod) {
		this.kurskod = kurskod;
	}

	@Override
	public String toString() {
		return "Kurstillfalle [starttermin=" + starttermin + ", anmkod="
				+ anmkod + ", kurskod=" + kurskod + "]";
	}
}
