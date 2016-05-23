package se.uu.its.integration.ladok2groups.l2dto;


public class BortReg extends PnrEvent {
	
	String kurskod;
    String urtabell;
    String urpost;

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
	
	@Override
	public String toString() {
		return "BortReg [datum=" + datum + ", tid=" + tid + ", pnr=" + pnr
				+ ", kurskod=" + kurskod + ", urtabell=" + urtabell
				+ ", urpost=" + urpost + "]";
	}
	
}
