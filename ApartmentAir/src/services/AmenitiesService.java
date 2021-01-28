package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import beans.Apartment;
import beans.User;
import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import enums.Role;

@Path("/amenity")
public class AmenitiesService {

	@Context
	ServletContext ctx;
	

	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("apartmentDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
		if(ctx.getAttribute("amenitiesDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("amenitiesDAO", new AmenitiesDAO(contextPath));
		}
		
		if(ctx.getAttribute("usersDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
		if(ctx.getAttribute("reservationDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
	
	}

	@Path("/getAllAmenities")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllAmenities(@Context HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
	
		AmenitiesDAO amenities = (AmenitiesDAO)ctx.getAttribute("amenitiesDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		
		/*if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();*/
		
		Collection<Amenity> amenitiesList = amenities.getAllAmenities();
		
		
		
		
		return Response.status(Response.Status.OK).entity(amenitiesList).build();
	}
	
	
	@Path("/createAmenity")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAmenity(Amenity amenity, @Context HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");

		AmenitiesDAO amenities = (AmenitiesDAO)ctx.getAttribute("amenitiesDAO");
		String contextPath = ctx.getRealPath("");
		
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Amenity a = amenities.createNewAmenity(amenity, contextPath);
		
		if(a == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
		
		
		return Response.status(Response.Status.OK).entity(a).build();
	}
	
	@Path("/editAmenity")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editAmenity(Amenity amenity, @Context HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		
		Amenity newAm = new Amenity(amenity.getId(), amenity.getName()); //saljem novu ukucanu izmenu ovde
	
		
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");

		AmenitiesDAO amenities = (AmenitiesDAO)ctx.getAttribute("amenitiesDAO");
		String contextPath = ctx.getRealPath("");
		
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Amenity a = amenities.editAmenity(amenity, contextPath);
		
		if(a == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		
		
		
		return Response.status(Response.Status.OK).entity(a).build();		
	}
	
	@Path("/removeAmenity/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeAmenity(@PathParam("id") int id, @Context HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
		
		

		User loggedUser = (User)request.getSession().getAttribute("loggedUser");

		AmenitiesDAO amenities = (AmenitiesDAO)ctx.getAttribute("amenitiesDAO");
		String contextPath = ctx.getRealPath("");
		
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		
		boolean removed  = amenities.removeAmenity(id, contextPath);
		
		if(!removed)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
	
		
		
		return Response.status(Response.Status.OK).build();
	}
}
