package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenity;

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
	
	@Path("/editAmenity")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAmenity(Amenity amenity) throws JsonGenerationException, JsonMappingException, IOException {
		
		Amenity newAm = new Amenity(amenity.getId(), amenity.getName()); //saljem novu ukucanu izmenu ovde
	
		ArrayList<Amenity> amenities = AmenitiesDAO.readAmenities(ctx.getRealPath("."));
		
		for(Amenity oldAm : amenities) {
			if(oldAm.getId() == newAm.getId()) {
				oldAm.setName(newAm.getName());
			
				AmenitiesDAO.writeAmenities(ctx.getRealPath("."), amenities);
				return Response.status(200).build();
			}
			
		}
		
		
		return Response.status(Response.Status.NOT_FOUND).build();		
	}
	
	@Path("/removeAmenity/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeAmenity(@PathParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println("Usao u delete");
		ArrayList<Amenity> amenities = AmenitiesDAO.readAmenities(ctx.getRealPath("."));
		
		amenities = amenities.stream().filter(a -> !(a.getId() == id))
				.collect(Collectors.toCollection(ArrayList::new));
		AmenitiesDAO.writeAmenities(ctx.getRealPath("."),amenities);
		
		
		return Response.status(Response.Status.OK).build();
	}
}
