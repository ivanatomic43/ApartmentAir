package beans;

import java.util.Date;

import enums.ReservationStatus;

public class Reservation {

	private int id;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}

	private int apartmentID;
    private Date dateOfReservation;
	private int numberOfNights;
	private double totalPrice;
	private String message;
	private int guestID;
	private ReservationStatus reservationStatus;
	private String hostUsername;
	
	
	public String getHostUsername() {
		return hostUsername;
	}


	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}


	public int getApartmentID() {
		return apartmentID;
	}


	public void setApartmentID(int apartmentID) {
		this.apartmentID = apartmentID;
	}


	public Date getDateOfReservation() {
		return dateOfReservation;
	}


	public void setDateOfReservation(Date dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}


	public int getGuestID() {
		return guestID;
	}


	public void setGuestID(int guestID) {
		this.guestID = guestID;
	}


	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}

	
	public Reservation() {}


	/**
	 * @return the numberOfNights
	 */
	public int getNumberOfNights() {
		return numberOfNights;
	}

	/**
	 * @param numberOfNights the numberOfNights to set
	 */
	public void setNumberOfNights(int numberOfNights) {
		this.numberOfNights = numberOfNights;
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	
	
}
