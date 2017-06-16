package se.uu.its.integration.ladok2groups.l2dto;


public class Namn extends PnrEvent {

	String avliden;

	public String getAvliden() {
		return avliden;
	}

	public void setAvliden(String avliden) {
		this.avliden = avliden;
	}

	@Override
	public String toString() {
		return "Namn [avliden=" + avliden + ", pnr=" + pnr + ", datum=" + datum
				+ ", tid=" + tid + "]";
	}
	
}
