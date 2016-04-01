package se.uu.its.integration.ladok2groups.dto;


public class Avliden {
	
	String pnr;
	String datum;
	
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		return "Avliden [pnr=" + pnr + ", datum=" + datum + "]";
	}
	
}
