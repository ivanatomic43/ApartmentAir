package services;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import beans.User;
import dao.ApartmentDAO;
import dao.UserDAO;

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
	
	@Path("/editUserProfile")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User editUserProfile(User user, HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException {
	
		System.out.println(user.getId() + user.getName() + user.getSurname()+user.getGender()+user.getPassword());
		
		
		
		
		
		
		
		
		return null;
	}
	
}
