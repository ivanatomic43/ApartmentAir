package beans;

public class Comment {

	private User guest = new User();
	private Apartment apartmentComment = new Apartment();
	private String text;
	private double rating=0;
	
	public Comment() {}

	/**
	 * @return the guest
	 */
	public User getGuest() {
		return guest;
	}

	/**
	 * @param guest the guest to set
	 */
	public void setGuest(User guest) {
		this.guest = guest;
	}

	/**
	 * @return the apartmentComment
	 */
	public Apartment getApartmentComment() {
		return apartmentComment;
	}

	/**
	 * @param apartmentComment the apartmentComment to set
	 */
	public void setApartmentComment(Apartment apartmentComment) {
		this.apartmentComment = apartmentComment;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
	
	
	
}
