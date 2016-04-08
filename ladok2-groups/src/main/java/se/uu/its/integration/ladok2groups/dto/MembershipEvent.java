package se.uu.its.integration.ladok2groups.dto;

import java.util.Date;

import se.uu.its.integration.ladok2groups.MembershipEventUtil;

public class MembershipEvent {
	
	public enum Type { ADD, REMOVE };
	
	Long id;
	Date date;
	String pnr;
	Type type;
	String courseCode;
	String semester;
	String reportCode;
	String origin;
	String origin2;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFormattedDate() {
		return MembershipEventUtil.DATE_FORMAT.format(date);
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
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public void setType(String type) {
		this.type = Type.valueOf(type);
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
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
		return "MembershipEvent [id=" + id + ", date=" + getFormattedDate() + ", pnr=" + pnr
				+ ", type=" + type + ", courseCode=" + courseCode
				+ ", semester=" + semester + ", reportCode=" + reportCode
				+ ", origin=" + origin + ", origin2=" + origin2 + "]";
	}
	
}