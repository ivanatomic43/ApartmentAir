package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;
import dto.CommentDTO;
import enums.CommentStatus;

public class CommentDAO {

	
private HashMap<Integer, Comment> comments =new HashMap<>();
	
	public CommentDAO() {
		
	}
	
	public CommentDAO(String contextPath) {

		loadComments(contextPath);
	}
	
	public void loadComments(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/comments.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Comment[] CommentArray=objectMapper.readValue(file, Comment[].class);
			
			for(Comment com:CommentArray) {
				comments.put(com.getId(), com);
			}
			System.out.println("Comments: " + comments.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveComments(String contextPath) {
		try {
			
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Comment> comArray=new ArrayList<>();
			
			for(Comment com: comments.values()) {
				comArray.add(com);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/comments.json"), comArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int generateNewId() {
		int id=1;
		for(Comment c:comments.values()) {
			if(c.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public Collection<CommentDTO> getApartmentComments(int id){
		
		ArrayList<CommentDTO> retVal = new ArrayList<>();
		CommentDTO dto = new CommentDTO();
		
		for(Comment c:comments.values()) {
			if(c.getApartmentID() == id && c.getStatus().equals(CommentStatus.APPROVED)) {
				dto.setId(c.getId());
				dto.setApartmentID(c.getApartmentID());
				dto.setGuestID(c.getGuestID());
				dto.setRating(c.getRating());
				dto.setText(c.getText());
				dto.setStatus(c.getStatus());
				retVal.add(dto);
			}
		}
		
		Collections.reverse(retVal);
		return retVal;
	}
	
	public Comment leaveComment(Comment newComment, int hostID, String contextPath) {
		
		System.out.println("USAO U LEave comment dao");
		
		int id = generateNewId();
		newComment.setId(id);
		newComment.setHostID(hostID);
		newComment.setStatus(CommentStatus.WAITING_FOR_APPROVAL);
		
		comments.put(newComment.getId(), newComment);
		System.out.println("putt comments");
		saveComments(contextPath);
		
		return newComment;
	}
	
	public Collection<CommentDTO> getApartmentCommentsHost(int id){
		
		ArrayList<CommentDTO> retVal = new ArrayList<>();
		CommentDTO dto = new CommentDTO();
		
		for(Comment c:comments.values()) {
			if(c.getHostID() == id) {
				dto.setId(c.getId());
				dto.setGuestID(c.getGuestID());
				dto.setApartmentID(c.getApartmentID());
				dto.setRating(c.getRating());
				dto.setStatus(c.getStatus());
				dto.setText(c.getText());
				
				
				
				retVal.add(dto);
			}
		}
		
		Collections.reverse(retVal);
		return retVal;
	}
	
	public Collection<CommentDTO> getAllCommentsAdmin(){
		
		ArrayList<CommentDTO> retVal = new ArrayList<>();
		CommentDTO dto = new CommentDTO();
		
		for(Comment c:comments.values()) {
			
				dto.setId(c.getId());
				dto.setGuestID(c.getGuestID());
				dto.setApartmentID(c.getApartmentID());
				dto.setRating(c.getRating());
				dto.setStatus(c.getStatus());
				dto.setText(c.getText());
				
				
				
				retVal.add(dto);
			
		}
		
		Collections.reverse(retVal);
		return retVal;
	}

	public boolean approveComment(int id, String contextPath) {
		
		boolean approved = false;
		for(Comment c : comments.values()) {
			if(c.getId() == id && c.getStatus().equals(CommentStatus.WAITING_FOR_APPROVAL)) {
				c.setStatus(CommentStatus.APPROVED);
				approved = true;
			}
		}
		
		saveComments(contextPath);
		return approved;
	}
	
	public boolean declineComment(int id, String contextPath) {
		
		boolean declined = false;
		for(Comment c : comments.values()) {
			if(c.getId() == id && c.getStatus().equals(CommentStatus.WAITING_FOR_APPROVAL)) {
				c.setStatus(CommentStatus.DENIED);
				declined = true;
			}
		}
		saveComments(contextPath);
		return declined;
	}
}
