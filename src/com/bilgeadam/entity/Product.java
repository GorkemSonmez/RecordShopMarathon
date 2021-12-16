package com.bilgeadam.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	protected int Id;
	@Column(name = "album_name")
	protected String albumName;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artist")
	protected Artist artist;
	
	@Enumerated(value = EnumType.STRING)
	protected EType albumType;
	
	@Column(name = "price")
	protected int price;
	@Column(name = "discount_rate")
	protected int discountRate;
	@Column(name = "stock")
	protected int stock;
	
	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	public Product(String albumName, Artist artist, EType albumType, int price, int discountRate, int stock) {
		super();
		this.albumName = albumName;
		this.artist = artist;
		this.albumType = albumType;
		this.price = price;
		this.discountRate = discountRate;
		this.stock = stock;
	}
	
	public int getId() {
		return Id;
	}
	
	public void setId(int id) {
		Id = id;
	}
	
	public String getAlbumName() {
		return albumName;
	}
	
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	
	public Artist getArtist() {
		return artist;
	}
	
	public void setArtist(Artist artist) {
		this.artist = artist;
	}
	
	public EType getAlbumType() {
		return albumType;
	}
	
	public void setAlbumType(EType albumType) {
		this.albumType = albumType;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getDiscountRate() {
		return discountRate;
	}
	
	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
