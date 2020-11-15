package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Reservation;
import dto.ReservationDTO;
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
		
		System.out.println(totalPrice);
		
		newReservation.setTotalPrice(totalPrice);
		
		reservations.put(newReservation.getId(), newReservation);
		
		saveReservations(contextPath);
		
		
		
		return newReservation;
		
		
	}
	
}
