package se.uu.its.integration.ladok2groups.dto;

import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.parse;
import static se.uu.its.integration.ladok2groups.util.MembershipEventUtil.parseUrPost;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.uu.its.integration.ladok2groups.l2dto.Namn;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.PnrEvent;
import se.uu.its.integration.ladok2groups.l2dto.Reg;
import se.uu.its.integration.ladok2groups.util.MembershipEventUtil;
import se.uu.its.integration.ladok2groups.util.PnrUtil;

public class PotentialMembershipEvent {
	
	public static enum Type { 
		ADD, // Deprecated
		KurstillfalleTillStatusEvent, 
		ForvantatDeltagandeSkapadEvent,
		AterbudEvent,
		RegistreringEvent,
		OmregistreringEvent,
		AvbrottEvent,
		AterkalladregistreringEvent,
		AterkalladOmregistreringEvent,
		StudentAvlidenmarkeringEvent,
		KontaktuppgifterEvent
	};
	
	public static Set<Type> MEMBERSHIP_ADD_TYPES = new HashSet<Type>(Arrays.asList(new Type[]{
			Type.ADD,
			Type.ForvantatDeltagandeSkapadEvent,
			Type.RegistreringEvent,
			Type.OmregistreringEvent
	}));
	
	Long id;
	LocalDateTime date;
	String pnr;
	Type meType;
	String courseCode;
	String reportCode;
	String startSemester;
	String semester;
	String program;
	String programOrientation;
	String origin;
	String origin2;
	
	public PotentialMembershipEvent() {
	}
	
	public PotentialMembershipEvent(PotentialMembershipEvent pme) {
		setMeType(pme.getMeType());
		setDate(pme.getDate());
		setPnr(pme.getPnr());
		setCourseCode(pme.getCourseCode());
		setReportCode(pme.getReportCode());
		setStartSemester(pme.getStartSemester());
		setSemester(pme.getSemester());
		setProgram(pme.getProgram());
		setProgramOrientation(pme.getProgramOrientation());
		setOrigin(pme.getOrigin());
		setOrigin2(pme.getOrigin2());
	}
	
	public PotentialMembershipEvent(PnrEvent pe) {
		setPnr(pe.getPnr());
		setDate(pe.getDatum(), pe.getTid());
	}

	public PotentialMembershipEvent(Reg r) {
		this((PnrEvent) r);
		setMeType("OMREG".equals(r.getOrigin()) ? Type.OmregistreringEvent
				: Type.RegistreringEvent); // Type.ADD
		setCourseCode(r.getKurskod());
		setReportCode(r.getAnmkod());
		setStartSemester(r.getStartter());
		setSemester(r.getTermin());
		setProgram(r.getProgram());
		setProgramOrientation(r.getInriktning());
		String[] o1o2 = r.getOrigin().split(":");
		setOrigin(o1o2[0]);
		setOrigin2(o1o2[1]);
	}

	public PotentialMembershipEvent(BortReg r) {
		this((PnrEvent) r);
		setMeType("OMKURS".equals(r.getUrtabell()) ?
				Type.AterkalladOmregistreringEvent 
				: Type.AterkalladregistreringEvent); // Type.REMOVE
		Map<String, String> urPost = parseUrPost(r.getUrpost());
		setSemester(urPost.get("TERMIN"));
		setCourseCode(r.getKurskod());
		setOrigin("BORTREGK");
		setOrigin2(r.getUrtabell());
	}
	
	public PotentialMembershipEvent(InReg r) {
		this((PnrEvent) r);
		setMeType(Type.AvbrottEvent); // Type.REMOVE
		// setMeType("INREGOM".equals(r.getOrigin()) ? Type.AterkalladOmregistreringEvent : Type.AterkalladregistreringEvent); // Type.REMOVE
		setOrigin(r.getOrigin());
		setCourseCode(r.getKurskod());
		setSemester(r.getTermin());
	}

	public PotentialMembershipEvent(Namn n) {
		this((PnrEvent) n);
		if ("J".equals(n.getAvliden())) {
			setMeType(Type.StudentAvlidenmarkeringEvent);
		} else {
			setMeType(Type.KontaktuppgifterEvent);
		}
		this.pnr = n.getSekel() + n.getPnr(); // Override setPnr because we have access to sekel
		setOrigin("NAMN");
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFormattedDate() {
		return MembershipEventUtil.format(date);
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public void setDate(String date, String time) {
		// Absence of time -> set as late as possible to not miss any event
		if (time == null || time.equals("000000") || time.length() !=6) {
			time = "235959";
		}
		this.date = parse(date + " " + time);
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = PnrUtil.normalize(pnr);
	}
	public Type getMeType() {
		return meType;
	}
	public String getMeTypeAsString() {
		return meType.toString();
	}
	public void setMeType(Type type) {
		this.meType = type;
	}
	public void setMeType(String type) {
		this.meType = Type.valueOf(type);
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
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
	public String getReportCode() {
		return reportCode;
	}
	public void setReportCode(String reportCode) {
		this.reportCode = reportCode;
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
		return "PotentialMembershipEvent [id=" + id + ", date=" + date
				+ ", pnr=" + pnr + ", meType=" + meType + ", courseCode="
				+ courseCode + ", reportCode=" + reportCode
				+ ", startSemester=" + startSemester + ", semester=" + semester
				+ ", program=" + program + ", programOrientation=" + programOrientation
				+ ", origin=" + origin + ", origin2=" + origin2 + "]";
	}
}
