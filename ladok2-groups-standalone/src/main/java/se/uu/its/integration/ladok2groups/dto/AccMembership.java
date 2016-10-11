package se.uu.its.integration.ladok2groups.dto;

import java.util.Date;

public class AccMembership {

	Date date;
	String pnr;
	String courseCode;
	String reportCode;
	String semester;
	String program;
	String orientation;
	String response;
	String condition;
	String condition2;
	String condition3;
	String progReportCode;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
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
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
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
	public String getProgReportCode() {
		return progReportCode;
	}
	public void setProgReportCode(String progReportCode) {
		this.progReportCode = progReportCode;
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
				+ semester + ", program=" + program + ", orientation="
				+ orientation + ", response=" + response + ", condition="
				+ condition + ", condition2=" + condition2 + ", condition3="
				+ condition3 + ", progReportCode=" + progReportCode + "]";
	}
}
