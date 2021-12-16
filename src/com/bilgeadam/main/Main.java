package com.bilgeadam.main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.controller.AdminController;
import com.bilgeadam.controller.ArtistController;
import com.bilgeadam.controller.UserController;
import com.bilgeadam.entity.Admin;
import com.bilgeadam.entity.Artist;
import com.bilgeadam.entity.CD;
import com.bilgeadam.entity.DVD;
import com.bilgeadam.entity.EType;
import com.bilgeadam.entity.Product;
import com.bilgeadam.entity.User;
import com.bilgeadam.entity.Vinyl;
import com.bilgeadam.util.HibernateUtil;

public class Main {
	Scanner scan = new Scanner(System.in);
	static double orderPrice = 0;
	
	public static void main(String[] args) {
		// createDatabase();
		Main main = new Main();
		main.start();
		
	}
	
	private void start() {
		
		System.out.println("Hoşgeldiniz");
		System.out.println("Admin Girişi için 1'e basın");
		System.out.println("User Girişi için 2'ye basın");
		int select = scan.nextInt();
		if (select == 1) {
			adminlogin();
		} else if (select == 2) {
			userlogin();
		} else {
			System.out.println("Lütfen doğru seçim yap");
			start();
		}
		
	}
	
	private void userlogin() {
		scan.nextLine();
		System.out.println("Mail gir");
		String mail = scan.nextLine();
		System.out.println("Şifre gir");
		String password = scan.nextLine();
		Session session = HibernateUtil.getSessionfactory().openSession();
		
		String hql = "select stu from User as stu where email=:mail and password=:password";
		TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
		typedQuery.setParameter("mail", mail);
		typedQuery.setParameter("password", password);
		User user;
		try {
			user = typedQuery.getSingleResult();
			System.out.println("Başarılı");
			menu();
			System.out.println("Sipariş Toplamı: " + orderPrice);
			
		} catch (Exception e) {
			System.out.println("Kullanıcı bulunamadı");
			e.printStackTrace();
			session.close();
			userlogin();
			
		}
		
	}
	
	private void menu() {
		while (true) {
			System.out.println("İndirimdeki albümleri görmek için 1 e basın");
			System.out.println("Türe göre albümleri görmek için 2 e basın");
			System.out.println("Sanatçıya göre albümleri görmek için 3 e basın");
			int select = scan.nextInt();
			if (select == 1) {
				TreeMap<String, Product> productList = discountProduct();
				
				while (true) {
					System.out.println("Sipariş vermek istediğiniz albümün ismini girin");
					scan.nextLine();
					String albumName = scan.nextLine();
					
					Product pro = productList.get(albumName);
					if (pro == null) {
						System.out.println("Böyle bir albüm yoktur");
					} else {
						orderPrice = orderPrice + pro.getPrice() * ((100.0 - pro.getDiscountRate()) / 100.0);
						
					}
					System.out.println("Sipariş vermeye devam etmek istiyormusunuz");
					String selection = scan.next();
					if (selection.equalsIgnoreCase("H")) {
						
						break;
					}
				}
				
			}
			if (select == 2) {
				
				TreeMap<String, Product> productList = listByType();
				while (true) {
					System.out.println("Sipariş vermek istediğiniz albümün ismini girin");
					
					String albumName = scan.next();
					
					Product pro = productList.get(albumName);
					if (pro == null) {
						System.out.println("Böyle bir albüm yoktur");
					} else {
						orderPrice = orderPrice + pro.getPrice() * ((100.0 - pro.getDiscountRate()) / 100.0);
						
					}
					System.out.println("Sipariş vermeye devam etmek istiyormusunuz");
					String selection = scan.next();
					if (selection.equalsIgnoreCase("H")) {
						
						break;
					}
				}
			}
			if (select == 3) {
				TreeMap<String, Product> productList = listByArtist();
				while (true) {
					System.out.println("Sipariş vermek istediğiniz albümün ismini girin");
					
					String albumName = scan.next();
					
					Product pro = productList.get(albumName);
					if (pro == null) {
						System.out.println("Böyle bir albüm yoktur");
					} else {
						orderPrice = orderPrice + pro.getPrice() * ((100.0 - pro.getDiscountRate()) / 100.0);
						
					}
					System.out.println("Sipariş vermeye devam etmek istiyormusunuz");
					String selection = scan.next();
					if (selection.equalsIgnoreCase("H")) {
						
						break;
					}
				}
			}
			
			System.out.println("Sipariş vermeye devam etmek istiyormusunuz");
			String selection = scan.next();
			if (selection.equalsIgnoreCase("H")) {
				
				break;
			}
		}
	}
	
