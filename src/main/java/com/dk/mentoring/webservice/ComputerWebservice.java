package com.dk.mentoring.webservice;

import javax.ws.rs.core.Response;

import com.dk.mentoring.entity.Computer;


public interface ComputerWebservice
{

	Response getComputer(String id);

	Response removeComputer(String id);

	Response createComputer(Computer str);

	Response updateComputer(Computer str);
}
