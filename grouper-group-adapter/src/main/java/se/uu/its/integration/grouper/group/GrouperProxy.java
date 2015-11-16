package se.uu.its.integration.grouper.group;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/grouper-ws/servicesRest/v2_2_000")
public interface GrouperProxy {

	@POST
	@Path(value="/groups")
	@Produces(MediaType.TEXT_XML + ";charset=UTF-8")
	public void createGroup(String groupCreateRequestXml);

}
