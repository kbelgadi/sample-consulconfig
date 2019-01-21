package com.accor.wco.samples.sample.consulconfig.model;

public class Hotel {
	String hotelId;
	String hotelName;
	String hotelComment;
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHotelComment() {
		return hotelComment;
	}
	public void setHotelComment(String hotelComment) {
		this.hotelComment = hotelComment;
	}
	public Hotel withHotelId(String hotelId) {
		this.setHotelId(hotelId);
		return this;
	}

	public Hotel withHotelName(String hotelName) {
		this.setHotelName(hotelName);
		return this;
	}
	public Hotel withHotelComment(String hotelComment) {
		this.setHotelComment(hotelComment);
		return this;
	}

}
