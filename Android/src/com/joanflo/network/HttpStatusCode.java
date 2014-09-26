package com.joanflo.network;

/**
 * Status codes used in this app
 * @author Joanflo
 */
public class HttpStatusCode {
	
	// Success
	public static final int OK = 200;
	public static final int CREATED = 201;
	public static final int NO_CONTENT = 204;
	
	// Client Error
	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	public static final int REQUEST_TIMEOUT = 408;
	
	// Server Error
	public static final int INTERNAL_SERVER_ERROR = 500;
	
}