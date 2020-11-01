package services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.User;
import dao.ApartmentDAO;
import dao.UserDAO;
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
	public User editUserProfile(User user,@Context HttpServletRequest request) {
	
		System.out.println(user.getId() + user.getName() + user.getSurname()+user.getGender()+user.getPassword());
		UserDAO users = (UserDAO)ctx.getAttribute("usersDAO");
		String contextPath = ctx.getRealPath("");
		
		User temp = users.getUsers().get(user.getId());
		user.setName(temp.getName());
		user.setSurname(temp.getSurname());
		user.setPassword(temp.getPassword());
		user.setGender(temp.getGender());
		
		
		users.editUser(user, contextPath);
		users.saveUsers(contextPath);
		
		return user;
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
	
}
