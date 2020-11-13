package services;


import java.util.List;
import java.util.Date;
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

import beans.Apartment;
import beans.Reservation;
import beans.User;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import enums.Role;




@Path("/reservation")
public class ReservationService {
			
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
	@Path("/createNewReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewReservation(Reservation newRes, @Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		if (loggedUser.getRole() != Role.GUEST)
			return Response.status(Response.Status.FORBIDDEN).build();
		
		Apartment apartment = apartments.getApartmentById(newRes.getApartmentID());
		if (apartment == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		
		
		
		
		
		
		
		return null;
	}
	
	@GET
	@Path("/getAllReservationsGuest/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReservationsGuest(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
	
		if(!loggedUser.getRole().equals(Role.GUEST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<Reservation> reservationList = reservations.getAllReservationsGuest(id);
		
		if(reservationList. isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
		return Response.status(Response.Status.OK).entity(reservationList).build();
	}
	
	@GET
	@Path("/getAllReservationsAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReservationsAdmin(@Context HttpServletRequest request) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
	
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<Reservation> reservationList = reservations.getAllReservations();
		
		if(reservationList. isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
		return Response.status(Response.Status.OK).entity(reservationList).build();
	}
	
}
