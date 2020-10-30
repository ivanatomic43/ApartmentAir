package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;
import enums.Role;

public class UserDAO {

	
private HashMap<Integer, User> users=new HashMap<>();

	public HashMap<Integer, User> getUsers() {
		return users;
	}

	public void setUsers(HashMap<Integer, User> users) {
		this.users = users;
	}

	public UserDAO() {
		
	}
	
	public  UserDAO(String contextPath) {

		loadUsers(contextPath);
	}
	
	public void loadUsers(String contextPath) {
	System.out.println("ucitavanjee: "+contextPath);

		try {
			File file=new File(contextPath+ "/jsonFiles/users.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			
			User[] usersA=objectMapper.readValue(file, User[].class);
			
			for(User u:usersA) {
				users.put(u.getId(), u);
			}
			System.out.println(usersA.length+"a=velicina");
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void addUser(User user,String contextPath) {
		int id=generateNewId();
		user.setId(id);
		user.setRole(Role.GUEST);
		users.put(id, user);
		saveUsers(contextPath);
	}
	
	public int generateNewId() {
		int id=1;
		for(User u:users.values()) {
			if(u.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public void saveUsers(String contextPath) {
		System.out.println("kthhh  "+contextPath);
		try {
			File file=new File(contextPath+"/jsonFiles/users.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<User> usersArray=new ArrayList<>();
			
			for(User u: users.values()) {
				usersArray.add(u);
			}
			
			System.out.println("aaaaa"+usersArray.size());
			objectMapper.writeValue(new File(contextPath + "/jsonFiles/users.json"),usersArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public boolean checkUserName(String username) {
		
		for(User u:users.values()) {
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;

	}
	
	public User checkLogin(String username,String password) {
		
		for(User u:users.values()) {
			if(u.getUsername().equals(username)) {
				if(u.getPassword().equals(password)) {
					return u;
				}
			}
		}
		
		return null;
		
	}
	
	public void addApartmentForRent(String contextPath, int appId, int userId) {
		System.out.println("Usao u addapartmentforrentDAO");
		User user = users.get(userId);
		user.getApartmentsForRent().add(appId);
		users.replace(user.getId(), user);
		
		saveUsers(contextPath);
		
		
	}

	public void editUser(User user, String contextPath) {
		
		users.replace(user.getId(), user);
		saveUsers(contextPath);
	}
	
	public ArrayList<User> getAllUsers(){
		ArrayList<User> ret = new ArrayList<>();
		for(User u: users.values()) {
			ret.add(u);
		}
		return ret;
	}
	
	public User getUserById(int id) {
		
	     
		
	     for(User u : users.values()) {
	    	 if(u.getId() == id){
	    		return u;
	    	 }
	     }
	     return null;
	}
	
	
	
	
	
	
	
	
	
}

	


