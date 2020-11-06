package services;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Comment;

import beans.User;
import dao.ApartmentDAO;
import dao.CommentDAO;
import dao.UserDAO;

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
	
	}
	
	
	@GET
	@Path("/getApartmentComments/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getApartmentComments(@PathParam("id") int id) {
		
		User loggedUser = (User) request.getSession().getAttribute("loggedUser");
		if (loggedUser == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();
		
		CommentDAO comments = (CommentDAO)ctx.getAttribute("commentDAO");
		
		Collection<Comment> commentsList = comments.getApartmentComments(id);
		
		if(commentsList.isEmpty()) {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
		
		
		return Response.status(Response.Status.OK).entity(commentsList).build();
		
	}
	
	
}
