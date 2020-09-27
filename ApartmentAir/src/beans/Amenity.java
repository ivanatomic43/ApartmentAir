package beans;

public class Amenity {

	

	private long id;
	public Amenity(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	private String name;
	
	public Amenity() {}
	
	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
	
}
