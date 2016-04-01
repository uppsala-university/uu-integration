package se.uu.its.integration.ladok2groups.dto;


public class Reg {
    
    String datum;
    String tid;
    String pnr;
	String anmkod;
    String startter;
    String kurskod;
    String completed;
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
				+ ", kurskod=" + kurskod + ", completed=" + completed
				+ ", origin=" + origin + "]";
	}
	
}
