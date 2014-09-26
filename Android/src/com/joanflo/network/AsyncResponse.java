package com.joanflo.network;

/**
 * This interface allows to another classes to act like a delegate and receive data
 * when the server returns a response.
 * @author Joanflo
 */
public interface AsyncResponse {
	
	public void requestFinished(InfoResponse[] infoResponses);
	
}
