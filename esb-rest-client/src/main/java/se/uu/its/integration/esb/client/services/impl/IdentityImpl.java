package se.uu.its.integration.esb.client.services.impl;

import java.io.ByteArrayInputStream;
import java.util.zip.GZIPInputStream;

import javax.ws.rs.core.Response;

import se.uu.its.integration.esb.client.services.Identity;
import se.uu.its.integration.esb.client.services.ServiceBase;

public class IdentityImpl extends ServiceBase implements Identity {

	private String kataloginformationUrl = "kataloginformation/";

	private static final String RESPONSE_TYPE_KATALOGINFORMATION = "application/vnd.ladok-kataloginformation";
	
    public IdentityImpl() throws Exception {
		super();
		
		if (cb != null)
			webtarget = cb.build().target(restBase + kataloginformationUrl);
		else
			throw new Exception("Could not initialize service.");
	}

	
	@Override
	public void registerAkkaAccoungChange(String akkaId, String personnummer) throws Exception {

    	log.info("Query URL: " +  restBase + kataloginformationUrl + "behorigheter");
    	log.info("Requested response type: " + RESPONSE_TYPE_KATALOGINFORMATION.toString() + mediaType);		
		
    	
    	Response res = webtarget.path("behorigheter").request(RESPONSE_TYPE_KATALOGINFORMATION + mediaType).get();
    	String body = res.readEntity(String.class);
    	
    	GZIPInputStream  gzip = new GZIPInputStream(new ByteArrayInputStream (body.getBytes()));

    	StringBuffer  szBuffer = new StringBuffer();

    	byte  tByte [] = new byte [1024];

    	while (true)
    	{
    	    int  iLength = gzip.read (tByte, 0, 1024); // <-- Error comes here

    	    if (iLength < 0)
    	        break;

    	    szBuffer.append (new String (tByte, 0, iLength));
    	}
    	
    	
//    	return szBuffer.toString();	
    }

}
