package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import beans.Apartment;
import beans.User;
import comparators.ApartmentDateComparator;
import comparators.ApartmentGuestComparator;
import comparators.ApartmentPriceComparator;
import comparators.ApartmentRoomComparator;
import dto.ReservationDTO;
import dto.SearchApartmentDTO;
import beans.Amenity;
import enums.ApartmentStatus;
import enums.OrderComparator;
import enums.ReservationStatus;

public class ApartmentDAO {

	private HashMap<Integer, Apartment> apartments=new HashMap<>();
	
	public ApartmentDAO() {
		
	}
	
	public ApartmentDAO(String contextPath) {

		loadApartments(contextPath);
	}
	
	public void loadApartments(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/apartments.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Apartment[] apartmentArray=objectMapper.readValue(file, Apartment[].class);
			
			for(Apartment app:apartmentArray) {
				apartments.put(app.getId(), app);
			}
			System.out.println("Apartments: " + apartments.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveApartments(String contextPath) {
		try {
			
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Apartment> appArray=new ArrayList<>();
			
			for(Apartment app: apartments.values()) {
				appArray.add(app);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/apartments.json"), appArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int generateNewId() {
		int id=1;
		for(Apartment a:apartments.values()) {
			if(a.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public Apartment addApartment(Apartment newApartment, String contextPath) {
		
		int id = generateNewId();
		newApartment.setId(id);
		newApartment.setStatus(ApartmentStatus.INACTIVE);
		newApartment.setComments(new ArrayList<>());
		newApartment.setReservations(new ArrayList<>());
		
		ArrayList<Date> dates = newApartment.getDates();
		Date startDate = dates.get(0);
		Date endDate = dates.get(dates.size() - 1);
		
		System.out.println(startDate);
		System.out.println(endDate);
		
		newApartment.setDateFrom(startDate);
		newApartment.setDateTo(endDate);
		
		apartments.put(newApartment.getId(), newApartment);
		
		saveApartments(contextPath);
		
		return newApartment;
	}
	

	public Collection<Apartment> getAllApartments(){
		ArrayList<Apartment> retVal=new ArrayList<>();
		for(Apartment a:apartments.values()) {
			
				retVal.add(a);
			
		}
		Collections.reverse(retVal);
		return retVal;
	}
	

	public Collection<Apartment> getAllActiveApartmentsHost(String username){
		ArrayList<Apartment> retVal=new ArrayList<>();
		
		for(Apartment a : apartments.values()) {
			
			if(a.getStatus().equals(ApartmentStatus.ACTIVE) && a.getHost().equals(username))
				retVal.add(a);
			
		}
		Collections.reverse(retVal);
		
		return retVal;
		
	}
	
	
	public Collection<Apartment> getAllInactiveApartments(String username){
		ArrayList<Apartment> retVal=new ArrayList<>();
		for(Apartment a:apartments.values()) {
			 if(a.getStatus().equals(ApartmentStatus.INACTIVE) && a.getHost().equals(username))
				retVal.add(a);
			
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public Collection<Apartment> getAllActiveApartments(){
		ArrayList<Apartment> retVal=new ArrayList<>();
		
		for(Apartment a : apartments.values()) {
			
			if(a.getStatus().equals(ApartmentStatus.ACTIVE))
				retVal.add(a);
			
		}
		Collections.reverse(retVal);
		
		return retVal;
		
	}
	
	
	public boolean makeApartmentActive( int id) {
		
		for(Apartment a : apartments.values()) {
			if((a.getId() == id) && a.getStatus().equals(ApartmentStatus.INACTIVE)) {
				a.setStatus(ApartmentStatus.ACTIVE);
				return true;
				
			}
		}
		 return false;
		
	}
	
	public Apartment getApartmentById(int id) {
		
		 for(Apartment a : apartments.values()) {
			 if(a.getId() == id) {
				 return a;
			 }
		 }
		
		return null;
		
	}
	
	public List<String> getApartmentAmenities(int id){
		
		List<String> amenities = new ArrayList<>();
		
		for(Apartment a : apartments.values()) {
			if(a.getId() == id) {
				amenities = a.getAmenities();
				
				return amenities;
			}
		}
		
		
		
		return null;
	}
	
	public ArrayList<Date> getApartmentDates(int id){
		
		ArrayList<Date> dates = new ArrayList<>();
		
		for(Apartment a: apartments.values()) {
			if(a.getId() == id) {
				dates = a.getDates();
				return dates;
			}
		}
		
		
		return null;
	}
	
	public boolean checkDate(int id, Date startDate, int nights) {
		
		boolean validDate= true;
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.DATE, nights);
		Date endDate = c.getTime();
		
		
		for(Apartment a: apartments.values()) {
			if(a.getId() == id) {
				System.out.println(a.getDateFrom());
				System.out.println(a.getDateTo());
				//startDate is before dateFrom? or endDate is after dateTo
				if(startDate.before(a.getDateFrom()) || endDate.after(a.getDateTo())) {
					validDate = false;
					
				}
				
				
			}
			
			
		}
		
		
		return validDate;
	
	}
	
	public boolean checkAvailableDates(int id, Date startDate, int nights) {
		
	
		for(Apartment a: apartments.values()) {
			if(a.getId() == id) {
				 

				for( int i = 1; i <= nights; i ++) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(startDate);
					c1.add(Calendar.DATE, i);
					c1.set(Calendar.HOUR_OF_DAY, 0);
					c1.set(Calendar.MINUTE, 0);
					c1.set(Calendar.SECOND, 0);
					c1.set(Calendar.MILLISECOND, 0); 
					
					boolean availableDates = a.getDates().stream().anyMatch(date -> {
						Calendar c2 = Calendar.getInstance();
						c2.setTime(date);
						c2.set(Calendar.HOUR_OF_DAY, 0);
						c2.set(Calendar.MINUTE, 0);
						c2.set(Calendar.SECOND, 0);
						c2.set(Calendar.MILLISECOND, 0);
						return c1.compareTo(c2) == 0;
					});
					if (!availableDates) //date is not available
						return availableDates;
				}
				
				
				for (int i = 1; i <= nights; i++) {
					Calendar c1 = Calendar.getInstance();
					c1.setTime(startDate);
					c1.add(Calendar.DATE, i);
					c1.set(Calendar.HOUR_OF_DAY, 0);
					c1.set(Calendar.MINUTE, 0);
					c1.set(Calendar.SECOND, 0);
					c1.set(Calendar.MILLISECOND, 0);

					ArrayList<Date> newDates = a.getDates().stream().filter(date -> {
						Calendar c2 = Calendar.getInstance();
						c2.setTime(date);
						c2.set(Calendar.HOUR_OF_DAY, 0);
						c2.set(Calendar.MINUTE, 0);
						c2.set(Calendar.SECOND, 0);
						c2.set(Calendar.MILLISECOND, 0);
						return c1.compareTo(c2) != 0;
					}).collect(Collectors.toCollection(ArrayList::new));
					a.setDates(newDates);
				}
				
			
				
			}
		}
		return true;
	}		
	
	public double getApartmentPrice(int id) {
		
		double price = 0;
		
		for(Apartment a : apartments.values()) {
			if(a.getId() == id) {
				price = a.getPricePerNight();
				return price;
			}
		}
		
		
		return price;
		
	}
	
	
	//only active apartments
	public ArrayList<Apartment> apartmentSearch(SearchApartmentDTO ap){
		
		ArrayList<Apartment> apartmentList = new ArrayList<>(); 
		
		for(Apartment a : apartments.values()) {
			if(a.getStatus().equals(ApartmentStatus.ACTIVE)) {
				
				if(ap.getDateFrom()== null & ap.getDateTo() == null) {
					if((a.getLocation().getAddress().getCity().toLowerCase().contains(ap.getLocation().toLowerCase())) || (a.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase())))
						{
							apartmentList.add(a);
						}
				}else if (ap.getLocation()== null) {
						
					if(((ap.getDateFrom().equals(a.getDateFrom()) || ((ap.getDateFrom().after(a.getDateFrom())) && ap.getDateFrom().before(a.getDateTo()))))
							&& (ap.getDateTo().equals(a.getDateTo()) || ((ap.getDateTo().after(a.getDateFrom())) && ap.getDateTo().before(a.getDateTo())))
							){
						apartmentList.add(a);
					}
				}
				else if(((ap.getDateFrom().equals(a.getDateFrom()) || ((ap.getDateFrom().after(a.getDateFrom())) && ap.getDateFrom().before(a.getDateTo()))))
						&& (ap.getDateTo().equals(a.getDateTo()) || ((ap.getDateTo().after(a.getDateFrom())) && ap.getDateTo().before(a.getDateTo())))
						&& ((a.getLocation().getAddress().getCity().toLowerCase().contains(ap.getLocation().toLowerCase())) || (a.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase())))){
					
			
					apartmentList.add(a);
			
				}
			}
		
		}
		
		
		
		return apartmentList;
	}
	
	public ArrayList<Apartment> apartmentSearchAdvanced(SearchApartmentDTO ap){
		
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		
		for(Apartment a : apartments.values()) {
			if(a.getStatus().equals(ApartmentStatus.ACTIVE)) {
				
				apartmentList.add(a);
				
			}
			
			
		}	
				if (ap.getDateFrom() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.after(ap.getDateFrom())))
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getDateTo() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.before(ap.getDateTo())))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getLocation() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getLocation().getAddress().getCity().toLowerCase()
									.contains(ap.getLocation().toLowerCase()) || apartment.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase()))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getPriceMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() >= ap.getPriceMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getPriceMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() <= ap.getPriceMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getRoomsMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() >= ap.getRoomsMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getRoomsMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() <= ap.getRoomsMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getGuestsMax() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getNumberOfGuests() <= ap.getGuestsMax())
							.collect(Collectors.toCollection(ArrayList::new));
				
		
		return apartmentList;
	}
	
	public ArrayList<Apartment> apartmentSearchHostAdvanced(SearchApartmentDTO ap, String hostUsername) {
		
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		

		for(Apartment a : apartments.values()) {
			if(a.getStatus().equals(ApartmentStatus.ACTIVE) && a.getHost().equals(hostUsername)) {
				
				apartmentList.add(a);
				
			}
			
			
		}	
				if (ap.getDateFrom() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.after(ap.getDateFrom())))
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getDateTo() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.before(ap.getDateTo())))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getLocation() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getLocation().getAddress().getCity().toLowerCase()
									.contains(ap.getLocation().toLowerCase()) || apartment.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase()))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getPriceMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() >= ap.getPriceMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getPriceMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() <= ap.getPriceMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getRoomsMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() >= ap.getRoomsMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getRoomsMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() <= ap.getRoomsMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getGuestsMax() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getNumberOfGuests() <= ap.getGuestsMax())
							.collect(Collectors.toCollection(ArrayList::new));
		
		
		
		
		
		return apartmentList;
	}
	
