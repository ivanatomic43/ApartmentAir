package services;

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

import beans.User;
import dao.ApartmentDAO;
import dao.UserDAO;
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
	
	
	
}
