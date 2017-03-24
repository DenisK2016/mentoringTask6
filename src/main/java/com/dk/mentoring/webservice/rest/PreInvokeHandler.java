package com.dk.mentoring.webservice.rest;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxrs.ext.RequestHandler;
import org.apache.cxf.jaxrs.model.ClassResourceInfo;
import org.apache.cxf.message.Message;
import org.springframework.stereotype.Repository;

import com.dk.mentoring.webservice.rest.exceptions.Error;
import com.dk.mentoring.webservice.rest.response.ResponseCreator;


@Repository
public class PreInvokeHandler implements RequestHandler
{

	int count = 0;

	private boolean validate(final String ss_id)
	{
		count++;
		System.out.println("SessionID: " + ss_id);
		if (count == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public Response handleRequest(final Message message, final ClassResourceInfo arg1)
	{
		final Map<String, List<String>> headers = CastUtils.cast((Map<?, ?>) message.get(Message.PROTOCOL_HEADERS));

		if (headers.get("ss_id") != null && validate(headers.get("ss_id").get(0)))
		{
			return null;
		}
		else
		{
			return ResponseCreator.error(401, Error.NOT_AUTHORIZED.getCode(), headers.get("version").get(0));
		}

	}
}
