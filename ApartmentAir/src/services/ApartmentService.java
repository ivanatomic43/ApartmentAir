package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

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
		return null;
	}
	
	@DELETE
	@Path("/deleteApartment/{id}")
	public void deleteApartment(@PathParam("id") int id, @Context HttpServletRequest request) {
		
		
	}
	
	
	
}
