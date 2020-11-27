package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import enums.ApartmentStatus;

public class SearchApartmentDTO {

	private Date dateFrom;
	private Date dateTo;
	private String location;
	private String sort;
	
	// advanced search
	private Integer guestsMax;
	private Integer roomsMin;
	private Integer roomsMax;
	private Double priceMin;
	private Double priceMax;
	
	private String type;
	private ApartmentStatus status;
	
	private List<String> amenities  = new ArrayList<>();
	
	
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Integer getGuestsMax() {
		return guestsMax;
	}
	public void setGuestsMax(Integer guestsMax) {
		this.guestsMax = guestsMax;
	}
	public Integer getRoomsMin() {
		return roomsMin;
	}
	public void setRoomsMin(Integer roomsMin) {
		this.roomsMin = roomsMin;
	}
	public Integer getRoomsMax() {
		return roomsMax;
	}
	public void setRoomsMax(Integer roomsMax) {
		this.roomsMax = roomsMax;
	}
	public Double getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}
	public Double getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(Double priceMax) {
		this.priceMax = priceMax;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ApartmentStatus getStatus() {
		return status;
	}
	public void setStatus(ApartmentStatus status) {
		this.status = status;
	}
	public List<String> getAmenities() {
		return amenities;
	}
	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}
	
	
	
	
}
