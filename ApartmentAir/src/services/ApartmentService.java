package services;

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
import beans.User;
import dao.ApartmentDAO;
import dao.UserDAO;

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
	
	}
	
	
	@POST
	@Path("/createApartment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment createApartment(Apartment newApp, @Context HttpServletRequest request) {
		System.out.println("Usao u create");
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
		
		return apartment;
		
		
	}
	
	@GET
	@Path("/getApartment/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Apartment getApartmentById(@PathParam("id") int id){
		return null;
	}
	
	@GET
	@Path("/getAllApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllApartments(@Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		
		return apartments.getAllApartments();
	
	}
	
	@GET
	@Path("/getAllActiveApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllActiveApartments(@Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		System.out.println("Username od hosta:" + loggedUser.getUsername());
		return apartments.getAllActiveApartments(loggedUser.getUsername());
		
	}
	
	
	@GET
	@Path("/getAllInactiveApartments")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Apartment> getAllInactiveApartments(@Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		User loggedUser = (User)request.getSession().getAttribute("loggedUser");
		System.out.println("Username od hosta:" + loggedUser.getUsername());
		return apartments.getAllInactiveApartments(loggedUser.getUsername());
		
		
		
		
		
		
	}
	
	
	@DELETE
	@Path("/deleteApartment/{id}")
	public void deleteApartment(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		
	}
	
	@PUT
	@Path("/makeActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeActive(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		ApartmentDAO apartments = (ApartmentDAO)ctx.getAttribute("apartmentDAO");
		String contextPath = ctx.getRealPath("");
		
		boolean done = apartments.makeApartmentActive(id);
		apartments.saveApartments(contextPath);
		
		if(!done) {
			return Response.status(404).build();
		}
		 
		return Response.status(200).build();
	}
	
	
}
