package beans;

import java.util.ArrayList;
import java.util.List;

public class User {

	//for all users
	private int id;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String gender;
	private String role;
	
	private List<Integer> apartmentsForRent = new ArrayList<>(); //host
	private List<Integer> rentedApartments = new ArrayList<>(); //guest
	private List<Integer> reservationList = new ArrayList<>(); //guest
	
	
	

	public User() {
		this.username="";
		this.password="";
		this.name="";
		this.surname="";
		this.role="Guest";
		this.gender="";
		
	}
	
	
	public User(String username, String password) {
		
		super();
		this.username = username;
		this.password = password;
		this.name="";
		this.surname="";
		this.role="Guest";
		this.gender="";
	}
	
	
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}


	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}


	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}


	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}


	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}


	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}


	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}


	
	public List<Integer> getApartmentsForRent() {
		return apartmentsForRent;
	}

	public void setApartmentsForRent(List<Integer> apartmentsForRent) {
		this.apartmentsForRent = apartmentsForRent;
	}

	public List<Integer> getRentedApartments() {
		return rentedApartments;
	}


	
	public void setRentedApartments(List<Integer> rentedApartments) {
		this.rentedApartments = rentedApartments;
	}


	
	public List<Integer> getReservationList() {
		return reservationList;
	}


	
	public void setReservationList(List<Integer> reservationList) {
		this.reservationList = reservationList;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
	
	
	
}
