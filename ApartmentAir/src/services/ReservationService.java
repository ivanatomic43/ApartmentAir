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
import javax.ws.rs.core.Response.Status;

import beans.Apartment;
import beans.Reservation;
import beans.User;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDTO;
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
		
		System.out.println("Pronasao newReservation");
		System.out.println("Ap id: " + newRes.getApartmentID());
		System.out.println("Guest id: " + newRes.getGuestID());
		System.out.println("POruka: " + newRes.getMessage());
		System.out.println("Broj nocenja: " + newRes.getNumberOfNights());
		System.out.println("Pocetni datum: " + newRes.getDateOfReservation());
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		if (loggedUser.getRole() != Role.GUEST)
			return Response.status(Response.Status.FORBIDDEN).build();
		
		Apartment apartment = apartments.getApartmentById(newRes.getApartmentID());
		if (apartment == null)
			return Response.status(Response.Status.NOT_FOUND).build();

		
		//checking selected date
		boolean dateValidation = apartments.checkDate(newRes.getApartmentID(), newRes.getDateOfReservation(), newRes.getNumberOfNights());
		if(!dateValidation) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Pick a valid date!").build();
		}
		
		//checking if all selected dates are available
		boolean availableDates = apartments.checkAvailableDates(newRes.getApartmentID(), newRes.getDateOfReservation(), newRes.getNumberOfNights());
		if(!availableDates) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Dates are not available!").build();
		}
		
		double price = apartments.getApartmentPrice(newRes.getApartmentID());
		
		Reservation reservation = reservations.createNewReservation(newRes, contextPath, price);
	
		users.addReservation(contextPath, reservation.getId(), loggedUser.getId());
		
		return Response.status(Response.Status.OK).entity(reservation).build();
	}
	
	@GET
	@Path("/getAllReservationsGuest/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllReservationsGuest(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
	
		if(!loggedUser.getRole().equals(Role.GUEST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<ReservationDTO> reservationList = reservations.getAllReservationsGuest(id);
		
		//view 
		Collection<Apartment> apartmentList = apartments.getAllApartments();
		Collection<User> userList = users.getAllUsers();
		
		for(ReservationDTO dto : reservationList){
				for(Apartment a : apartmentList) {
					if(dto.getApartmentID() == a.getId()) {
						dto.setApartmentType(a.getType());
						dto.setHostUsername(a.getHost());
						dto.setStreet(a.getLocation().getAddress().getStreet());
						dto.setNumber(a.getLocation().getAddress().getNumber());
						dto.setCity(a.getLocation().getAddress().getCity());
					}
				}
		}
		
		for(ReservationDTO dto : reservationList) {
			for(User u : userList) {
				if(dto.getGuestID() == u.getId()) {
					dto.setGuestName(u.getName());
					dto.setGuestSurname(u.getSurname());
				}
			}
			
		}
		
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
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
	
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		Collection<ReservationDTO> reservationList = reservations.getAllReservations();
		
		Collection<Apartment> apartmentList = apartments.getAllApartments();
		Collection<User> userList = users.getAllUsers();
		
		for(ReservationDTO dto : reservationList){
				for(Apartment a : apartmentList) {
					if(dto.getApartmentID() == a.getId()) {
						dto.setApartmentType(a.getType());
						dto.setHostUsername(a.getHost());
						dto.setStreet(a.getLocation().getAddress().getStreet());
						dto.setNumber(a.getLocation().getAddress().getNumber());
						dto.setCity(a.getLocation().getAddress().getCity());
					}
				}
		}
		
		for(ReservationDTO dto : reservationList) {
			for(User u : userList) {
				if(dto.getGuestID() == u.getId()) {
					dto.setGuestName(u.getName());
					dto.setGuestSurname(u.getSurname());
				}
			}
			
		}
		
		
		
		
		
		
		if(reservationList. isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
		return Response.status(Response.Status.OK).entity(reservationList).build();
	}
	
}
