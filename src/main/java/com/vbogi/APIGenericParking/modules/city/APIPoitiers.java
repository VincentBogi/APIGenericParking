package com.vbogi.APIGenericParking.modules.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vbogi.APIGenericParking.models.Location;
import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;
import com.vbogi.APIGenericParking.modules.city.dtos.PoitiersParkingDto;

@Component
public class APIPoitiers extends APICity<PoitiersParkingDto> {

	@Value("${city.poitiers.urlApiParkingList}")
	private String API_URL_PARKING_LIST;
	
	@Value("${city.poitiers.urlApiAvailablePlaces}")
	private String API_URL_AVAILABLE_PLACES;

	
	public APIPoitiers() {
		super(CityConstants.POITIERS);
	}
	
	@Override
	public List<Parking> getParkingsData(SearchZone searchZone) throws Exception {
		List<Parking> parkings = new ArrayList<Parking>();
		RestTemplate restTemplate = new RestTemplate();

		// build and use url of near Parking list
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(API_URL_PARKING_LIST)
                .queryParam("geofilter.distance", searchZone.getLocation().getLatitude() + "," + searchZone.getLocation().getLongitude() + "," + searchZone.getDistanceByMeters().intValue() );
        String completeRequestUrl = uriBuilder.toUriString();
	    String response = restTemplate.getForObject(completeRequestUrl, String.class);
	    
	    // build and use url of available places 
	    UriComponentsBuilder uriBuilder2 = UriComponentsBuilder.fromUriString(API_URL_AVAILABLE_PLACES)
                .queryParam("geofilter.distance", searchZone.getLocation().getLatitude() + "," + searchZone.getLocation().getLongitude() + "," + searchZone.getDistanceByMeters().intValue() );
        String completeRequestUrl2 = uriBuilder2.toUriString();
	    String response2 = restTemplate.getForObject(completeRequestUrl2, String.class);
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    // Map creation nameParking (key) -> avalaiblePlace (value)
	    Map<String, Integer> mapAvailablePlacesByParking = new HashMap<String, Integer>();
	    JsonNode jsonResult2;
	    jsonResult2 = objectMapper.readTree(response2);
        JsonNode recordsNode2 = jsonResult2.get("records");
        if (recordsNode2.isArray()) {
        	for (JsonNode record : recordsNode2) {
        		JsonNode fields = record.get("fields");
        		String parkingName = fields.path("nom").asText();
        		Integer availablePlaces = fields.path("places").asInt();
        		mapAvailablePlacesByParking.put(parkingName, availablePlaces);
        	}
        }
	    
	    // transform results into Parking objects
	    JsonNode jsonResult;
	    jsonResult = objectMapper.readTree(response);
        JsonNode recordsNode = jsonResult.get("records");
        if (recordsNode.isArray()) {
        	for (JsonNode record : recordsNode) {
	        	JsonNode fields = record.get("fields");
	            PoitiersParkingDto poitierParkingDto = objectMapper.treeToValue(fields, PoitiersParkingDto.class);
	        	Parking parking = transformDataToParking(searchZone, poitierParkingDto);
	        	
	        	// set the available place number with result of second request
	            if(mapAvailablePlacesByParking.containsKey(parking.getName())) {
	            	parking.setNbAvailablePlaces(mapAvailablePlacesByParking.get(parking.getName()));
	            }
	        	
	        	parkings.add(parking);
        	}
        }
        
		return parkings;
	}

	
	@Override
	public Parking transformDataToParking(SearchZone searchZone, PoitiersParkingDto apiData) throws Exception {
		Parking parking = new Parking(apiData.getName(), getCityName(), apiData.getCapacity(), null, new Location(getCityName(), apiData.getLatitude(), apiData.getLongitude()));
		return parking;
	}

}
