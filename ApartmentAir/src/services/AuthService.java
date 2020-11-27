package services;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;

import beans.User;
import dao.UserDAO;

@Path("/auth")
public class AuthService {

	@Context
	ServletContext ctx;
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
	// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
	// Inicijalizacija treba da se obavi samo jednom

		if(ctx.getAttribute("usersDAO")==null) {
		String contextPath=ctx.getRealPath("");
		ctx.setAttribute("usersDAO", new UserDAO(contextPath));
		}
	}
	
	@POST
	@Path("/signUp")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(User u, @Context HttpServletRequest request) {
		if(u==null) {
			
		}
		
		
		UserDAO users=(UserDAO) ctx.getAttribute("usersDAO");
		boolean postojiVec=users.checkUserName(u.getUsername());
		
		if(postojiVec==true) {
			return Response.status(400).build();
		}
		
		String contextPath=ctx.getRealPath("");
		users.addUser(u, contextPath);
		request.getSession().setAttribute("loggedUser", u);
		
		return Response.status(Response.Status.OK).entity(u).build();
	}
	
	@GET
	@Path("/getLogged")
	@Produces(MediaType.APPLICATION_JSON)
	public User getLogged(@Context HttpServletRequest request) {
		return (User)request.getSession().getAttribute("loggedUser");
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(User user, @Context HttpServletRequest request) {
		
		UserDAO usersDAO=(UserDAO)ctx.getAttribute("usersDAO");
		
		
		User u=usersDAO.checkLogin(user.getUsername(), user.getPassword());
		
		if(u!=null) {
			
			request.getSession().setAttribute("loggedUser", u);
			
			//u.setGender("male");
			return u;
			
		}
		
		return null; 
		
		//return null;
	
	}
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return Response.status(200).build();
	}
	
	@GET
	@Path("/testlogin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public  User testLogin(@Context HttpServletRequest request) {
		System.out.println("\n!!!!!!!!!!");
		User retVal = null;
	  retVal =  (User)request.getSession().
			getAttribute("user");
	  if (retVal == null) {
		  System.out.println("\nNije nasao korisnika ulogovanog\n");
		  return null;
	  }
	  System.out.println("Nasao je ulogovanog korisnika: "+retVal.getUsername());
	  return retVal;
	}
	
	
}
