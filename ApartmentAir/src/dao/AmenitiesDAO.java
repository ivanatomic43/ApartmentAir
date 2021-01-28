package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenity;
import beans.Apartment;
import config.PathConfig;

public class AmenitiesDAO {

private HashMap<Integer, Amenity> amenities=new HashMap<>();
	
	public AmenitiesDAO() {
		
	}
	
	public AmenitiesDAO(String contextPath) {

		loadAmenities(contextPath);
	}
	
	public void loadAmenities(String contextPath) {
		
		try {
			File file=new File(contextPath+ "/jsonFiles/amenities.json");
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			Amenity[] amenitiesArray=objectMapper.readValue(file, Amenity[].class);
			
			for(Amenity a:amenitiesArray) {
				amenities.put(a.getId(), a);
			}
			System.out.println("Amenities: " + amenities.size());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
	}
	
	public void saveAmenities(String contextPath) {
		try {
			
			ObjectMapper objectMapper=new ObjectMapper();
			objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);

			ArrayList<Amenity> appArray=new ArrayList<>();
			
			for(Amenity a: amenities.values()) {
				appArray.add(a);
			}
			

			objectMapper.writeValue(new File(contextPath+"/jsonFiles/amenities.json"), appArray);
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public int generateNewId() {
		int id=1;
		for(Amenity a:amenities.values()) {
			if(a.getId()==id) {
				id++;
			}
		}
		return id;
	}
	public Amenity getAmenityById(int id) throws JsonGenerationException, JsonMappingException, IOException {
		
		for (Amenity amenity : amenities.values()) {
			if (amenity.getId() == id)
				return amenity;
		}
		return null;
	}

	
	public Collection<Amenity> getAllAmenities(){
		ArrayList<Amenity> retVal=new ArrayList<>();
		for(Amenity a:amenities.values()) {
			if(!a.isRemoved()) {
				retVal.add(a);
			}
		}
		Collections.reverse(retVal);
		return retVal;
	}
	
	public Amenity createNewAmenity(Amenity amenity, String contextPath) {
		
		int id = generateNewId();
		amenity.setId(id);
		amenity.setRemoved(false);
		
		amenities.put(amenity.getId(), amenity);
		
		saveAmenities(contextPath);
		
		return amenity;
		
		
	}
	
	public Amenity editAmenity(Amenity amenity, String contextPath) {
		
		for(Amenity oldAm : amenities.values()) {
			if(oldAm.getId() == amenity.getId()) {
				oldAm.setName(amenity.getName());
			
				saveAmenities(contextPath);
				return oldAm;
			}
			
		}
		return null;
}
	
	public boolean removeAmenity(int id, String contextPath) {
		 boolean removed = false;
		
 ArrayList<Amenity> amenitiesList = new ArrayList<>();
 
 
 	for(Amenity a : amenities.values()) {
	 if(a.getId() == id) {
		 a.setRemoved(true);
		 saveAmenities(contextPath);
		 removed = true;
		 return removed;
	 }
 	}
		
		 return removed;

		
	}
	
	
	
}
