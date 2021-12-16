package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.Admin;

public class AdminController implements IDatabaseCrud<Admin> {
	
	@Override
	public void create(Admin entity) {
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
	public void delete(Admin entity) {
		try {
			Admin findEntity = find(entity.getId());
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
	public void update(Admin entity) {
		try {
			Admin findEntity = find(entity.getId());
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
	public ArrayList<Admin> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from Admin as str where str.id>=:key";
		TypedQuery<Admin> typedQuery = session.createQuery(hql, Admin.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<Admin> arrayList = (ArrayList<Admin>) typedQuery.getResultList();
		System.out.println("listelendi " + Admin.class);
		return arrayList;
	}
	
	@Override
	public Admin find(long id) {
		Session session = databaseConnectionHibernate();
		Admin adminEntity;
		try {
			adminEntity = session.find(Admin.class, id);
			
			if (adminEntity != null) {
				System.out.println("bulundu... " + adminEntity);
				return adminEntity;
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
