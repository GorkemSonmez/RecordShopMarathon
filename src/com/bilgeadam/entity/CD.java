package com.bilgeadam.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CD")
public class CD extends Product {
	
	public CD() {
		super();
	}
	
	public CD(String albumName, Artist artist, EType albumType, int price, int discountRate, int stock) {
		super(albumName, artist, albumType, price, discountRate, stock);
		
	}
	
	@Override
	public String toString() {
		return "CD [getId()=" + getId() + ", getAlbumName()=" + getAlbumName() + ", getArtist()=" + getArtist()
				+ ", getAlbumType()=" + getAlbumType() + ", getPrice()=" + getPrice() + ", getDiscountRate()="
				+ getDiscountRate() + ", getStock()=" + getStock() + "]";
	}
	
}
