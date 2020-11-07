package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import beans.Apartment;
import beans.User;
import beans.Amenity;
import enums.ApartmentStatus;

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
		System.out.println("Usao u addApartmentDAO");
		int id = generateNewId();
		newApartment.setId(id);
		newApartment.setStatus(ApartmentStatus.INACTIVE);
		newApartment.setComments(new ArrayList<>());
		newApartment.setReservations(new ArrayList<>());
		
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
				for(int i = 0; i <amenities.size(); i ++) {
					System.out.println("Amenities: " + i);
				}
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
	
}
