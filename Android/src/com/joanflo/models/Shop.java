package com.joanflo.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Shop {

	// primary key
	private int idShop;
	
	//foreign key
	private City city;
	
	// shop info
	private CharSequence direction;
	private CharSequence schedule;
	private CharSequence phone;
	private CharSequence email;
	
	// location
	private double latitude;
	private double longitude;
	
	
	
	public Shop(int idShop, City city, CharSequence direction, CharSequence schedule, CharSequence phone, CharSequence email, double latitude, double longitude) {
		this.idShop = idShop;
		this.city = city;
		this.direction = direction;
		this.schedule = schedule;
		this.phone = phone;
		this.email = email;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Shop(JSONObject jShop) throws JSONException {
		this.idShop = jShop.getInt("idShop");
		this.direction = jShop.getString("direction");
		this.schedule = jShop.getString("schedule");
		this.phone = jShop.getString("phone");
		this.email = jShop.getString("email");
		this.latitude = jShop.getDouble("latitude");
		this.longitude = jShop.getDouble("longitude");
		this.city = new City(jShop.getString("cityName"));
	}



	public int getIdShop() {
		return idShop;
	}


	public City getCity() {
		return city;
	}


	public CharSequence getDirection() {
		return direction;
	}

	public void setDirection(CharSequence direction) {
		this.direction = direction;
	}


	public CharSequence getSchedule() {
		return schedule;
	}

	public void setSchedule(CharSequence schedule) {
		this.schedule = schedule;
	}


	public CharSequence getPhone() {
		return phone;
	}

	public void setPhone(CharSequence phone) {
		this.phone = phone;
	}


	public CharSequence getEmail() {
		return email;
	}

	public void setEmail(CharSequence email) {
		this.email = email;
	}


	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
	public boolean equals(Object obj) {
		if (obj instanceof Shop) {
			Shop shop = (Shop) obj;
			return idShop == shop.getIdShop(); 
		} else {
			return false;
		}
	}
	
	
	
	public JSONObject convertToJSON() {
		JSONObject jShop = new JSONObject();
		
		try {
			jShop.put("idShop", idShop);
			jShop.put("direction", direction);
			jShop.put("schedule", schedule);
			jShop.put("phone", phone);
			jShop.put("email", email);
			jShop.put("latitude", latitude);
			jShop.put("longitude", longitude);
			if (city != null) {
				JSONObject jCity = city.convertToJSON();
				jShop.put("city", jCity);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jShop;
	}
	
	
}
