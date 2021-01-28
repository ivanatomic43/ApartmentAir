package beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.ApartmentStatus;


public class Apartment {

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	private String type;
	private int numberOfRooms;
	private int numberOfGuests;
	private Location location;
	private ArrayList<Date> dates;
	private Date dateFrom;
	private Date dateTo;
	private String host;
	private List<Integer> comments = new ArrayList<>();
	private String image="";
	private double pricePerNight;
	private ApartmentStatus status;
	private List<String> amenities = new ArrayList<>();
	private List<Reservation> reservations = new ArrayList<>();
	private boolean deleted = false;
	private double rating;
	
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
	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	/**
	 * @param numberOfRooms the numberOfRooms to set
	 */
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	/**
	 * @return the numberOfGuests
	 */
	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	/**
	 * @param numberOfGuests the numberOfGuests to set
	 */
	public void setNumberOfGuests(int numberOfGuests) {
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
	public String getHost() {
		return host;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
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
	public double getPricePerNight() {
		return pricePerNight;
	}

	/**
	 * @param pricePerNight the pricePerNight to set
	 */
	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public ArrayList<Date> getDates() {
		return dates;
	}

	public void setDates(ArrayList<Date> dates) {
		this.dates = dates;
	}

	public ApartmentStatus getStatus() {
		return status;
	}

	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}

	/**
	 * @return the amenities
	 */
	public List<String> getAmenities() {
		return amenities;
	}

	/**
	 * @param amenities the amenities to set
	 */
	public void setAmenities(List<String> amenities) {
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
