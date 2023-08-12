package com.vbogi.APIGenericParking.models;

import java.util.Objects;

public class SearchZone {
	private Location location;
	private Double distanceByMeters;
	
	public SearchZone(Location location, Double distanceByMeters) {
		this.location = location;
		this.distanceByMeters = distanceByMeters;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Double getDistanceByMeters() {
		return distanceByMeters;
	}

	public void setDistanceByMeters(Double distanceByMeters) {
		this.distanceByMeters = distanceByMeters;
	}

	@Override
	public int hashCode() {
		return Objects.hash(distanceByMeters, location);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchZone other = (SearchZone) obj;
		return Objects.equals(distanceByMeters, other.distanceByMeters) && Objects.equals(location, other.location);
	}
	
	
}
