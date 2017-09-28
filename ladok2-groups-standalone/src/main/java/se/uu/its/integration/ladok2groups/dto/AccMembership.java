package se.uu.its.integration.ladok2groups.dto;

import java.time.LocalDateTime;

import se.uu.its.integration.ladok2groups.l2dto.Antagen;
import se.uu.its.integration.ladok2groups.util.PnrUtil;

public class AccMembership {

	LocalDateTime date;
	String pnr;
	String courseCode;
	String reportCode;
	String semester;
	String program;
	String programOrientation;
	String response;
	String condition1;
	String condition2;
	String condition3;
	String programReportCode;

	public AccMembership() {
	}

	public AccMembership(Antagen a, LocalDateTime date) {
		setDate(date);
		setPnr(a.getPnr());
		setReportCode(a.getAnmkod());
		setSemester(a.getTermin());
		setCourseCode(a.getKurskod());
		setCondition1(a.getVillkor());
		setCondition2(a.getVillkor2());
		setCondition3(a.getVillkor3());
		setProgram(a.getProgram());
		setProgramOrientation(a.getInriktning());
		setProgramReportCode(a.getProgramAnmkod());
		setResponse(a.getSvar());
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = PnrUtil.normalize(pnr);
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getProgramOrientation() {
		return programOrientation;
	}
	public void setProgramOrientation(String programOrientation) {
		this.programOrientation = programOrientation;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getCondition1() {
		return condition1;
	}
	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}
	public String getCondition2() {
		return condition2;
	}
	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}
	public String getCondition3() {
		return condition3;
	}
	public void setCondition3(String condition3) {
		this.condition3 = condition3;
	}
	public String getProgramReportCode() {
		return programReportCode;
	}
	public void setProgramReportCode(String programReportCode) {
		this.programReportCode = programReportCode;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pnr == null) ? 0 : pnr.hashCode());
		result = prime * result
				+ ((reportCode == null) ? 0 : reportCode.hashCode());
		result = prime * result
				+ ((semester == null) ? 0 : semester.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		AccMembership other = (AccMembership) obj;
		if (pnr == null) {
			if (other.pnr != null) {
				return false;
			}
		} else if (!pnr.equals(other.pnr)) {
			return false;
		}
		if (reportCode == null) {
			if (other.reportCode != null) {
				return false;
			}
		} else if (!reportCode.equals(other.reportCode)) {
			return false;
		}
		if (semester == null) {
			if (other.semester != null) {
				return false;
			}
		} else if (!semester.equals(other.semester)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AccMembership [date=" + date + ", pnr=" + pnr + ", courseCode="
				+ courseCode + ", reportCode=" + reportCode + ", semester="
				+ semester + ", program=" + program + ", programOrientation="
				+ programOrientation + ", response=" + response
				+ ", condition1=" + condition1 + ", condition2=" + condition2
				+ ", condition3=" + condition3 + ", programReportCode="
				+ programReportCode + "]";
	}
	
}
