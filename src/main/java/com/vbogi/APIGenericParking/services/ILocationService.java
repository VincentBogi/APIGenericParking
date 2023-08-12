package com.vbogi.APIGenericParking.services;

public interface ILocationService {
	public String getCityFromCoordinates(Double latitude, Double longitude) throws Exception;
}
