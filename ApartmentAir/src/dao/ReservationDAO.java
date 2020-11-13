package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Reservation;

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
	
	public Collection<Reservation> getAllReservationsGuest(int id){
		
		ArrayList<Reservation> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				if(r.getGuestID() == id) {
					reservationList.add(r);
				}
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	

	public Collection<Reservation> getAllReservations(){
		
		ArrayList<Reservation> reservationList = new ArrayList<>();
		
			for(Reservation r: reservations.values()) {
				
				 	reservationList.add(r);
				
			}
		Collections.reverse(reservationList);
		return reservationList;
	}
	
	
}
