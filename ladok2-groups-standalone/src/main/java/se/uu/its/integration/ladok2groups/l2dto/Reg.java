package se.uu.its.integration.ladok2groups.l2dto;


public class Reg extends PnrEvent {
    
	String anmkod;
    String startter;
    String termin;
    String kurskod;
    String completed;
    String program;
    String inriktning;
    String origin;

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
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getInriktning() {
		return inriktning;
	}
	public void setInriktning(String inriktning) {
		this.inriktning = inriktning;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@Override
	public String toString() {
		return "Reg [anmkod=" + anmkod + ", startter=" + startter + ", termin="
				+ termin + ", kurskod=" + kurskod + ", completed=" + completed
				+ ", program=" + program + ", inriktning=" + inriktning
				+ ", origin=" + origin + ", pnr=" + pnr + ", datum=" + datum
				+ ", tid=" + tid + "]";
	}
}
