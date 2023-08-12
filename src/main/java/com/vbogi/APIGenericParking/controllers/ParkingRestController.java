package com.vbogi.APIGenericParking.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vbogi.APIGenericParking.dtos.LocationDTO;
import com.vbogi.APIGenericParking.dtos.ParkingDTO;
import com.vbogi.APIGenericParking.models.Location;
import com.vbogi.APIGenericParking.models.Parking;
import com.vbogi.APIGenericParking.models.SearchZone;
import com.vbogi.APIGenericParking.modules.city.APICity;
import com.vbogi.APIGenericParking.modules.city.APICityFactory;
import com.vbogi.APIGenericParking.services.ILocationService;
import com.vbogi.APIGenericParking.services.IParkingService;


@RestController
@CrossOrigin(origins = "${urlApi.origins}")
@RequestMapping("${app.uri.parkingRestController}")
public class ParkingRestController {
	
	private final MessageSource messageSource;
	
	private APICityFactory apiCityFactory;

	private IParkingService parkingService;

	private ILocationService locationService;
	
	@Autowired
	public ParkingRestController(APICityFactory apiCityFactory,
			IParkingService parkingService, 
			ILocationService locationService,
			MessageSource messageSource) {
		this.apiCityFactory = apiCityFactory;
		this.parkingService = parkingService;
		this.locationService = locationService;
		this.messageSource = messageSource;
	}
	
	
	/**
	 * Get all quizs
	 * @return
	 */
	@GetMapping("/getNearParking")
	public ResponseEntity<Object> getNearParking( @RequestParam("latitude") Double latitude, @RequestParam("longitude") Double longitude,  @RequestParam("searchDistance") Double searchDistance) {
		
		try {
			LocationDTO locationDTO = new LocationDTO(latitude, longitude);
			String cityName = "";
			
			// find city name location
			cityName = locationService.getCityFromCoordinates(locationDTO.getLatitude(), locationDTO.getLongitude());
			
			// build search zone
			SearchZone searchZone = new SearchZone(new Location(cityName, locationDTO.getLatitude(), locationDTO.getLongitude()), searchDistance);
			
			// get Parking Data
			List<Parking> parkings = new ArrayList<Parking>();
			List<ParkingDTO> parkingsDTO = new ArrayList<ParkingDTO>();
			if( !cityName.isBlank() ) {
				// build data
				APICity<?> apiCity = apiCityFactory.createAPICity(cityName);
				parkings = parkingService.getParkingData(apiCity, searchZone);
				
				// prepare and return dto results
				System.out.println("\n\nPARKINGS PROCHE :");
				for (Parking parking : parkings) {
					System.out.println(parking);
					parkingsDTO.add(parking.toParkingDTO());
				}
				return new ResponseEntity<Object>(parkingsDTO, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>( messageSource.getMessage("error.cityNotFound", new Object[]{locationDTO.getLatitude(), locationDTO.getLongitude()}, LocaleContextHolder.getLocale()), HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
