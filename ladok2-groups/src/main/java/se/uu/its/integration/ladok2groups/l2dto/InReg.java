package se.uu.its.integration.ladok2groups.l2dto;


public class InReg extends PnrEvent {
	
	String termin;
	String kurskod;
	String origin;

	public String getTermin() {
		return termin;
	}
	public void setTermin(String termin) {
		this.termin = termin;
	}
	public String getKurskod() {
		return kurskod;
	}
	public void setKurskod(String kurskod) {
		this.kurskod = kurskod;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Override
	public String toString() {
		return "InReg [datum=" + datum + ", tid=" + tid + ", pnr=" + pnr
				+ ", termin=" + termin + ", kurskod=" + kurskod + ", origin="
				+ origin + "]";
	}
	
}
