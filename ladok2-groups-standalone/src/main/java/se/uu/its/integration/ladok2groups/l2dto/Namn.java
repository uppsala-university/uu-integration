package se.uu.its.integration.ladok2groups.l2dto;


public class Namn extends PnrEvent {

	String avliden;
	String sekel;

	public String getAvliden() {
		return avliden;
	}

	public void setAvliden(String avliden) {
		this.avliden = avliden;
	}

	public String getSekel() {
		return sekel;
	}

	public void setSekel(String sekel) {
		this.sekel = sekel;
	}

	@Override
	public String toString() {
		return "Namn [avliden=" + avliden + ", sekel=" + sekel + ", pnr=" + pnr
				+ ", datum=" + datum + ", tid=" + tid + "]";
	}
	
}
