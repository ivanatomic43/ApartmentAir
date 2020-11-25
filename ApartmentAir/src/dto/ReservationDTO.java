package dto;

import enums.ReservationStatus;

public class ReservationDTO {

	
	private int id;
	private String apartmentType;
	private int apartmentID;
	private int guestID;
	private String dateOfReservation;
	private int numberOfNights;
	private double totalPrice;
	private String message;
	private String guestName;
	private String guestSurname;
	private String guestUsername;
	
	public String getGuestUsername() {
		return guestUsername;
	}
	public void setGuestUsername(String guestUsername) {
		this.guestUsername = guestUsername;
	}
	private String reservationStatus;
	private String hostUsername;
	private String street;
	private String number;
	private String city;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApartmentID() {
		return apartmentID;
	}
	public void setApartmentID(int apartmentID) {
		this.apartmentID = apartmentID;
	}
	public int getGuestID() {
		return guestID;
	}
	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}
	public String getApartmentType() {
		return apartmentType;
	}
	public void setApartmentType(String apartmentType) {
		this.apartmentType = apartmentType;
	}
	public String getDateOfReservation() {
		return dateOfReservation;
	}
	public void setDateOfReservation(String dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}
	public int getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public String getHostUsername() {
		return hostUsername;
	}
	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}
	
	
	
	
	
	
	
	
	
}
