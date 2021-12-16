package com.bilgeadam.controller;

import java.util.ArrayList;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.bilgeadam.entity.CD;

public class CDController implements IDatabaseCrud<CD> {
	
	@Override
	public void create(CD entity) {
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
	public void delete(CD entity) {
		try {
			CD findEntity = find(entity.getId());
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
	public void update(CD entity) {
		try {
			CD findEntity = find(entity.getId());
			if (findEntity != null) {
				findEntity.setAlbumName(entity.getAlbumName());
				findEntity.setArtist(entity.getArtist());
				findEntity.setAlbumType(entity.getAlbumType());
				findEntity.setPrice(entity.getPrice());
				findEntity.setDiscountRate(entity.getDiscountRate());
				findEntity.setStock(entity.getStock());
				
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
	public ArrayList<CD> list() {
		Session session = databaseConnectionHibernate();
		
		// unutma: buradaki sorgulama entity sorgulaması yani java classına göre
		// çağıracağız.
		String hql = "select str from CD as str where str.id>=:key";
		TypedQuery<CD> typedQuery = session.createQuery(hql, CD.class);
		
		long id = 1L;
		typedQuery.setParameter("key", id);
		
		ArrayList<CD> arrayList = (ArrayList<CD>) typedQuery.getResultList();
		System.out.println("listelendi " + CD.class);
		return arrayList;
	}
	
	@Override
	public CD find(long id) {
		Session session = databaseConnectionHibernate();
		CD CDEntity;
		try {
			CDEntity = session.find(CD.class, id);
			
			if (CDEntity != null) {
				System.out.println("bulundu... " + CDEntity);
				return CDEntity;
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
