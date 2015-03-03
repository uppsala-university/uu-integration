package se.uu.its.integration.esb.client.services;

public interface Identity {
	
	/**
	 * Notifies account changes.
	 * 
	 * @param akkaEventRefId Identifier of the local event.
	 * @param akkaKId The identifier of the changed account.
	 * @param personnummer The account reference of personal number.
	 * @return The identifier of the event handled by the integration technology.
	 * @throws Exception
	 */
	public String registerAkkaAccountChange(String akkaEventRefId,
			String akkaKId, 
			String personnummer) throws Exception;

}
