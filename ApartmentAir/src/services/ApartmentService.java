package services;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.Amenity;
import beans.Apartment;
import beans.Reservation;
import beans.User;
import dao.AmenitiesDAO;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDTO;
import enums.Role;

@Path("/apartment")
public class ApartmentService {
	
	
	@Context
	ServletContext ctx;
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("apartmentDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
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
	
	
	@POST
	@Path("/createApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createApartment(Apartment newApp, @Context HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		User user = (User)request.getSession().getAttribute("loggedUser");
		System.out.println(user.getUsername());
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		newApp.setHost(user.getUsername());
		String contextPath = ctx.getRealPath("");
		Apartment apartment = apartments.addApartment(newApp, contextPath);
		if(apartment == null) {
			System.out.println("Usao u apartment null");
			return null;
		}
		
		users.addApartmentForRent(contextPath, apartment.getId(), user.getId());
		
		return Response.status(Response.Status.OK).entity(apartment).build();
		
		
	}
	
	@PUT
	@Path("/editApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editApartment(Apartment newApp, @Context HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {
		
		User user = (User)request.getSession().getAttribute("loggedUser");
		String contextPath = ctx.getRealPath("");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		Collection<Apartment> apartmentList = apartments.getAllApartmentsAdmin();
		
		for(Apartment a: apartmentList) {
			if(a.getId() == newApp.getId()) {
				a.setType(newApp.getType());
				a.setPricePerNight(newApp.getPricePerNight());
				a.setNumberOfGuests(newApp.getNumberOfGuests());
				a.setNumberOfRooms(newApp.getNumberOfRooms());
				apartments.saveApartments(contextPath);
				return Response.status(Response.Status.OK).build();
				
			}
		}
		
		return Response.status(Response.Status.NOT_FOUND).build();
		
		
	}
	
	
	@GET
	@Path("/getApartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartment(@PathParam("id") int id){
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		
		Apartment apartment = apartments.getApartmentById(id);
		
		if(apartment== null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		return Response.status(Response.Status.OK).entity(apartment).build();
		
	}
	
	@GET
	@Path("/getAllApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllApartmentsAdmin(@Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<Apartment> apartmentList = apartments.getAllApartmentsAdmin();
		
		
		return Response.status(Response.Status.OK).entity(apartmentList).build();
	
	}
	
	@GET
	@Path("/getAllActiveApartmentsHost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllActiveApartmentsHost(@Context HttpServletRequest request)  {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		System.out.println("Username od hosta:" + loggedUser.getUsername());
		

		if(loggedUser==null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<Apartment> apartmentList = apartments.getAllActiveApartmentsHost(loggedUser.getUsername());
		
		if(!apartmentList.isEmpty())
			return Response.status(Response.Status.OK).entity(apartmentList).build();
		else 
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
	}
	
	
	@GET
	@Path("/getAllInactiveApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllInactiveApartments(@Context HttpServletRequest request){
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		
		if(loggedUser==null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<Apartment> apartmentList = apartments.getAllInactiveApartments(loggedUser.getUsername());
		
		if(!apartmentList.isEmpty())
			return Response.status(Response.Status.OK).entity(apartmentList).build();
		else 
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
	}
	
	@GET
	@Path("/getAllActiveApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllActiveApartments(@Context HttpServletRequest request)  {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		
		
		
		Collection<Apartment> apartmentList = apartments.getAllActiveApartments();
		
		if(!apartmentList.isEmpty())
			return Response.status(Response.Status.OK).entity(apartmentList).build();
		else 
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
	}
	

	
	@PUT
	@Path("/makeActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeActive(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		String contextPath = ctx.getRealPath("");
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
	
		if(!loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		boolean done = apartments.makeApartmentActive(id);
		apartments.saveApartments(contextPath);
		
		if(!done) {
			return Response.status(404).build();
		}
		 
		return Response.status(200).build();
	}
	
	@GET
	@Path("/getApartmentAmenities/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentAmenities(@PathParam("id") int id)  {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		
		List<String> amenities = apartments.getApartmentAmenities(id);
		
		if(amenities == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		
		
		return Response.status(Response.Status.OK).entity(amenities).build();
	
		
		
	}
	
	@GET
	@Path("/getApartmentDates/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentDates(@PathParam("id") int id) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		
		ArrayList<Date> dates = apartments.getApartmentDates(id);
		
		if(dates.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		return Response.status(Response.Status.OK).entity(dates).build();
	}
	
	@Path("/deleteApartment/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeApartment(@PathParam("id") int id, @Context HttpServletRequest request)  {
		System.out.println("usao u delete");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		String contextPath = ctx.getRealPath("");
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		
		Collection<Reservation> reservationList = reservations.getAllReservationsA();
		
		
		boolean isReserved = false;
		
		for(Reservation r : reservationList) {
			if(r.getApartmentID() == id) {
				isReserved = true;
				return Response.status(Response.Status.BAD_REQUEST).entity(isReserved).build();
			}
		}
		
		Collection<Apartment> apartmentList = apartments.getAllApartments();
		for(Apartment a : apartmentList) {
			if(a.getId() == id && a.isDeleted() == false) {
				a.setDeleted(true);
			}
		}
		
		 apartments.saveApartments(contextPath);
		 isReserved = true;
		
		return Response.status(Response.Status.OK).entity(isReserved).build();
	}
	
}
