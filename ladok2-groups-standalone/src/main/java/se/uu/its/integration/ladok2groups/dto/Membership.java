package se.uu.its.integration.ladok2groups.dto;

import java.time.LocalDateTime;

public class Membership {
	
	Long id;
	LocalDateTime date;
	String pnr;
	String courseCode;
	String reportCode;
	String startSemester;
	String semester;
	String program;
	String programOrientation;
	String origin;
	String origin2;
	
	public Membership() {
	}
	
	public Membership(PotentialMembershipEvent me) {
		setDate(me.getDate());
		setPnr(me.getPnr());
		setCourseCode(me.getCourseCode());
		setReportCode(me.getReportCode());
		setStartSemester(me.getStartSemester());
		setSemester(me.getSemester());
		setOrigin(me.getOrigin());
		setOrigin2(me.getOrigin2());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getStartSemester() {
		return startSemester;
	}
	public void setStartSemester(String startSemester) {
		this.startSemester = startSemester;
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
	public void setProgramOrientation(String orientation) {
		this.programOrientation = orientation;
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
		return "Membership [id=" + id + ", date=" + date + ", pnr=" + pnr
				+ ", courseCode=" + courseCode + ", reportCode=" + reportCode
				+ ", startSemester=" + startSemester + ", semester=" + semester
				+ ", program=" + program + ", programOrientation="
				+ programOrientation + ", origin=" + origin + ", origin2="
				+ origin2 + "]";
	}
	
}
