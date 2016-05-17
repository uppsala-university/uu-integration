package se.uu.its.integration.ladok2groups.l2dto;

public class PnrEvent {
	
	String pnr;
    String datum;
    String tid = "235959";

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
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		// Absence of time -> set as late as possible to not miss any event
		if (tid == null || tid.equals("000000")) {
			this.tid = "235959";
		} else {
			this.tid = tid;
		}
	}
}
