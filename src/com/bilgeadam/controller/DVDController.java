package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.DVD;

public class DVDController implements IDatabaseCrud<DVD> {
	
	@Override
	public void create(DVD entity) {
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
	public void delete(DVD entity) {
		try {
			DVD findEntity = find(entity.getId());
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
	public void update(DVD entity) {
		try {
			DVD findEntity = find(entity.getId());
			if (findEntity != null) {
				findEntity.setAlbumName(entity.getAlbumName());
				findEntity.setArtist(entity.getArtist());
				findEntity.setAlbumType(entity.getAlbumType());
				findEntity.setPrice(entity.getPrice());
				findEntity.setDiscountRate(entity.getDiscountRate());
				findEntity.setStock(entity.getStock());
				findEntity.setQuality(entity.getQuality());
				
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
	public ArrayList<DVD> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from DVD as str where str.id>=:key";
		TypedQuery<DVD> typedQuery = session.createQuery(hql, DVD.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<DVD> arrayList = (ArrayList<DVD>) typedQuery.getResultList();
		System.out.println("listelendi " + DVD.class);
		return arrayList;
	}
	
	@Override
	public DVD find(long id) {
		Session session = databaseConnectionHibernate();
		DVD DVDEntity;
		try {
			DVDEntity = session.find(DVD.class, id);
			
			if (DVDEntity != null) {
				System.out.println("bulundu... " + DVDEntity);
				return DVDEntity;
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
