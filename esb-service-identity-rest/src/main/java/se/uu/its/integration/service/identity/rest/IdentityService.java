package se.uu.its.integration.service.identity.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import se.uu.its.integration.model.events.AffiliationEvent;
import se.uu.its.integration.model.events.OrganizationDepartmentMappingEvent;
import se.uu.its.integration.model.events.PersonEvent;
import se.uu.its.integration.model.events.RoleEvent;

@Path("/identity/")
public interface IdentityService {
	
    @POST
    @Path("/event/affiliation/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response putIdentityAffiliationEvent(AffiliationEvent event); /*{
        return null;
    }*/

    @POST
    @Path("/event/person/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response putIdentityPersonEvent(PersonEvent event); /*{
        return null;
    }*/
    
    @POST
    @Path("/event/role/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response putIdentityRoleEvent(RoleEvent event); /* {
        return null;
    }*/
    
    @POST
    @Path("/event/orgdepmap/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response putIdentityOrganizationDepartmentMappingEvent(OrganizationDepartmentMappingEvent event); /* {
        return null;
    }*/
}
