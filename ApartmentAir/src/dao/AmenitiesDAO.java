package dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Amenity;
import config.PathConfig;

public class AmenitiesDAO {

	public static ArrayList<Amenity> readAmenities(String absolutPath) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		File amenitiesFile = new File(absolutPath + PathConfig.AMENITIES_FILE);
		boolean created = amenitiesFile.createNewFile();
		if (created)
			mapper.writeValue(amenitiesFile, new ArrayList<Amenity>());
		return mapper.readValue(amenitiesFile, new TypeReference<ArrayList<Amenity>>() {
		});
	}
	
	public static void writeAmenities(String absolutPath, ArrayList<Amenity> amenities) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File amenitiesFile = new File(absolutPath + PathConfig.AMENITIES_FILE);
		amenitiesFile.createNewFile();
		mapper.writeValue(amenitiesFile, amenities);
	}

	public static Amenity getAmenityById(String absolutPath, long id) throws JsonGenerationException, JsonMappingException, IOException {
		ArrayList<Amenity> amenities = readAmenities(absolutPath);
		for (Amenity amenity : amenities) {
			if (amenity.getId() == id)
				return amenity;
		}
		return null;
	}
	
	public static int generateNewId(String absolutPath) throws JsonGenerationException, JsonMappingException, IOException {
		int id=1;
		ArrayList<Amenity> amenities = readAmenities(absolutPath);
		for(Amenity a: amenities) {
			if(a.getId()==id) {
				id++;
			}
		}
		return id;
	}
	
	
	
	
	
	
}
