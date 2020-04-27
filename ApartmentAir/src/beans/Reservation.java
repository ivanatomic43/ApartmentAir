package beans;

public class Reservation {

	private Apartment rentedApartmen = new Apartment();
	private String dateOfReservation;
	private int numberOfNights = 1;
	private double totalPrice;
	private String message;
	private User guest = new User();
	private String reservationStatus;
	
	public Reservation() {}

	/**
	 * @return the rentedApartmen
	 */
	public Apartment getRentedApartmen() {
		return rentedApartmen;
	}

	/**
	 * @param rentedApartmen the rentedApartmen to set
	 */
	public void setRentedApartmen(Apartment rentedApartmen) {
		this.rentedApartmen = rentedApartmen;
	}

	/**
	 * @return the dateOfReservation
	 */
	public String getDateOfReservation() {
		return dateOfReservation;
	}

	/**
	 * @param dateOfReservation the dateOfReservation to set
	 */
	public void setDateOfReservation(String dateOfReservation) {
		this.dateOfReservation = dateOfReservation;
	}

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

	/**
	 * @return the client
	 */
	public User getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(User client) {
		this.client = client;
	}

	/**
	 * @return the reservationStatus
	 */
	public String getReservationStatus() {
		return reservationStatus;
	}

	/**
	 * @param reservationStatus the reservationStatus to set
	 */
	public void setReservationStatus(String reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	
	
}
