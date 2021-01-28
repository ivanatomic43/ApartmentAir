package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Reservation;
import comparators.ApartmentPriceComparator;
import comparators.ReservationPriceComparator;
import dto.ReservationDTO;
import dto.SearchReservationDTO;
import enums.OrderComparator;
import enums.ReservationStatus;

public class ReservationDAO {

	private HashMap<Integer, Reservation> reservations = new HashMap<>();
	
    public ReservationDAO() {
		
	}
	
	public ReservationDAO(String contextPath) {

		loadReservations(contextPath);
	}
	
	public void loadReservations(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/reservations.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Reservation[] reservationArray=objectMapper.readValue(file, Reservation[].class);
			
			for(Reservation res:reservationArray) {
				reservations.put(res.getId(), res);
			}
			System.out.println("reservations: " + reservations.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveReservations(String contextPath) {
		try {
			
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Reservation> resArray=new ArrayList<>();
			
			for(Reservation res: reservations.values()) {
				resArray.add(res);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/reservations.json"), resArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int generateNewId() {
		int id=1;
		for(Reservation r:reservations.values()) {
			if(r.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	public Collection<ReservationDTO> getAllReservationsGuest(int id){
		
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				if(r.getGuestID() == id) {
					ReservationDTO res = new ReservationDTO();
					res.setId(r.getId());
					res.setApartmentID(r.getApartmentID());
					res.setGuestID(r.getGuestID());
					res.setDateOfReservation(r.getDateOfReservation().toString());
					res.setNumberOfNights(r.getNumberOfNights());
					res.setTotalPrice(r.getTotalPrice());
					res.setMessage(r.getMessage());
					res.setReservationStatus(r.getReservationStatus().toString());
					reservationList.add(res);
				}
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	

	public Collection<ReservationDTO> getAllReservations(){
		
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				ReservationDTO res = new ReservationDTO();
				res.setId(r.getId());
				res.setApartmentID(r.getApartmentID());
				res.setGuestID(r.getGuestID());
				res.setDateOfReservation(r.getDateOfReservation().toString());
				res.setNumberOfNights(r.getNumberOfNights());
				res.setTotalPrice(r.getTotalPrice());
				res.setMessage(r.getMessage());
				res.setReservationStatus(r.getReservationStatus().toString());
				reservationList.add(res);
				 	
				
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	
	
	public Collection<Reservation> getAllReservationsA(){
		System.out.println("Usao u get res a");
		ArrayList<Reservation> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				
				reservationList.add(r);
				 	
				
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	
public Collection<ReservationDTO> getAllReservationsHost(String hostUsername){
		
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				if(r.getHostUsername().equals(hostUsername)) {
					ReservationDTO res = new ReservationDTO();
					res.setId(r.getId());
					res.setApartmentID(r.getApartmentID());
					res.setGuestID(r.getGuestID());
					res.setDateOfReservation(r.getDateOfReservation().toString());
					res.setNumberOfNights(r.getNumberOfNights());
					res.setTotalPrice(r.getTotalPrice());
					res.setMessage(r.getMessage());
					res.setReservationStatus(r.getReservationStatus().toString());
					reservationList.add(res);
				}
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	
	public Reservation createNewReservation(Reservation newReservation, String contextPath, double price) {
		System.out.println("usao u createNewReservationDAO");
		
		int id = generateNewId();
		newReservation.setId(id);
		newReservation.setReservationStatus(ReservationStatus.CREATED);
		
		
		//calculate totalPrice
		double totalPrice = newReservation.getNumberOfNights() * price;
		
		
		
		newReservation.setTotalPrice(totalPrice);
		
		reservations.put(newReservation.getId(), newReservation);
		
		saveReservations(contextPath);
		
		
		
		return newReservation;
		
		
	}
	
	public boolean cancelReservation(int id) {
		
		boolean cancelled = false;
		for(Reservation r: reservations.values()) {
			if(r.getId() == id && (r.getReservationStatus().equals(ReservationStatus.ACCEPTED) || r.getReservationStatus().equals(ReservationStatus.CREATED))) {
				
				r.setReservationStatus(ReservationStatus.CANCELED);
				
				return cancelled = true;
			}
			
			
		}
		
		return false;
	}
	
	public boolean approveReservation(int id) {
		
		boolean approved = false;
		for(Reservation r: reservations.values()) {
			if(r.getId() == id &&  r.getReservationStatus().equals(ReservationStatus.CREATED)) {
				
				r.setReservationStatus(ReservationStatus.ACCEPTED);
				return approved = true;
			}
			
			
		}
		
		return approved;
	}
	
	
	public boolean declineReservation(int id) {
		
		boolean declined = false;
		for(Reservation r: reservations.values()) {
			if(r.getId() == id && (r.getReservationStatus().equals(ReservationStatus.ACCEPTED) || r.getReservationStatus().equals(ReservationStatus.CREATED))) {
				
				r.setReservationStatus(ReservationStatus.DECLINED);
				return declined = true;
			}
			
			
		}
		
		return declined;
	}
	
	public boolean finishReservation(int id) {
		
		Calendar currentTime = Calendar.getInstance();
		
		
		
		
		boolean finished = false;
		for(Reservation r: reservations.values()) {
			if(r.getId() == id && r.getReservationStatus().equals(ReservationStatus.ACCEPTED)) {
					Calendar endDate = Calendar.getInstance();
					endDate.setTime(r.getDateOfReservation());
					endDate.add(Calendar.DATE, r.getNumberOfNights());
					
					if(currentTime.compareTo(endDate) == -1) {
						System.out.println("End date has not passed..");
						finished = false;
					} 
					
				
				
				r.setReservationStatus(ReservationStatus.FINISHED);
				return finished = true;
			}
			
			
		}
		
		return false;
	}
	
	public ArrayList<ReservationDTO> reservationSearchAdmin(SearchReservationDTO reservation){
		
		ArrayList<ReservationDTO> reservationList = new ArrayList<>();
		
		for(Reservation r : reservations.values()) {
			
			if(reservation.getUsername() == null) { //ako nije prosledjen username, vrati celu listu
				
				ReservationDTO res = new ReservationDTO();
				res.setId(r.getId());
				res.setApartmentID(r.getApartmentID());
				res.setGuestID(r.getGuestID());
				res.setDateOfReservation(r.getDateOfReservation().toString());
				res.setNumberOfNights(r.getNumberOfNights());
				res.setTotalPrice(r.getTotalPrice());
				res.setMessage(r.getMessage());
				res.setReservationStatus(r.getReservationStatus().toString());
				reservationList.add(res);
				
			}
			else { //ako je prosledjen username, vrati rezervacije koje se poklapaju s njim
				
				
				if(r.getGuestUsername().equals(reservation.getUsername())) {
					ReservationDTO res = new ReservationDTO();
					res.setId(r.getId());
					res.setApartmentID(r.getApartmentID());
					res.setGuestID(r.getGuestID());
					res.setDateOfReservation(r.getDateOfReservation().toString());
					res.setNumberOfNights(r.getNumberOfNights());
					res.setTotalPrice(r.getTotalPrice());
					res.setMessage(r.getMessage());
					res.setReservationStatus(r.getReservationStatus().toString());
					reservationList.add(res);
				}
				
			}
			
			
			
		}
		
		
		
		
		
		
		Collections.reverse(reservationList);
		return reservationList;
	}
	
	public ArrayList<ReservationDTO> reservationSearchHost(SearchReservationDTO reservation, String hostUsername){
	
	ArrayList<ReservationDTO> reservationList = new ArrayList<>();
	
	for(Reservation r : reservations.values()) {
		if(r.getHostUsername().equals(hostUsername)) {
			if(reservation.getUsername() == null) { //ako nije prosledjen username, vrati celu listu
			
			ReservationDTO res = new ReservationDTO();
			res.setId(r.getId());
			res.setApartmentID(r.getApartmentID());
			res.setGuestID(r.getGuestID());
			res.setDateOfReservation(r.getDateOfReservation().toString());
			res.setNumberOfNights(r.getNumberOfNights());
			res.setTotalPrice(r.getTotalPrice());
			res.setMessage(r.getMessage());
			res.setReservationStatus(r.getReservationStatus().toString());
			reservationList.add(res);
			
			}
			else { //ako je prosledjen username, vrati rezervacije koje se poklapaju s njim
			
			
				if(r.getGuestUsername().equals(reservation.getUsername())) {
				ReservationDTO res = new ReservationDTO();
				res.setId(r.getId());
				res.setApartmentID(r.getApartmentID());
				res.setGuestID(r.getGuestID());
				res.setDateOfReservation(r.getDateOfReservation().toString());
				res.setNumberOfNights(r.getNumberOfNights());
				res.setTotalPrice(r.getTotalPrice());
				res.setMessage(r.getMessage());
				res.setReservationStatus(r.getReservationStatus().toString());
				reservationList.add(res);
			}
			
		}
		
		
		
	}
	
	}
	
	
	Collections.reverse(reservationList);
	return reservationList;

	}

	
	public ArrayList<ReservationDTO> sortReservations(ArrayList<ReservationDTO> reservationList, String sort){
	
		switch(sort) {
		
		case "PRICE_ASC": 
			reservationList.sort(new ReservationPriceComparator(OrderComparator.ASCENDING));
			break;
		case "PRICE_DESC":
			reservationList.sort(new ReservationPriceComparator(OrderComparator.DESCENDING));
			break;
		}
		
		return reservationList;
	}
	
	public ArrayList<ReservationDTO> filterReservations(ArrayList<ReservationDTO> reservationList, ReservationStatus status){
		
		
		String resStatus = status.toString();
	
		if(status.equals(ReservationStatus.NONE)) {
			return reservationList;
		}
		
		reservationList = reservationList.stream()
				.filter(reservation -> reservation.getReservationStatus().toString().equals(resStatus))
				.collect(Collectors.toCollection(ArrayList::new));
		
		
		
		return reservationList;
	}
}
