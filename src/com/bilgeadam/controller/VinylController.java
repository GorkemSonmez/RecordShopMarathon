package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.Vinyl;

public class VinylController implements IDatabaseCrud<Vinyl> {
	
	@Override
	public void create(Vinyl entity) {
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
	public void delete(Vinyl entity) {
		try {
			Vinyl findEntity = find(entity.getId());
			if (findEntity != null) {
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.remove(findEntity);
				session.getTransaction().commit();
				session.close();
				System.out.println("silme tamamdır");
			}
		} catch (Exception e) {
			System.out.println("silme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void update(Vinyl entity) {
		try {
			Vinyl findEntity = find(entity.getId());
			if (findEntity != null) {
				findEntity.setAlbumName(entity.getAlbumName());
				findEntity.setArtist(entity.getArtist());
				findEntity.setAlbumType(entity.getAlbumType());
				findEntity.setPrice(entity.getPrice());
				findEntity.setDiscountRate(entity.getDiscountRate());
				findEntity.setStock(entity.getStock());
				findEntity.setRpm(entity.getRpm());
				findEntity.setDiameter(entity.getDiameter());
				
				Session session = databaseConnectionHibernate();
				session.getTransaction().begin();
				session.merge(findEntity);
				session.getTransaction().commit();
				session.close();
				System.out.println("güncelleme tamamdır");
			}
			
		} catch (Exception e) {
			System.out.println("güncelleme anında hata meydana geldi !!!!! ");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public ArrayList<Vinyl> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from Vinyl as str where str.id>=:key";
		TypedQuery<Vinyl> typedQuery = session.createQuery(hql, Vinyl.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<Vinyl> arrayList = (ArrayList<Vinyl>) typedQuery.getResultList();
		System.out.println("listelendi " + Vinyl.class);
		return arrayList;
	}
	
	@Override
	public Vinyl find(long id) {
		Session session = databaseConnectionHibernate();
		Vinyl vinylEntity;
		try {
			vinylEntity = session.find(Vinyl.class, id);
			
			if (vinylEntity != null) {
				System.out.println("bulundu... " + vinylEntity);
				session.close();
				return vinylEntity;
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
