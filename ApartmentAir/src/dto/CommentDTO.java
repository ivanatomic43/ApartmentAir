package dto;

import enums.CommentStatus;

public class CommentDTO {

	private int id;
	private int guestID;
	private int apartmentID;
	private String apartmentType;
	private String text;
	private String guestName;
	private String guestSurname;
	private double rating;
	private CommentStatus status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGuestID() {
		return guestID;
	}
	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}
	public int getApartmentID() {
		return apartmentID;
	}
	public void setApartmentID(int apartmentID) {
		this.apartmentID = apartmentID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getGuestSurname() {
		return guestSurname;
	}
	public void setGuestSurname(String guestSurname) {
		this.guestSurname = guestSurname;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public CommentStatus getStatus() {
		return status;
	}
	public void setStatus(CommentStatus status) {
		this.status = status;
	}
	
	public String getApartmentType() {
		return apartmentType;
	}
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	
	
	
	
}
