package beans;

public class Amenity {

	

	private int id;
	public Amenity(int id, String name) {
		super();
		this.id = id;
		this.name = name;
		
	}

	
	private String name;
	private boolean isRemoved=false;
	
	public boolean isRemoved() {
		return isRemoved;
	}


	public void setRemoved(boolean isRemoved) {
		this.isRemoved = isRemoved;
	}


	public Amenity() {}
	
	
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
