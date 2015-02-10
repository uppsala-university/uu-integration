package se.uu.its.integration.service.identity.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.uu.its.integration.model.events.AffiliationEvent;

@Path("/identity/")
public class IdentityService {

    @POST
    @Path("/event/")
    @Consumes(MediaType.APPLICATION_XML)
    public Response putIdentityEvent(AffiliationEvent identityEvent) {
        return null;
    }	
	
}
