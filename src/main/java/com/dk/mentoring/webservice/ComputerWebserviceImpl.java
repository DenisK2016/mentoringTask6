package com.dk.mentoring.webservice;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.ContextLoader;

import com.dk.mentoring.dao.ComputerDao;
import com.dk.mentoring.entity.Computer;
import com.dk.mentoring.webservice.rest.exceptions.Error;
import com.dk.mentoring.webservice.rest.response.ResponseCreator;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/computerWebservice")
public class ComputerWebserviceImpl implements ComputerWebservice {

	@Autowired
	private ComputerDao computerDao;
	@Context
	private HttpHeaders requestHeaders;

	public ComputerWebserviceImpl() {
		super();
		this.computerDao = (ComputerDao) ContextLoader.getCurrentWebApplicationContext().getBean("computerDao");
	}

	private String getHeaderVersion() {
		final List<String> requestHeader = requestHeaders.getRequestHeader("version");
		return null != requestHeader ? requestHeader.get(0) : "0";
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getComputer(@PathParam("id") final String id) {
		final Computer computer = computerDao.getComputer(Integer.valueOf(id));
		if (computer != null) {
			return ResponseCreator.success("200", computer);
		} else {
			return ResponseCreator.error(404, Error.NOT_FOUND.getCode(), getHeaderVersion());
		}
	}

	@GET
	@Path("/xml/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Computer getXMLComputer(@PathParam("id") final String id) {
		final Computer computer = computerDao.getComputer(Integer.valueOf(id));
		if (computer != null) {
			Computer compWithoutImage = new Computer();
			compWithoutImage.setActive(computer.getActive());
			compWithoutImage.setCreated(computer.getCreated());
			compWithoutImage.setId(computer.getId());
			compWithoutImage.setName(computer.getName());
			return compWithoutImage;
		} else {
			return null;
		}
	}

	@DELETE
	@Path("/{id}")
	@Override
	public Response removeComputer(@PathParam("id") final String id) {
		if (computerDao.delete(Integer.valueOf(id))) {
			return ResponseCreator.success(getHeaderVersion(), "removed");
		} else {
			return ResponseCreator.success(getHeaderVersion(), "no such id");
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Override
	public Response createComputer(@RequestBody final Computer computer) {
		System.out.println("POST");
		final Computer creComputer = computerDao.insert(computer);
		if (creComputer != null) {
			return ResponseCreator.success(getHeaderVersion(), creComputer);
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response updateComputer(@RequestBody final Computer computer) {
		final Computer updComputer = computerDao.update(computer);
		if (updComputer != null) {
			return ResponseCreator.success(getHeaderVersion(), updComputer);
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
		}
	}

	@POST
	@Path("/postimage/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Object createComputer(@Context HttpServletRequest request, @PathParam("id") final String id) throws IOException {
		final Computer computer = computerDao.getComputer(Integer.valueOf(id));

		if (computer != null) {
			computer.setImage(IOUtils.toByteArray(request.getInputStream()));
			computerDao.update(computer);
			return null;
		} else {
			return ResponseCreator.error(500, Error.SERVER_ERROR.getCode(), getHeaderVersion());
		}

	}

	@GET
	@Path("/image/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getImageComputer(@PathParam("id") final String id) throws IOException {
		final Computer computer = computerDao.getComputer(Integer.valueOf(id));
		if (computer != null) {
			File file = new File("image.jpg");
			FileUtils.writeByteArrayToFile(file, computer.getImage());
			ResponseBuilder resp = Response.ok((Object) file);
			return resp.build();
		} else {
			return null;
		}
	}

	public ComputerDao getComputerDAO() {
		return computerDao;
	}

	public void setComputerDAO(final ComputerDao computerDAO) {
		this.computerDao = computerDAO;
	}

}
