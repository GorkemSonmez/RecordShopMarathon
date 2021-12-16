package com.bilgeadam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DVD")
public class DVD extends Product {
	@Column(name = "quality")
	private int quality;
	
	public DVD() {
		super();
	}
	
	public DVD(String albumName, Artist artist, EType albumType, int price, int discountRate, int stock,
			int quality) {
		super(albumName, artist, albumType, price, discountRate, stock);
		this.quality = quality;
	}
	
	public int getQuality() {
		return quality;
	}
	
	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return "DVD [quality=" + quality + ", getQuality()=" + getQuality() + ", getId()=" + getId()
				+ ", getAlbumName()=" + getAlbumName() + ", getArtist()=" + getArtist() + ", getAlbumType()="
				+ getAlbumType() + ", getPrice()=" + getPrice() + ", getDiscountRate()=" + getDiscountRate()
				+ ", getStock()=" + getStock() + "]";
	}
	
}
