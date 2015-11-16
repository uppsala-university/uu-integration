package se.uu.its.integration.service.group.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.uu.its.integration.model.events.GroupCreateRequestEvent;

@Path("/group")
public class GroupService {
	
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response putCreateGroup(GroupCreateRequestEvent event) {
        return null;
    }

}
