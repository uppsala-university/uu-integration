package se.uu.its.integration.ladok2groups.dto;

public class InReg {
	
	String datum;
	String tid;
	String pnr;
	String termin;
	String kurskod;
	String origin;

	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
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
