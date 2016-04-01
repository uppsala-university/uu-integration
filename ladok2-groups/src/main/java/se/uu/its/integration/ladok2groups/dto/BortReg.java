package se.uu.its.integration.ladok2groups.dto;


public class BortReg {
	
	String datum;
	String tid;
	String pnr;
	String kurskod;
    String urtabell;
    String urpost;
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
	public String getKurskod() {
		return kurskod;
	}
	public void setKurskod(String kurskod) {
		this.kurskod = kurskod;
	}
	public String getUrtabell() {
		return urtabell;
	}
	public void setUrtabell(String urtabell) {
		this.urtabell = urtabell;
	}
	public String getUrpost() {
		return urpost;
	}
	public void setUrpost(String urpost) {
		this.urpost = urpost;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Override
	public String toString() {
		return "BortReg [datum=" + datum + ", tid=" + tid + ", pnr=" + pnr
				+ ", kurskod=" + kurskod + ", urtabell=" + urtabell
				+ ", urpost=" + urpost + ", origin=" + origin + "]";
	}
	
}
