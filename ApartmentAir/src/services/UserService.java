package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.Reservation;
import beans.User;
import dao.ApartmentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.ReservationDTO;
import enums.Role;

@Path("/users")
public class UserService {
		
	@Context
	ServletContext ctx;
	
	
	@PostConstruct
	public void init() {
		if(ctx.getAttribute("usersDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
		if(ctx.getAttribute("apartmentDAO")==null) {
			String contextPath=ctx.getRealPath("");
			ctx.setAttribute("apartmentDAO", new ApartmentDAO(contextPath));
		}
	}
	
	@PUT
	@Path("/editUserProfile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response editUserProfile(User user,@Context HttpServletRequest request) {
	
		
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		
		Collection<User> usersList = users.getAllUsers();
		
		for(User u : usersList) {
			if(u.getId() == user.getId()) {
				u.setPassword(user.getPassword());
				u.setName(user.getName());
				u.setSurname(user.getSurname());
				users.saveUsers(contextPath);
				request.getSession().setAttribute("loggedUser", u);
				return Response.status(Response.Status.OK).entity(u).build();
			}
		}
		
		
		return Response.status(Response.Status.NOT_FOUND).build();
		
		
	}
	 
	@GET
	@Path("/getAllUsers")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<User> getAllUsers(@Context HttpServletRequest request){
		
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		/*if (loggedUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		if (!(loggedUser.getRole().equals("Admin")));
			return Response.status(Response.Status.FORBIDDEN).build();*/
		
		return users.getAllUsers();
	}
	
	@PUT
	@Path("/changeRole/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeRole(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if(loggedUser.getRole().equals(Role.GUEST) || loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		users.changeUserRole(id);
		users.saveUsers(contextPath);
		
	 return Response.status(200).build();
	}
	

	@PUT
	@Path("/blockUnblock/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response blockUnblock(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if(loggedUser.getRole().equals(Role.GUEST) || loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		users.changeUserStatus(id);
		users.saveUsers(contextPath);
		
	 return Response.status(200).build();
	}
	
	
	
	@GET
	@Path("/getAllUsersHost/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllUsersHost(@PathParam("username")String username, @Context HttpServletRequest request){
		
		
		
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		
		if (loggedUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		if (!(loggedUser.getRole().equals(Role.HOST)))
			return Response.status(Response.Status.FORBIDDEN).build();
		
		
		Collection<ReservationDTO> reservationList = reservations.getAllReservationsHost(username);
		
		ArrayList<User> usersList = users.getAllUsersHost(reservationList);
		
		
		
			
		return Response.status(Response.Status.OK).entity(usersList).build();
	}
	
	@POST
	@Path("/createHost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createHost(User u, @Context HttpServletRequest request) {
		if(u==null) {
			System.out.println("User je null");
		}
		
		
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		boolean postojiVec=users.checkUserName(u.getUsername());
		
		if(postojiVec==true) {
			return Response.status(400).build();
		}
		
		String contextPath=ctx.getRealPath("");
		users.addHost(u, contextPath);
		//request.getSession().setAttribute("loggedUser", u);
		
		return Response.status(Response.Status.OK).entity(u).build();
	}
	
}
