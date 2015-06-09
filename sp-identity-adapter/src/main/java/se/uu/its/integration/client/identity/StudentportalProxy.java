package se.uu.its.integration.client.identity;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/akka-event")
public interface StudentportalProxy {

	@POST
	@Path(value="")
	public void invalidateUserCache(@QueryParam("accountId") String accountId);

	@GET
	@Path(value="/user/{userId}")
	@Produces({MediaType.APPLICATION_JSON})
	public String userInfo(@PathParam("userId") String userId);
}
