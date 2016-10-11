package se.uu.its.integration.ladok2groups.l2dto;


public class Antagen {
    
	String anmkod;
    String termin;
    String kurskod;
	String pnr;
    String program;
    String inriktning;
    String svar;
    String villkor;
    String villkor2;
    String villkor3;
    String proganmkod;
    
	public String getAnmkod() {
		return anmkod;
	}
	public void setAnmkod(String anmkod) {
		this.anmkod = anmkod;
	}
	public String getTtermin() {
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
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
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
	public String getSvar() {
		return svar;
	}
	public void setSvar(String svar) {
		this.svar = svar;
	}
	public String getVillkor() {
		return villkor;
	}
	public void setVillkor(String villkor) {
		this.villkor = villkor;
	}
	public String getVillkor2() {
		return villkor2;
	}
	public void setVillkor2(String villkor2) {
		this.villkor2 = villkor2;
	}
	public String getVillkor3() {
		return villkor3;
	}
	public void setVillkor3(String villkor3) {
		this.villkor3 = villkor3;
	}
	public String getProganmkod() {
		return proganmkod;
	}
	public void setProganmkod(String proganmkod) {
		this.proganmkod = proganmkod;
	}

	@Override
	public String toString() {
		return "Antagen [anmkod=" + anmkod + ", termin=" + termin
				+ ", kurskod=" + kurskod + ", pnr=" + pnr + ", program="
				+ program + ", inriktning=" + inriktning + ", svar=" + svar
				+ ", villkor=" + villkor + ", villkor2=" + villkor2
				+ ", villkor3=" + villkor3 + ", proganmkod=" + proganmkod + "]";
	}
}
