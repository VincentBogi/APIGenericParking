package com.vbogi.APIGenericParking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.vbogi.APIGenericParking.services.IParkingService;

@Configuration
public class ParkingServiceConfig {
	
	@Value("${qualifier.parkingServiceUsed}")
	private String parkingServiceUsed;
	
    @Bean
    public IParkingService selectedParkingService(@Autowired IParkingService[] parkingServices) {
        for (IParkingService parkingService : parkingServices) {
            if (parkingService.getClass().getSimpleName().equals(parkingServiceUsed)) {
                return parkingService;
            }
        }
        throw new IllegalArgumentException("No parking service found for the given qualifier.");
    }
}
