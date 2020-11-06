package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Comment;

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
	
	public Collection<Comment> getApartmentComments(int id){
		
		ArrayList<Comment> retVal = new ArrayList<>();
	
		
		for(Comment c:comments.values()) {
			if(c.getApartmentID() == id) {
				retVal.add(c);
			}
		}
		
		Collections.reverse(retVal);
		return retVal;
	}
	
	
	
	
}
