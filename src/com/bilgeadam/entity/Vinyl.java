package com.bilgeadam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Vinyl")
public class Vinyl extends Product {
	@Column(name = "rpm")
	private int rpm;
	@Column(name = "diameter")
	private int diameter;
	
	public Vinyl() {
		super();
		
	}
	
	public Vinyl(String albumName, Artist artist, EType albumType, int price, int discountRate, int stock, int rpm,
			int diameter) {
		super(albumName, artist, albumType, price, discountRate, stock);
		this.rpm = rpm;
		this.diameter = diameter;
	}
	
	public int getRpm() {
		return rpm;
	}
	
	public void setRpm(int rpm) {
		this.rpm = rpm;
	}
	
	public int getDiameter() {
		return diameter;
	}
	
	public void setDiameter(int diameter) {
		this.diameter = diameter;
	}
	
	@Override
	public String toString() {
		return "Vinyl [rpm=" + rpm + ", diameter=" + diameter + ", getId()=" + getId() + ", getAlbumName()="
				+ getAlbumName() + ", getArtist()=" + getArtist() + ", getAlbumType()=" + getAlbumType()
				+ ", getPrice()=" + getPrice() + ", getDiscountRate()=" + getDiscountRate() + ", getStock()="
				+ getStock() + "]";
	}
	
}