	private TreeMap<String, Product> listByArtist() {
		TreeMap<String, Product> productList = new TreeMap<>();
		while (true) {
			System.out.println("Listelemek istediğiniz sanatçıyı girin");
			
			String inputArtist = scan.next();
			Session session = HibernateUtil.getSessionfactory().openSession();
			String hql = "select stu from Artist as stu where artistName=:x";
			TypedQuery<Artist> typedQuery = session.createQuery(hql, Artist.class);
			typedQuery.setParameter("x", inputArtist);
			Artist artist = typedQuery.getSingleResult();
			int artistId = artist.getId();
			
			String hql1 = "select stu from Vinyl as stu where artist=" + artistId;
			TypedQuery<Vinyl> typedQuery1 = session.createQuery(hql1, Vinyl.class);
			ArrayList<Vinyl> vinyl = (ArrayList<Vinyl>) typedQuery1.getResultList();
			
			String hql2 = "select stu from CD as stu where artist=" + artistId;
			TypedQuery<CD> typedQuery2 = session.createQuery(hql2, CD.class);
			ArrayList<CD> cd = (ArrayList<CD>) typedQuery2.getResultList();
			
			String hql3 = "select stu from DVD as stu where artist=" + artistId;
			TypedQuery<DVD> typedQuery3 = session.createQuery(hql3, DVD.class);
			ArrayList<DVD> dvd = (ArrayList<DVD>) typedQuery3.getResultList();
			
			for (Vinyl temp : vinyl) {
				productList.put(temp.getAlbumName(), temp);
			}
			for (DVD temp : dvd) {
				
				productList.put(temp.getAlbumName(), temp);
			}
			for (CD temp : cd) {
				
				productList.put(temp.getAlbumName(), temp);
			}
			productList.entrySet().forEach(entry -> {
				System.out.println(entry.getKey() + " " + entry.getValue());
			});
			System.out.println("Diğer sanatçıları görmek istiyor musun");
			String selection = scan.next();
			if (selection.equalsIgnoreCase("H")) {
				break;
			}
		}
		return productList;
	}
	
