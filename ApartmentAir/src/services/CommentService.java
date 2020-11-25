package services;

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

import beans.Apartment;
import beans.Comment;

import beans.User;
import dao.ApartmentDAO;
import dao.CommentDAO;
import dao.ReservationDAO;
import dao.UserDAO;
import dto.CommentDTO;
import enums.Role;

@Path("/comment")
public class CommentService {

	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
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
		if(ctx.getAttribute("commentDAO")==null) {
			String contextPath= ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
		if(ctx.getAttribute("reservationDAO")==null) {
			String contextPath= ctx.getRealPath("");
			ctx.setAttribute("reservationDAO", new ReservationDAO(contextPath));
		}
	
	}
	
	
	@GET
	@Path("/getApartmentComments/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentComments(@PathParam("id") int id) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		
		Collection<CommentDTO> commentsList = comments.getApartmentComments(id);
		
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		Collection<User> userList = users.getAllUsers();
		
		for(CommentDTO c: commentsList) {
			for(User u : userList) {
				if(c.getGuestID()==u.getId()) {
					c.setGuestName(u.getName());
					c.setGuestSurname(u.getSurname());
				}
			}
			
		}
		
		if(commentsList.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		
		
		return Response.status(Response.Status.OK).entity(commentsList).build();
		
	}
	
	@POST
	@Path("/leaveComment") //saljem id rezervacije, nadji id apartmana i u njegove komentare strpaj id od novokreiranog komentara
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response leaveComment(Comment newComment) {
		System.out.println("Pronasao leave comment");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		
		if(!loggedUser.getRole().equals(Role.GUEST)) {
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		ReservationDAO reservations = (ReservationDAO)ctx.getAttribute("reservationDAO");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		
		//getting hostID
		String hostUsername="";
		int hostID = 0;
		Collection<Apartment> apartmentList = apartments.getAllApartments();
		for(Apartment a: apartmentList) {
			if(a.getId() == newComment.getApartmentID()) {
				hostUsername = a.getHost();
				System.out.println("Username host for comment section:" +hostUsername);
			}
		}
		
		Collection<User> userList = users.getAllUsers();
		for(User u : userList) {
			if(u.getUsername().equals(hostUsername)) {
				hostID = u.getId();
				System.out.println("ID host for comment section: " + hostID);
			}
		}
		
	   Comment comment =  comments.leaveComment(newComment, hostID, contextPath);
	   
	   
		
		return Response.status(Response.Status.OK).entity(newComment).build();
		
	}
	
	@GET
	@Path("/getAllCommentsHost/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsHost(@PathParam("id") int id) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if(!loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		Collection<CommentDTO> commentsList = comments.getApartmentCommentsHost(id);
		
		//adding apartmentType & guest info
		Collection<Apartment> apartmentList = apartments.getAllApartments();
		Collection<User> userList = users.getAllUsers();
		
		for(CommentDTO c : commentsList) {
			for(Apartment a: apartmentList) {
				if(c.getApartmentID() == a.getId()) {
					c.setApartmentType(a.getType());
				}
			}
		}
		
		for(CommentDTO c: commentsList) {
			for(User u : userList) {
				if(c.getGuestID()==u.getId()) {
					c.setGuestName(u.getName());
					c.setGuestSurname(u.getSurname());
				}
			}
			
		}
		
		
		if(commentsList.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		
		
		return Response.status(Response.Status.OK).entity(commentsList).build();
		
	}
	
	
	@GET
	@Path("/getAllCommentsAdmin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCommentsAdmin() {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if (!loggedUser.getRole().equals(Role.ADMIN))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		
		Collection<CommentDTO> commentsList = comments.getAllCommentsAdmin();
		
		//adding apartmentType & guest info
				Collection<Apartment> apartmentList = apartments.getAllApartments();
				Collection<User> userList = users.getAllUsers();
				
				for(CommentDTO c : commentsList) {
					for(Apartment a: apartmentList) {
						if(c.getApartmentID() == a.getId()) {
							c.setApartmentType(a.getType());
						}
					}
				}
				
				for(CommentDTO c: commentsList) {
					for(User u : userList) {
						if(c.getGuestID()==u.getId()) {
							c.setGuestName(u.getName());
							c.setGuestSurname(u.getSurname());
						}
					}
					
				}
		
		if(commentsList.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		
		
		return Response.status(Response.Status.OK).entity(commentsList).build();
		
	}
	
	
	@PUT
	@Path("/approveComment/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveComment(@PathParam("id") int id) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if (!loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		String contextPath = ctx.getRealPath("");
		
		boolean approved = comments.approveComment(id, contextPath);
		
		if(!approved)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		return Response.status(Response.Status.OK).build();
	}
	
	@PUT
	@Path("/declineComment/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response declineComment(@PathParam("id") int id) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.FORBIDDEN).build();
		if (!loggedUser.getRole().equals(Role.HOST))
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		String contextPath = ctx.getRealPath("");
		

		boolean declined = comments.declineComment(id, contextPath);
		
		if(!declined)
			return Response.status(Response.Status.BAD_REQUEST).build();
		
		return Response.status(Response.Status.OK).build();
		
		
		
	}
	
}
