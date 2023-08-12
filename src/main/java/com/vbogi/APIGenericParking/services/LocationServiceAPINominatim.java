package com.vbogi.APIGenericParking.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Qualifier("APINominatim")
public class LocationServiceAPINominatim implements ILocationService {

	@Override
	public String getCityFromCoordinates(Double latitude, Double longitude) throws Exception {
		String cityName = "";
		String apiUrl = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude+ "&format=json";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);
        
     // transform results into Parking objects
        ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode jsonResult;
	    jsonResult = objectMapper.readTree(response);
        JsonNode adress = jsonResult.get("address");
        if(adress != null) {
        	cityName = adress.path("city").asText();
        }
        
		return cityName;
	}

}