	private TreeMap<String, Product> listByType() {
		TreeMap<String, Product> productList = new TreeMap<>();
		while (true) {
			
			System.out.println("Listelemek istediğiniz Türü girin");
			
			String inputType = scan.nextLine();
			if (inputType.equalsIgnoreCase("rock")) {
				
				Session session = HibernateUtil.getSessionfactory().openSession();
				String hql = "select stu from Vinyl as stu where albumType=:x";
				TypedQuery<Vinyl> typedQuery = session.createQuery(hql, Vinyl.class);
				typedQuery.setParameter("x", EType.Rock);
				ArrayList<Vinyl> vinyl = (ArrayList<Vinyl>) typedQuery.getResultList();
				String hql1 = "select stu from CD as stu where albumType=:x";
				TypedQuery<CD> typedQuery1 = session.createQuery(hql1, CD.class);
				typedQuery1.setParameter("x", EType.Rock);
				ArrayList<CD> cd = (ArrayList<CD>) typedQuery1.getResultList();
				String hql2 = "select stu from DVD as stu where albumType=:x";
				TypedQuery<DVD> typedQuery2 = session.createQuery(hql2, DVD.class);
				typedQuery2.setParameter("x", EType.Rock);
				ArrayList<DVD> dvd = (ArrayList<DVD>) typedQuery2.getResultList();
				for (Vinyl temp : vinyl) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (DVD temp : dvd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (CD temp : cd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				productList.entrySet().forEach(entry -> {
					System.out.println(entry.getKey() + " " + entry.getValue());
				});
				System.out.println("Diğer türleri görmek istiyor musun");
				String selection = scan.nextLine();
				if (selection.equalsIgnoreCase("H")) {
					break;
				}
				
			} else if (inputType.equalsIgnoreCase("pop")) {
				Session session = HibernateUtil.getSessionfactory().openSession();
				String hql = "select stu from Vinyl as stu where albumType=:x";
				TypedQuery<Vinyl> typedQuery = session.createQuery(hql, Vinyl.class);
				typedQuery.setParameter("x", EType.Pop);
				ArrayList<Vinyl> vinyl = (ArrayList<Vinyl>) typedQuery.getResultList();
				String hql1 = "select stu from CD as stu where albumType=:x";
				TypedQuery<CD> typedQuery1 = session.createQuery(hql1, CD.class);
				typedQuery1.setParameter("x", EType.Pop);
				ArrayList<CD> cd = (ArrayList<CD>) typedQuery1.getResultList();
				String hql2 = "select stu from DVD as stu where albumType=:x";
				TypedQuery<DVD> typedQuery2 = session.createQuery(hql2, DVD.class);
				typedQuery2.setParameter("x", EType.Pop);
				ArrayList<DVD> dvd = (ArrayList<DVD>) typedQuery2.getResultList();
				for (Vinyl temp : vinyl) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (DVD temp : dvd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (CD temp : cd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				productList.entrySet().forEach(entry -> {
					System.out.println(entry.getKey() + " " + entry.getValue());
				});
				System.out.println("Diğer türleri görmek istiyor musun");
				String selection = scan.nextLine();
				if (selection.equalsIgnoreCase("H")) {
					break;
				}
				
			} else if (inputType.equalsIgnoreCase("rap")) {
				Session session = HibernateUtil.getSessionfactory().openSession();
				String hql = "select stu from Vinyl as stu where albumType=:x";
				TypedQuery<Vinyl> typedQuery = session.createQuery(hql, Vinyl.class);
				typedQuery.setParameter("x", EType.Rap);
				ArrayList<Vinyl> vinyl = (ArrayList<Vinyl>) typedQuery.getResultList();
				String hql1 = "select stu from CD as stu where albumType=:x";
				TypedQuery<CD> typedQuery1 = session.createQuery(hql1, CD.class);
				typedQuery.setParameter("x", EType.Rap);
				ArrayList<CD> cd = (ArrayList<CD>) typedQuery1.getResultList();
				String hql2 = "select stu from DVD as stu where albumType=:x";
				TypedQuery<DVD> typedQuery2 = session.createQuery(hql2, DVD.class);
				typedQuery.setParameter("x", EType.Rap);
				ArrayList<DVD> dvd = (ArrayList<DVD>) typedQuery2.getResultList();
				for (Vinyl temp : vinyl) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (DVD temp : dvd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				for (CD temp : cd) {
					
					productList.put(temp.getAlbumName(), temp);
				}
				productList.entrySet().forEach(entry -> {
					System.out.println(entry.getKey() + " " + entry.getValue());
				});
				System.out.println("Diğer türleri görmek istiyor musun");
				String selection = scan.nextLine();
				if (selection.equalsIgnoreCase("H")) {
					break;
				}
				
			} else {
				System.out.println("Böyle bir tür yoktur");
			}
		}
		return productList;
	}
	
	private TreeMap<String, Product> discountProduct() {
		Session session = HibernateUtil.getSessionfactory().openSession();
		String hql = "select stu from Vinyl as stu where discountRate>=1";
		TypedQuery<Vinyl> typedQuery = session.createQuery(hql, Vinyl.class);
		ArrayList<Vinyl> vinyl = (ArrayList<Vinyl>) typedQuery.getResultList();
		String hql1 = "select stu from CD as stu where discountRate>=1";
		TypedQuery<CD> typedQuery1 = session.createQuery(hql1, CD.class);
		ArrayList<CD> cd = (ArrayList<CD>) typedQuery1.getResultList();
		String hql2 = "select stu from DVD as stu where discountRate>=1";
		TypedQuery<DVD> typedQuery2 = session.createQuery(hql2, DVD.class);
		ArrayList<DVD> dvd = (ArrayList<DVD>) typedQuery2.getResultList();
		
		TreeMap<String, Product> productList = new TreeMap<>();
		for (Vinyl temp : vinyl) {
			
			productList.put(temp.getAlbumName(), temp);
		}
		for (DVD temp : dvd) {
			
			productList.put(temp.getAlbumName(), temp);
		}
		for (CD temp : cd) {
			
			productList.put(temp.getAlbumName(), temp);
		}
		productList.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " " + entry.getValue());
		});
		return productList;
	}
	
	private void adminlogin() {
		scan.nextLine();
		System.out.println("Mail gir");
		String mail = scan.nextLine();
		System.out.println("Şifre gir");
		String password = scan.nextLine();
		Session session = HibernateUtil.getSessionfactory().openSession();
		
		String hql = "select stu from Admin as stu where email=:mail and password=:password";
		TypedQuery<Admin> typedQuery = session.createQuery(hql, Admin.class);
		typedQuery.setParameter("mail", mail);
		typedQuery.setParameter("password", password);
		Admin admin;
		try {
			admin = typedQuery.getSingleResult();
			System.out.println("Başarılı");
			
		} catch (Exception e) {
			System.out.println("Kullanıcı bulunamadı");
			session.close();
			userlogin();
			
		}
		
	}
	
	private static void createDatabase() {
		Admin admin = new Admin();
		admin.setEmail("admin");
		admin.setPassword("qwerty");
		User user = new User();
		user.setEmail("gorkem");
		user.setPassword("şifre1234");
		Artist artist1 = new Artist("Artistname1", "ArtistSurname 1", "çok başarılı bir artist");
		Artist artist2 = new Artist("Artistname2", "ArtistSurname 2", "çok başarılı bir artist");
		Artist artist3 = new Artist("Artistname3", "ArtistSurname 3", "çok başarılı bir artist");
		CD cd1 = new CD("AlbumCD1", artist1, EType.Pop, 50, 15, 5);
		CD cd2 = new CD("AlbumCD2", artist2, EType.Pop, 50, 15, 5);
		CD cd3 = new CD("AlbumCD3", artist3, EType.Pop, 50, 15, 5);
		DVD dvd1 = new DVD("AlbumDVD1", artist1, EType.Pop, 50, 15, 5, 1024);
		DVD dvd2 = new DVD("AlbumDVD2", artist2, EType.Pop, 50, 15, 5, 1024);
		DVD dvd3 = new DVD("AlbumDVD3", artist3, EType.Pop, 50, 15, 5, 1024);
		Vinyl vinyl1 = new Vinyl("AlbumVinyl1", artist1, EType.Pop, 50, 0, 5, 45, 7);
		Vinyl vinyl2 = new Vinyl("AlbumVinyl2", artist2, EType.Pop, 50, 15, 5, 45, 7);
		Vinyl vinyl3 = new Vinyl("AlbumVinyl3", artist3, EType.Pop, 50, 15, 5, 45, 7);
		artist1.getAlbums().add(cd1);
		artist1.getAlbums().add(dvd1);
		artist1.getAlbums().add(vinyl1);
		artist2.getAlbums().add(cd2);
		artist2.getAlbums().add(dvd2);
		artist2.getAlbums().add(vinyl2);
		artist3.getAlbums().add(cd3);
		artist3.getAlbums().add(dvd3);
		artist3.getAlbums().add(vinyl3);
		
		AdminController adminController = new AdminController();
		adminController.create(admin);
		UserController userController = new UserController();
		userController.create(user);
		
		ArtistController artistController = new ArtistController();
		artistController.create(artist1);
		artistController.create(artist2);
		artistController.create(artist3);
		
	}
	
}
