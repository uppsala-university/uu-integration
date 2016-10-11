package se.uu.its.integration.ladok2groups.l2dto;


public class Accepted extends PnrEvent {
    
	String anmkod;
    String startter;
    String termin;
    String kurskod;
    String completed;
    String origin;

    /*
    la.anmalt as anmkod,
    la.termin as starttermin,
    kt.kurskod as kurskod,
    la.pnr as pnr,
    la.progr as program,
    la.proginr as orientation,
    la.svar as svar,
    la.villkor as villkor,
    la.villkor2 as villkor2,
    la.villkor3 as villkor3,
    la.inlkoppl as proganmkod
	*/
    
	public String getAnmkod() {
		return anmkod;
	}
	public void setAnmkod(String anmkod) {
		this.anmkod = anmkod;
	}
	public String getStartter() {
		return startter;
	}
	public void setStartter(String startter) {
		this.startter = startter;
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
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Override
	public String toString() {
		return "Reg [datum=" + datum + ", tid=" + tid + ", pnr=" + pnr
				+ ", anmkod=" + anmkod + ", startter=" + startter
				+ ", termin=" + termin + ", kurskod=" + kurskod
				+ ", completed=" + completed + ", origin=" + origin + "]";
	}
	
}
