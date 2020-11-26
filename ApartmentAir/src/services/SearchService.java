package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.model.internal.RankedComparator.Order;

import beans.Apartment;
import beans.Reservation;
import beans.User;
import comparators.ApartmentPriceComparator;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDTO;
import dto.SearchApartmentDTO;
import dto.SearchReservationDTO;
import enums.OrderComparator;
import enums.Role;

@Path("/search")
public class SearchService {

	
	@Context
	ServletContext ctx;
	

	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("usersDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
		
		if(ctx.getAttribute("apartmentDAO")==null) {
			String contexxtPath=ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contexxtPath));
		}
		if(ctx.getAttribute("reservationDAO")==null) {
			String contextPath= ctx.getRealPath("");
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
		
	}
	
	@POST
	@Path("/searchUsersAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchUsersAdmin(User user, @Context HttpServletRequest request) {
		
		
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		

		
		if(loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if(!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		
		Collection<User> usersList = users.searchUsersAdmin(user);
		
		if(usersList.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
		
		
		return Response.status(Response.Status.OK).entity(usersList).build();
	}
	
	@POST
	@Path("/basicSearch")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response apartmentSearch(SearchApartmentDTO apartment, @Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		
		
		if(loggedUser == null)
			apartmentList = apartments.apartmentSearch(apartment);
		else if(loggedUser.getRole().equals(Role.ADMIN))
			apartmentList = apartments.apartmentSearchAdmin(apartment);
		else if(loggedUser.getRole().equals(Role.GUEST))
			apartmentList = apartments.apartmentSearch(apartment);
		else if(loggedUser.getRole().equals(Role.HOST))
			apartmentList = apartments.apartmentSearchAdmin(apartment);
		
		if(apartmentList.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
			//sorting
		
			apartmentList = apartments.sortApartments(apartmentList, apartment.getSort());
	
			return Response.status(Response.Status.OK).entity(apartmentList).build();
		
	}
	
	
	@POST
	@Path("/searchReservations")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchReservations(SearchReservationDTO reservation, @Context HttpServletRequest request) {
		
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
	
		
		if(loggedUser.getRole().equals(Role.ADMIN))
			reservationList = reservations.reservationSearchAdmin(reservation);
		else if(loggedUser.getRole().equals(Role.HOST)) {
			String hostUsername = loggedUser.getUsername();
			reservationList = reservations.reservationSearchHost(reservation, hostUsername);
		}
		
			//sorting
		
			reservationList = reservations.sortReservations(reservationList, reservation.getSort());
	
			reservationList = reservations.filterReservations(reservationList, reservation.getFilter());
		
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
					dto.setGuestUsername(u.getUsername());
				}
			}
			
		}
			if(reservationList.isEmpty())
			return Response.status(Response.Status.NO_CONTENT).build();
		
		
		
		return Response.status(Response.Status.OK).entity(reservationList).build();
		

			
		
	}
	
	
}
