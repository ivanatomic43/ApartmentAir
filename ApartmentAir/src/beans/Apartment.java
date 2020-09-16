package beans;

import java.util.ArrayList;
import java.util.List;


public class Apartment {

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String type;
	private String numberOfRooms;
	private String numberOfGuests;
	private Location location;
	//private String [] datesForRent ;
	//private List<Integer> datesForRent = new ArrayList<>();
	private String dateForRent;
	private User host;
	private List<Integer> comments = new ArrayList<>();
	//private String [] images;
	private String pricePerNight;
	private String timeToSignUp;
	private String timetoSignOut;
	private String status = "aktivan";
	private List<Amenities> amenities = new ArrayList<>();
	private List<Reservation> reservations = new ArrayList<>();
	
	public Apartment() {}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the numberOfRooms
	 */
	public String getNumberOfRooms() {
		return numberOfRooms;
	}

	/**
	 * @param numberOfRooms the numberOfRooms to set
	 */
	public void setNumberOfRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	/**
	 * @return the numberOfGuests
	 */
	public String getNumberOfGuests() {
		return numberOfGuests;
	}

	/**
	 * @param numberOfGuests the numberOfGuests to set
	 */
	public void setNumberOfGuests(String numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the datesForRent
	 */
/*	public List<Integer> getDatesForRent() {
		return datesForRent;
	}

	
	
	public void setDatesForRent(List<Integer> datesForRent) {
		this.datesForRent = datesForRent;
	}
*/
	public User getHost() {
		return host;
	}

	public String getDateForRent() {
		return dateForRent;
	}

	public void setDateForRent(String dateForRent) {
		this.dateForRent = dateForRent;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(User host) {
		this.host = host;
	}

	/**
	 * @return the comments
	 */
	public List<Integer> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Integer> comments) {
		this.comments = comments;
	}

	/**
	 * @return the pricePerNight
	 */
	public String getPricePerNight() {
		return pricePerNight;
	}

	/**
	 * @param pricePerNight the pricePerNight to set
	 */
	public void setPricePerNight(String pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	/**
	 * @return the timeToSignUp
	 */
	public String getTimeToSignUp() {
		return timeToSignUp;
	}

	/**
	 * @param timeToSignUp the timeToSignUp to set
	 */
	public void setTimeToSignUp(String timeToSignUp) {
		this.timeToSignUp = timeToSignUp;
	}

	/**
	 * @return the timetoSignOut
	 */
	public String getTimetoSignOut() {
		return timetoSignOut;
	}

	/**
	 * @param timetoSignOut the timetoSignOut to set
	 */
	public void setTimetoSignOut(String timetoSignOut) {
		this.timetoSignOut = timetoSignOut;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the amenities
	 */
	public List<Amenities> getAmenities() {
		return amenities;
	}

	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(List<Amenities> amenities) {
		this.amenities = amenities;
	}

	/**
	 * @return the reservations
	 */
	public List<Reservation> getReservations() {
		return reservations;
	}

	/**
	 * @param reservations the reservations to set
	 */
	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
	
	
	
	
}
