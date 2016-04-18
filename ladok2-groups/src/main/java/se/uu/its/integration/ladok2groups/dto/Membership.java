package se.uu.its.integration.ladok2groups.dto;

import java.util.Date;


public class Membership {
	
	Long id;
	Date date;
	String pnr;
	String courseCode;
	String reportCode;
	String semester;
	String origin;
	String origin2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getOrigin2() {
		return origin2;
	}
	public void setOrigin2(String origin2) {
		this.origin2 = origin2;
	}
	
	@Override
	public String toString() {
		return "Membership [id=" + id + ", pnr=" + pnr + ", courseCode="
				+ courseCode + ", reportCode=" + reportCode + ", semester="
				+ semester + ", origin=" + origin + ", origin2=" + origin2
				+ "]";
	}

}