public ArrayList<Apartment> apartmentSearchAdminAdvanced(SearchApartmentDTO ap) {
		
		ArrayList<Apartment> apartmentList = new ArrayList<>();
		

		for(Apartment a : apartments.values()) {
				apartmentList.add(a);
				
		}	
				if (ap.getDateFrom() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.after(ap.getDateFrom())))
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getDateTo() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getDates().stream()
									.anyMatch(date -> date.before(ap.getDateTo())))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getLocation() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getLocation().getAddress().getCity().toLowerCase()
									.contains(ap.getLocation().toLowerCase()) || apartment.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase()))
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getPriceMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() >= ap.getPriceMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getPriceMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getPricePerNight() <= ap.getPriceMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getRoomsMin() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() >= ap.getRoomsMin())
							.collect(Collectors.toCollection(ArrayList::new));
				if (ap.getRoomsMax() != null)
					apartmentList = apartmentList.stream().filter(apartment -> apartment.getNumberOfRooms() <= ap.getRoomsMax())
							.collect(Collectors.toCollection(ArrayList::new));

				if (ap.getGuestsMax() != null)
					apartmentList = apartmentList.stream()
							.filter(apartment -> apartment.getNumberOfGuests() <= ap.getGuestsMax())
							.collect(Collectors.toCollection(ArrayList::new));
		
		
		
		
		
		return apartmentList;
	}
	
	
	
	//all apartments - admin & host search
	public ArrayList<Apartment> apartmentSearchAdmin(SearchApartmentDTO ap){
		
		ArrayList<Apartment> apartmentList = new ArrayList<>(); 
		
		for(Apartment a : apartments.values()) {
			
				
				if(ap.getDateFrom()== null & ap.getDateTo() == null) {
					if((a.getLocation().getAddress().getCity().toLowerCase().contains(ap.getLocation().toLowerCase())) || (a.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase())))
						{
							apartmentList.add(a);
						}
				}else if (ap.getLocation()== null) {
						
					if(((ap.getDateFrom().equals(a.getDateFrom()) || ((ap.getDateFrom().after(a.getDateFrom())) && ap.getDateFrom().before(a.getDateTo()))))
							&& (ap.getDateTo().equals(a.getDateTo()) || ((ap.getDateTo().after(a.getDateFrom())) && ap.getDateTo().before(a.getDateTo())))
							){
						apartmentList.add(a);
					}
				}
				else if(((ap.getDateFrom().equals(a.getDateFrom()) || ((ap.getDateFrom().after(a.getDateFrom())) && ap.getDateFrom().before(a.getDateTo()))))
						&& (ap.getDateTo().equals(a.getDateTo()) || ((ap.getDateTo().after(a.getDateFrom())) && ap.getDateTo().before(a.getDateTo())))
						&& ((a.getLocation().getAddress().getCity().toLowerCase().contains(ap.getLocation().toLowerCase())) || (a.getLocation().getAddress().getStreet().toLowerCase().contains(ap.getLocation().toLowerCase())))){
					
			
					apartmentList.add(a);
			
				}
			}
		
		
		
		
		
		return apartmentList;
	}
	
	public ArrayList<Apartment> sortApartments(ArrayList<Apartment> apartmentList, String sort){
		
		switch(sort) {
			
		case "PRICE_ASC": 
			apartmentList.sort(new ApartmentPriceComparator(OrderComparator.ASCENDING));
			break;
		case "PRICE_DESC":
			apartmentList.sort(new ApartmentPriceComparator(OrderComparator.DESCENDING));
			break;
		case "DATE_ASC":
			apartmentList.sort(new ApartmentDateComparator(OrderComparator.ASCENDING));
			break;
		case "DATE_DESC":
			apartmentList.sort(new ApartmentDateComparator(OrderComparator.DESCENDING));
			break;
		case "GUEST_ASC":
			apartmentList.sort(new ApartmentGuestComparator(OrderComparator.ASCENDING));
			break;
		case "GUEST_DESC":
			apartmentList.sort(new ApartmentGuestComparator(OrderComparator.DESCENDING));
			break;
		case "ROOM_ASC":
			apartmentList.sort(new ApartmentRoomComparator(OrderComparator.ASCENDING));
			break;
		case "ROOM_DESC":
			apartmentList.sort(new ApartmentRoomComparator(OrderComparator.DESCENDING));
			break;
		}
		
		
		
		return apartmentList;
		
	}
	
	
public ArrayList<Apartment> filterApartments(ArrayList<Apartment> reservationList, String type){
		
	
	
		if(type.equals("BOTH")) {
			return reservationList;
		}
		
		reservationList = reservationList.stream()
				.filter(reservation -> reservation.getType().toLowerCase().equals(type.toLowerCase()))
				.collect(Collectors.toCollection(ArrayList::new));
		
		
		
		return reservationList;
	}
	
	
}