package com.bilgeadam.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.bilgeadam.entity.Admin;
import com.bilgeadam.entity.Artist;
import com.bilgeadam.entity.CD;
import com.bilgeadam.entity.DVD;
import com.bilgeadam.entity.User;
import com.bilgeadam.entity.Vinyl;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = sessionFactoryHibernate();
	
	// method
	private static SessionFactory sessionFactoryHibernate() {
		try {
			// instance
			Configuration configuration = new Configuration();
			
			configuration.addAnnotatedClass(Admin.class);
			configuration.addAnnotatedClass(User.class);
			configuration.addAnnotatedClass(CD.class);
			configuration.addAnnotatedClass(DVD.class);
			configuration.addAnnotatedClass(Vinyl.class);
			configuration.addAnnotatedClass(Artist.class);
			// entity classlarımızı buraya ekleyeceğiz
			
			// composition
			
			SessionFactory factory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
			return factory;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// dış dünyada bununla bu classa erişim sağlayabileceğim.
	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
	
}
