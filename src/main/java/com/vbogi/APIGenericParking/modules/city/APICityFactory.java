package com.vbogi.APIGenericParking.modules.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class APICityFactory {
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
    private APIMarseille apiMarseille;

    @Autowired
    private APIPoitiers apiPoitiers;
	
	public APICity<?> createAPICity(String cityName) {
		APICity<?> apiCity;
        if (cityName.equalsIgnoreCase(CityConstants.MARRSEILLE)) {
        	apiCity = apiMarseille;
        } else if (cityName.equalsIgnoreCase(CityConstants.POITIERS)) {
        	apiCity = apiPoitiers;
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("error.cityNotManaged", new Object[]{cityName}, LocaleContextHolder.getLocale()));
        }
        
        return apiCity;
    }
}
