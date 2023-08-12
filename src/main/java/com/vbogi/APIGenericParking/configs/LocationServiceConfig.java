package com.vbogi.APIGenericParking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.vbogi.APIGenericParking.services.ILocationService;

@Configuration
public class LocationServiceConfig {
	
	@Value("${qualifier.locationServiceUsed}")
	private String locationServiceUsed;
	
	@Primary
    @Bean
    public ILocationService selectedLocationService(@Autowired ILocationService[] locationServices) {
        for (ILocationService locationService : locationServices) {
            if (locationService.getClass().getSimpleName().equals(locationServiceUsed)) {
                return locationService;
            }
        }
        throw new IllegalArgumentException("No location service found for the given qualifier.");
    }
}
