package se.uu.its.integration.esb.client.services;

public interface Identity {
	
	public void registerAkkaAccoungChange(String akkaEventRefId,
			String akkaKId, 
			String personnummer) throws Exception;

}
