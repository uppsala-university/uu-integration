package se.uu.its.integration.ladok2groups;

import se.uu.its.integration.ladok2groups.dto.MembershipEvent;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Reg;


public class GroupEventUtil {
	
	public static MembershipEvent toGroupEvent(Reg r) {
		MembershipEvent ge = new MembershipEvent();
		return ge;
	}

	public static MembershipEvent toGroupEvent(BortReg r) {
		MembershipEvent ge = new MembershipEvent();
		return ge;
	}

	public static MembershipEvent toGroupEvent(InReg r) {
		MembershipEvent ge = new MembershipEvent();
		return ge;
	}

	public static MembershipEvent toGroupEvent(Avliden a) {
		MembershipEvent ge = new MembershipEvent();
		return ge;
	}

}
