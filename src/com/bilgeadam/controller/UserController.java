package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.User;

public class UserController implements IDatabaseCrud<User> {
	@Override
	public void create(User entity) {
		try {
			Session session = databaseConnectionHibernate();
			session.getTransaction().begin();
			session.persist(entity);
			session.getTransaction().commit();
			System.out.println("ekleme tamamdır");
		} catch (Exception e) {
			
			System.out.println("ekleme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void delete(User entity) {
		try {
			User findEntity = find(entity.getId());
			if (findEntity != null) {
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.remove(findEntity);
				session.getTransaction().commit();
				System.out.println("silme tamamdır");
			}
		} catch (Exception e) {
			System.out.println("silme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(User entity) {
		try {
			User findEntity = find(entity.getId());
			if (findEntity != null) {
				findEntity.setName(entity.getName());
				findEntity.setSurname(entity.getSurname());
				findEntity.setPhoneNumber(entity.getPhoneNumber());
				findEntity.setAddress(entity.getAddress());
				findEntity.setEmail(entity.getEmail());
				findEntity.setPassword(entity.getPassword());
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.merge(findEntity);
				session.getTransaction().commit();
				System.out.println("güncelleme tamamdır");
			}
			
		} catch (Exception e) {
			System.out.println("güncelleme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<User> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from User as str where str.id>=:key";
		TypedQuery<User> typedQuery = session.createQuery(hql, User.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<User> arrayList = (ArrayList<User>) typedQuery.getResultList();
		System.out.println("listelendi " + User.class);
		return arrayList;
	}
	
	@Override
	public User find(long id) {
		Session session = databaseConnectionHibernate();
		User userEntity;
		try {
			userEntity = session.find(User.class, id);
			
			if (userEntity != null) {
				System.out.println("bulundu... " + userEntity);
				return userEntity;
			} else {
				System.out.println("Aradığınız kriterde sonuçlar bulunamadı ...");
				return null;
			}
		} catch (Exception e) {
			System.out.println("find anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		return null;
	}
	
}
