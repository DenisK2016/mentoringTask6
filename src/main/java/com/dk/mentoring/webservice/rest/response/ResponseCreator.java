package com.dk.mentoring.webservice.rest.response;

import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.cedarsoftware.util.io.JsonWriter;
import com.dk.mentoring.entity.Computer;

public class ResponseCreator {

	public static Response error(final int status, final int errorCode, final String version) {
		final Response.ResponseBuilder response = Response.status(status);
		response.header("version", version);
		response.header("errorcode", errorCode);
		response.entity("none");
		return response.build();
	}

	public static Response success(final String version, final Object object) {

		if (object instanceof Computer) {
			final Computer computer = (Computer) object;
			final JSONObject jsonObject = new JSONObject();

			jsonObject.put("id(Integer)", computer.getId());
			jsonObject.put("name(String)", computer.getName());
			jsonObject.put("created(Date)", computer.getCreated());
			jsonObject.put("active(Boolean)", computer.getActive());
			jsonObject.put("image", null != computer.getImage() ? "Yes" : "No");

			final String result = JsonWriter.formatJson(jsonObject.toString());
			return Response.status(200).entity(result).build();
		}

		final Response.ResponseBuilder response = Response.ok();
		response.header("version", version);
		if (object != null) {
			response.entity(object);
		} else {
			response.entity("none");
		}
		return response.build();
	}
}
