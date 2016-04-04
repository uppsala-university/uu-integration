package se.uu.its.integration.ladok2groups;

import se.uu.its.integration.ladok2groups.dto.GroupEvent;
import se.uu.its.integration.ladok2groups.l2dto.Avliden;
import se.uu.its.integration.ladok2groups.l2dto.BortReg;
import se.uu.its.integration.ladok2groups.l2dto.InReg;
import se.uu.its.integration.ladok2groups.l2dto.Reg;


public class GroupEventUtil {
	
	public static GroupEvent toGroupEvent(Reg r) {
		GroupEvent ge = new GroupEvent();
		return ge;
	}

	public static GroupEvent toGroupEvent(BortReg r) {
		GroupEvent ge = new GroupEvent();
		return ge;
	}

	public static GroupEvent toGroupEvent(InReg r) {
		GroupEvent ge = new GroupEvent();
		return ge;
	}

	public static GroupEvent toGroupEvent(Avliden a) {
		GroupEvent ge = new GroupEvent();
		return ge;
	}

}
