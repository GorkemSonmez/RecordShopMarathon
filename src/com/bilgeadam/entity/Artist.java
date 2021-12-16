package com.bilgeadam.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Artist")
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int Id;
	@Column(name = "artist_name")
	private String artistName;
	@Column(name = "artist_surname")
	private String artistSurname;
	@Column(name = "artist_info")
	private String artistInfo;
	@OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Product.class)
	private Set<Product> albums;
	
	public Artist() {
		// TODO Auto-generated constructor stub
	}
	
	public Artist(String artistName, String artistSurname, String artistInfo) {
		super();
		this.artistName = artistName;
		this.artistSurname = artistSurname;
		this.artistInfo = artistInfo;
		albums = new HashSet<Product>();
	}
	
	public String getArtistName() {
		return artistName;
	}
	
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	
	public String getArtistSurname() {
		return artistSurname;
	}
	
	public void setArtistSurname(String artistSurname) {
		this.artistSurname = artistSurname;
	}
	
	public String getArtistInfo() {
		return artistInfo;
	}
	
	public void setArtistInfo(String artistInfo) {
		this.artistInfo = artistInfo;
	}
	
	public Set<Product> getAlbums() {
		return albums;
	}
	
	public void setAlbums(Set<Product> albums) {
		this.albums = albums;
	}
	
	public int getId() {
		return Id;
	}
	
	public void setId(int id) {
		Id = id;
	}
	
	@Override
	public String toString() {
		return "Artist [Id=" + Id + ", artistName=" + artistName + ", artistSurname=" + artistSurname + ", artistInfo="
				+ artistInfo + "]";
	}
	
}
