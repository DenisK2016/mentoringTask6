package com.dk.mentoring.webservice.rest.exceptions;

public enum Error
{
	NOT_AUTHORIZED(4010, "Not Authorized 777"), 
	FORBIDDEN(4030, "Forbidden 777"), 
	NOT_FOUND(4040, "Not Found 777."), 
	SERVER_ERROR(5001, "Server error during operation. 777");

	private final int code;
	private final String description;

	private Error(final int code, final String description)
	{
		this.code = code;
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public int getCode()
	{
		return code;
	}

	@Override
	public String toString()
	{
		return code + ": " + description;
	}
}
