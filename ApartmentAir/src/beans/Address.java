package beans;

public class Address {

	

	private String street;
	private String number;
	private String city;
	private int postNumber;
	
	
	public Address() {
		
	}
	
	public Address(String street, String number, String city, int postNumber) {
		
		this.street= street;
		this.number=number;
		this.city = city;
		this.postNumber=postNumber;
	}
	
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the postNumber
	 */
	public int getPostNumber() {
		return postNumber;
	}

	/**
	 * @param postNumber the postNumber to set
	 */
	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}
	
}
