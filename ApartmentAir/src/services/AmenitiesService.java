package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenity;
import config.PathConfig;
import dao.AmenitiesDAO;

@Path("/amenity")
public class AmenitiesService {

	@Context
	ServletContext ctx;
	
	

	@Path("/getAllAmenities")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAmenities() throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Usao u get all amm");
		ArrayList<Amenity> amenities = AmenitiesDAO.readAmenities(ctx.getRealPath("."));
		return Response.status(Response.Status.OK).entity(amenities).build();
	}
	
	
	@Path("/createAmenity")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAmenity(Amenity amenity) throws JsonGenerationException, JsonMappingException, IOException {
		
		int newId = AmenitiesDAO.generateNewId(ctx.getRealPath("."));
		Amenity a = new Amenity(newId, amenity.getName());
		ArrayList<Amenity> amenities = AmenitiesDAO.readAmenities(ctx.getRealPath("."));
		amenities.add(a);
		AmenitiesDAO.writeAmenities(ctx.getRealPath("."),amenities);
		return Response.status(Response.Status.OK).entity(a).build();
	}
	
}
