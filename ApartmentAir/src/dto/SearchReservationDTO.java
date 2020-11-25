package dto;

import enums.ReservationStatus;

public class SearchReservationDTO {

	private String username;
	private String sort;
	private ReservationStatus filter;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public ReservationStatus getFilter() {
		return filter;
	}
	public void setFilter(ReservationStatus filter) {
		this.filter = filter;
	}
	
	
	
}
